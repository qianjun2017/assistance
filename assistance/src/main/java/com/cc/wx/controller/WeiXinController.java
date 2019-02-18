package com.cc.wx.controller;

import com.cc.common.web.Response;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.wx.form.CodeForm;
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.response.OpenidResponse;
import com.cc.wx.service.WeiXinService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yuanwenshu on 2018/9/20.
 */
@Controller
@RequestMapping("/wx")
public class WeiXinController {
	
    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private SystemConfigService systemConfigService;
    
    /**
     * 获取微信用户的openid
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/openid", method = RequestMethod.GET)
    public Response<String> queryUserOpenid(@ModelAttribute CodeForm form){
    	Response<String> response = new Response<String>();
    	OpenidRequest openidRequest = new OpenidRequest();
		SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.appid");
		if(appidSystemConfigBean!=null){
			openidRequest.setAppid(appidSystemConfigBean.getPropertyValue());
		}
		SystemConfigBean secretSystemConfigBean = systemConfigService.querySystemConfigBean("wx.secret");
		if(secretSystemConfigBean!=null){
			openidRequest.setSecret(secretSystemConfigBean.getPropertyValue());
		}
		openidRequest.setCode(form.getCode());
		OpenidResponse openidResponse = weiXinService.queryOpenid(openidRequest);
		if(!openidResponse.isSuccess()){
			response.setMessage(openidResponse.getMessage());
			return response;
		}
		response.setData(openidResponse.getOpenid());
		response.setSuccess(Boolean.TRUE);
		return response;
    }
}
