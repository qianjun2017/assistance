/**
 * 
 */
package com.cc.push.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.spring.SpringContextUtil;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.push.bean.FormBean;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.PushUserBean;
import com.cc.push.enums.PushStatusEnum;
import com.cc.push.enums.PushTypeEnum;
import com.cc.push.result.FormResult;
import com.cc.push.service.PushService;
import com.cc.wx.http.request.TemplateMessageRequest;
import com.cc.wx.http.request.model.Value;
import com.cc.wx.http.response.TemplateMessageResponse;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;

/**
 * @author ws_yu
 *
 */
public class TemplatePushThread extends Thread {
	
	private Long pushId;

	public TemplatePushThread(Long pushId) {
		this.pushId = pushId;
	}

	@Override
	public void run() {
		PushBean pushBean = PushBean.get(PushBean.class, pushId);
		if(pushBean==null){
			return ;
		}
		PushService pushService = SpringContextUtil.getBean(PushService.class);
		List<PushUserBean> pushUserBeanList = new ArrayList<PushUserBean>();
		PushTypeEnum pushTypeEnum = PushTypeEnum.getPushTypeEnumByCode(pushBean.getType());
		if(PushTypeEnum.ASSIGN.equals(pushTypeEnum)){
			pushUserBeanList.addAll(PushUserBean.findAllByParams(PushUserBean.class, "pushId", pushBean.getId()));
		}else if(PushTypeEnum.ALL.equals(pushTypeEnum)){
			List<FormResult> userFormList = pushService.queryAllUserFormList();
			if(!ListTools.isEmptyOrNull(userFormList)){
				pushUserBeanList.addAll(userFormList.stream().map(userForm->{
					PushUserBean pushUserBean = new PushUserBean();
					pushUserBean.setPushId(pushBean.getId());
					pushUserBean.setOpenid(userForm.getOpenid());
					pushUserBean.setName(userForm.getUserName());
					return pushUserBean;
				}).collect(Collectors.toList()));
			}
		}
		if(ListTools.isEmptyOrNull(pushUserBeanList)){
			pushBean.setStatus(PushStatusEnum.DONE.getCode());
			pushBean.save();
			return;
		}
		pushBean.setStatus(PushStatusEnum.DOING.getCode());
		pushBean.save();
		WeiXinService weiXinService = SpringContextUtil.getBean(WeiXinService.class);
		AccessTokenService accessTokenService = SpringContextUtil.getBean(AccessTokenService.class);
		TemplateMessageRequest request = new TemplateMessageRequest();
		request.setAccessToken(accessTokenService.queryAccessToken());
		request.setPage(pushBean.getPage());
		request.setEmphasisKeyword(pushBean.getEmphasisKeyword());
		request.setTemplateId(pushBean.getTemplateId());
		byte[] content = pushBean.getContent();
		if(content!=null && content.length>0){
			HashMap<String, Object> dataMap = JsonTools.toObject(new String(content), HashMap.class);
			if(dataMap!=null && !dataMap.isEmpty()){
				Map<String, Value> data = new HashMap<String, Value>();
				Iterator<String> iterator = dataMap.keySet().iterator();
				while(iterator.hasNext()){
					String keyword = iterator.next();
					data.put(keyword, new Value(StringTools.toString(dataMap.get(keyword))));
				}
			}
		}
		for(PushUserBean pushUserBean: pushUserBeanList){
			request.setToUser(pushUserBean.getOpenid());
			FormBean formBean = pushService.queryUserForm(pushUserBean.getUserId());
			if(formBean!=null){
				formBean.delete();
				request.setFormId(formBean.getFormId());
				request.setToUser(pushUserBean.getOpenid());
				TemplateMessageResponse response = weiXinService.sendTemplateMessage(request);
				pushUserBean.setSuccess(response.isSuccess());
				pushUserBean.setMessage(response.getMessage());
			}else{
				pushUserBean.setSuccess(Boolean.FALSE);
				pushUserBean.setMessage("没有可用的formId");
			}
			pushUserBean.save();
		}
		pushBean.setStatus(PushStatusEnum.DONE.getCode());
		pushBean.save();
	}

}
