package com.cc.sms.tx.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
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
		SystemConfigBean appIdSystemConfigBean = systemConfigService.querySystemConfigBean("tenxun.sms.appid");
		if(appIdSystemConfigBean==null || StringTools.isNullOrNone(appIdSystemConfigBean.getPropertyValue())){
			response.setMessage("请配置腾讯短信服务应用appid");
			return response;
		}
		request.setSdkAppid(appIdSystemConfigBean.getPropertyValue());
		SystemConfigBean appKeySystemConfigBean = systemConfigService.querySystemConfigBean("tenxun.sms.appkey");
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
		if(!StringTools.matches(phone.getMobile(), "^1[34578]\\d{9}$")){
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
		request.setTime(DateTools.now().getTime());
		request.setRandom(StringTools.getRandomCode(6));
		StringBuffer buffer = new StringBuffer();
		buffer.append("appkey="+appKeySystemConfigBean.getPropertyValue())
			.append("&random="+request.getRandom())
			.append("&time="+request.getTime())
			.append("&mobile="+phone.getMobile());
		request.setSig(SHA256Tools.encrypt(buffer.substring(0)));
		String httpResponse = httpService.post(request.getUrl()+"?sdkappid="+request.getSdkAppid()+"&random="+request.getRandom(), JsonTools.covertObject(request, HashMap.class), "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TenXunSmsSendResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

}
