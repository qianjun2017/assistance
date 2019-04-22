/**
 * 
 */
package com.cc.push.web;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.bean.CustomerBean;
import com.cc.push.bean.PageBean;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.PushUserBean;
import com.cc.push.bean.TemplateBean;
import com.cc.push.enums.PushStatusEnum;
import com.cc.push.enums.PushTypeEnum;
import com.cc.push.form.PushForm;
import com.cc.push.form.PushQueryForm;
import com.cc.push.result.FormResult;
import com.cc.push.result.PushListResult;
import com.cc.push.service.PushService;
import com.cc.push.thread.TemplatePushThread;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/push")
public class PushController {
	
	@Autowired
	private PushService pushService;
	
	@ResponseBody
	@RequiresPermissions(value = { "push" })
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public Response<Object> queryPushCustomer(){
		Response<Object> response = new Response<Object>();
		try {
			List<FormResult> userFormList = pushService.queryAllUserFormList();
			response.setData(userFormList);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 全部会员推送
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "push" })
	@RequestMapping(value = "/all", method = RequestMethod.POST)
	public Response<String> pushAll(@RequestBody PushForm form){
		Response<String> response = new Response<String>();
		if(form.getTemplateId()==null){
			response.setMessage("请选择发送模板");
			return response;
		}
		TemplateBean templateBean = TemplateBean.get(TemplateBean.class, form.getTemplateId());
		if(templateBean==null){
			response.setMessage("发送模板不存在或已删除，请重新选择");
			return response;
		}
		PushBean pushBean = new PushBean();
		pushBean.setTemplateId(templateBean.getTemplateId());
		pushBean.setTitle(templateBean.getTitle());
		if(form.getPageId()!=null){
			PageBean pageBean = PageBean.get(PageBean.class, form.getPageId());
			if(pageBean==null){
				response.setMessage("跳转详情页面不存在或已删除，请重新选择");
				return response;
			}
			pushBean.setPage(pageBean.getPage()+(StringTools.isNullOrNone(form.getParam())?"":("?"+form.getParam())));
		}
		pushBean.setEmphasisKeyword(form.getEmphasisKeyword());
		pushBean.setStatus(PushStatusEnum.UNDO.getCode());
		pushBean.setType(PushTypeEnum.ALL.getCode());
		pushBean.setCreateTime(DateTools.now());
		try {
			if(form.getKeywords()!=null && !form.getKeywords().isEmpty()){
				pushBean.setContent(JsonTools.toJsonString(form.getKeywords()).getBytes("utf-8"));
			}
			pushService.savePush(pushBean);
			new TemplatePushThread(pushBean.getId()).start();
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 指定会员推送
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "push" })
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	public Response<String> pushAssign(@RequestBody PushForm form){
		Response<String> response = new Response<String>();
		if(form.getTemplateId()==null){
			response.setMessage("请选择发送模板");
			return response;
		}
		TemplateBean templateBean = TemplateBean.get(TemplateBean.class, form.getTemplateId());
		if(templateBean==null){
			response.setMessage("发送模板不存在或已删除，请重新选择");
			return response;
		}
		PushBean pushBean = new PushBean();
		pushBean.setTemplateId(templateBean.getTemplateId());
		pushBean.setTitle(templateBean.getTitle());
		if(form.getPageId()!=null){
			PageBean pageBean = PageBean.get(PageBean.class, form.getPageId());
			if(pageBean==null){
				response.setMessage("跳转详情页面不存在或已删除，请重新选择");
				return response;
			}
			pushBean.setPage(pageBean.getPage()+(StringTools.isNullOrNone(form.getParam())?"":("?"+form.getParam())));
		}
		if(ListTools.isEmptyOrNull(form.getUserList())){
			response.setMessage("请选择接收人");
			return response;
		}
		pushBean.setEmphasisKeyword(form.getEmphasisKeyword());
		pushBean.setStatus(PushStatusEnum.UNDO.getCode());
		pushBean.setType(PushTypeEnum.ASSIGN.getCode());
		pushBean.setCreateTime(DateTools.now());
		try {
			if(form.getKeywords()!=null && !form.getKeywords().isEmpty()){
				pushBean.setContent(JsonTools.toJsonString(form.getKeywords()).getBytes("utf-8"));
			}
			pushService.savePush(pushBean);
			for(Long userId: form.getUserList()){
				CustomerBean customerBean = CustomerBean.get(CustomerBean.class, userId);
				if(customerBean!=null){
					PushUserBean pushUserBean = new PushUserBean();
					pushUserBean.setName(customerBean.getName());
					pushUserBean.setOpenid(customerBean.getOpenid());
					pushUserBean.setUserId(customerBean.getId());
					pushUserBean.setPushId(pushBean.getId());
					pushService.savePushUser(pushUserBean);
				}
			}
			new TemplatePushThread(pushBean.getId()).start();
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询推送历史
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/history/page", method = RequestMethod.GET)
	public Page<PushListResult> queryTemplatePage(@ModelAttribute PushQueryForm form){
		Page<PushListResult> page = pushService.queryPushPage(form);
		return page;
	}
	
	/**
	 * 查询历史推送人员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/history/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Object> queryPushUser(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		List<PushUserBean> pushUserList = PushUserBean.findAllByParams(PushUserBean.class, "pushId", id);
		if(ListTools.isEmptyOrNull(pushUserList)){
			response.setMessage("没有推送给任何人员");
			return response;
		}
		response.setData(pushUserList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
