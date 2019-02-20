package com.cc.wx.controller;

import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.wx.form.CodeForm;
import com.cc.wx.form.WXACodeForm;
import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.request.model.Color;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.http.response.OpenidResponse;
import com.cc.wx.http.response.WXACodeResponse;
import com.cc.wx.service.WeiXinService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static Logger logger = LoggerFactory.getLogger(WeiXinController.class);
	
    @Autowired
    private WeiXinService weiXinService;

    @Autowired
    private SystemConfigService systemConfigService;
    
    /**
	 * 小程序调用凭证
	 */
	private String accessToken;
	
	/**
	 * 小程序调用凭证过期时间
	 */
	private Date accessTokenExpired;
    
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
    
    /**
	 * 获取access_token
	 * @return
	 */
	private String fetchAccessToken(){
		if(!StringTools.isNullOrNone(accessToken) && accessTokenExpired!=null && accessTokenExpired.after(new Date())){
			return accessToken;
		}else{
			Calendar calendar = Calendar.getInstance();
			AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
			SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.appid");
			if(appidSystemConfigBean!=null){
				accessTokenRequest.setAppid(appidSystemConfigBean.getPropertyValue());
			}
			SystemConfigBean secretSystemConfigBean = systemConfigService.querySystemConfigBean("wx.secret");
			if(secretSystemConfigBean!=null){
				accessTokenRequest.setSecret(secretSystemConfigBean.getPropertyValue());
			}
			AccessTokenResponse accessTokenResponse = weiXinService.queryAccessToken(accessTokenRequest);
			if(accessTokenResponse.isSuccess()){
				accessToken = accessTokenResponse.getAccessToken();
				calendar.add(Calendar.SECOND, accessTokenResponse.getExpiresIn());
				accessTokenExpired = calendar.getTime();
			}else{
				logger.info("获取access_token错误---"+accessTokenResponse.getMessage());
				accessToken = null;
			}
			return accessToken;
		}
	}
	
	/**
	 * 生成小程序码
	 * @param scene
	 * @param page
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/acode", method = RequestMethod.GET)
	public void createWXACode(@ModelAttribute WXACodeForm form, HttpServletResponse response) throws IOException{
		WXACodeRequest wxaCodeRequest = new WXACodeRequest();
		wxaCodeRequest.setAccessToken(fetchAccessToken());
		wxaCodeRequest.setScene(form.getScene());
		wxaCodeRequest.setPage(form.getPage());
		wxaCodeRequest.setAutoColor(form.getAutoColor());
		wxaCodeRequest.setIsHyaline(form.getIsHyaline());
		wxaCodeRequest.setWidth(form.getWidth());
		if(form.getR()!=null && form.getG()!=null && form.getB()!=null){
			Color color = new Color();
			color.setB(form.getB());
			color.setG(form.getB());
			color.setR(form.getR());
			wxaCodeRequest.setLineColor(color);
		}
		WXACodeResponse wxaCodeResponse = weiXinService.createWXACode(wxaCodeRequest);
		if(!wxaCodeResponse.isSuccess()){
			logger.info("获取小程序码错误---"+wxaCodeResponse.getMessage());
			return;
		}
		response.setContentType("image/jpeg");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(wxaCodeResponse.getAcode());
		outputStream.flush();
		outputStream.close();
	}
}
