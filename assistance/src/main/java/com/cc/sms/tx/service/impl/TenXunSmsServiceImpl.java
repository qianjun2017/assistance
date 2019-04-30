package com.cc.sms.tx.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.SHA256Tools;
import com.cc.common.tools.StringTools;
import com.cc.sms.tx.http.request.TenXunSmsSendRequest;
import com.cc.sms.tx.http.request.model.Phone;
import com.cc.sms.tx.http.response.TenXunSmsSendResponse;
import com.cc.sms.tx.service.TenXunSmsService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;

@Service
public class TenXunSmsServiceImpl implements TenXunSmsService {
	
	@Autowired
	private HttpService httpService;
	
	@Autowired
    private SystemConfigService systemConfigService;

	@Override
	public TenXunSmsSendResponse smsSend(TenXunSmsSendRequest request) {
		TenXunSmsSendResponse response = new TenXunSmsSendResponse();
		List<SystemConfigBean> systemConfigBeanList = systemConfigService.querySystemConfigBeanList("tenxun.sms");
		if(ListTools.isEmptyOrNull(systemConfigBeanList)){
			response.setMessage("请配置腾讯短信服务参数");
			return response;
		}
		SystemConfigBean appKeySystemConfigBean = null;
		for(SystemConfigBean systemConfigBean: systemConfigBeanList){
			if("tenxun.sms.appid".equals(systemConfigBean.getPropertyName())){
				request.setSdkAppid(systemConfigBean.getPropertyValue());
			}else if("tenxun.sms.appkey".equals(systemConfigBean.getPropertyName())){
				appKeySystemConfigBean = systemConfigService.querySystemConfigBean("tenxun.sms.appkey");
			}else if("tenxun.sms.sign".equals(systemConfigBean.getPropertyName())){
				request.setSign(systemConfigBean.getPropertyValue());
			}else if("tenxun.sms.extend".equals(systemConfigBean.getPropertyName())){
				request.setExtend(StringTools.isNullOrNone(systemConfigBean.getPropertyValue())?StringTools.getRandomNumberCode(6):systemConfigBean.getPropertyValue());
			}
		}
		if(StringTools.isNullOrNone(request.getSdkAppid())){
			response.setMessage("请配置腾讯短信服务应用appid");
			return response;
		}
		if(appKeySystemConfigBean==null || StringTools.isNullOrNone(appKeySystemConfigBean.getPropertyValue())){
			response.setMessage("请配置腾讯短信服务应用appkey");
			return response;
		}
		if(request.getParams()==null){
			request.setParams(new String[]{});
		}
		if(request.getPhone()==null){
			response.setMessage("请输入短信接收手机号码");
			return response;
		}
		Phone phone = request.getPhone();
		if(StringTools.isNullOrNone(phone.getMobile())){
			response.setMessage("请输入短信接收手机号码");
			return response;
		}
		if(!StringTools.isPhone(phone.getMobile())){
        	response.setMessage("请输入11位有效手机号码");
            return response;
        }
		if(StringTools.isNullOrNone(phone.getNationCode())){
			response.setMessage("请输入手机号码对应的国家码");
            return response;
		}
		if(request.getTemplateId()==null){
			response.setMessage("请选择短信模板");
            return response;
		}
		request.setTime(DateTools.now().getTime()/1000);
		request.setRandom(StringTools.getRandomCode(6));
		request.setExt(StringTools.getSys36SeqNo());
		request.setExtend(StringTools.isNullOrNone(request.getExtend())?"":request.getExtend());
		request.setSign(StringTools.isNullOrNone(request.getSign())?"":request.getSign());
		StringBuffer buffer = new StringBuffer();
		buffer.append("appkey="+appKeySystemConfigBean.getPropertyValue())
			.append("&random="+request.getRandom())
			.append("&time="+request.getTime())
			.append("&mobile="+phone.getMobile());
		request.setSig(SHA256Tools.encrypt(buffer.substring(0)));
		System.out.println(JsonTools.toJsonString(request));
		String httpResponse = httpService.post(request.getUrl()+"?sdkappid="+request.getSdkAppid()+"&random="+request.getRandom(), JsonTools.covertObject(request, HashMap.class), "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TenXunSmsSendResponse.class);
		if(response.getResult()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

}
