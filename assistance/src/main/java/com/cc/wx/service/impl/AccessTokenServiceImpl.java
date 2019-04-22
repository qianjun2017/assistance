/**
 * 
 */
package com.cc.wx.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.tools.StringTools;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.service.AccessTokenService;
import com.cc.wx.service.WeiXinService;

/**
 * @author ws_yu
 *
 */
@Service
public class AccessTokenServiceImpl implements AccessTokenService {
	
	private static Logger logger = LoggerFactory.getLogger(AccessTokenService.class);
	
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

	@Override
	public String queryAccessToken() {
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
}
