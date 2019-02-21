/**
 * 
 */
package com.cc.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.cc.common.tools.JsonTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.StringTools;
import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.http.response.OpenidResponse;
import com.cc.wx.http.response.WXACodeResponse;
import com.cc.wx.service.WeiXinService;

/**
 * @author Administrator
 *
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
	
	@Autowired
	private HttpService httpService;
	
	@Override
	public OpenidResponse queryOpenid(OpenidRequest request) {
		OpenidResponse response = new OpenidResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getCode())) {
			response.setMessage("请输入登录code");
			return response;
		}
		if (StringTools.isNullOrNone(request.getSecret())) {
			response.setMessage("请输入应用secret");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", request.getAppid());
		paramMap.put("secret", request.getSecret());
		paramMap.put("js_code", request.getCode());
		paramMap.put("grant_type", request.getGrantType());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		Map<String, String> map = JsonTools.toObject(httpResponse, Map.class);
		if(map.containsKey("openid")){
			response.setSuccess(Boolean.TRUE);
			response.setOpenid(map.get("openid"));
		}else{
			response.setMessage(map.get("errmsg"));
		}
		return response;
	}

	@Override
	public AccessTokenResponse queryAccessToken(AccessTokenRequest request) {
		AccessTokenResponse response = new AccessTokenResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getSecret())) {
			response.setMessage("请输入应用secret");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", request.getAppid());
		paramMap.put("secret", request.getSecret());
		paramMap.put("grant_type", request.getGrantType());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, AccessTokenResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public WXACodeResponse createWXACode(WXACodeRequest request) {
		WXACodeResponse response = new WXACodeResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getScene())) {
			response.setMessage("请输入小程序码参数");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("scene", request.getScene());
		if(!StringTools.isNullOrNone(request.getPage())){
			paramMap.put("page", request.getPage());
		}
		if(request.getWidth()!=null){
			paramMap.put("width", request.getWidth());
		}
		if(request.getAutoColor()!=null){
			paramMap.put("auto_color", request.getAutoColor());
		}
		if(request.getIsHyaline()!=null){
			paramMap.put("is_hyaline", request.getIsHyaline());
		}
		if(request.getLineColor()!=null){
			paramMap.put("line_color", request.getLineColor());
		}
		byte[] httpResponse = httpService.postForBytes(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (httpResponse==null) {
			response.setMessage("http返回值为空");
			return response;
		}
		try{
			response = JsonTools.toObject(new String(httpResponse), WXACodeResponse.class);
			response.setMessage(response.getErrmsg());
			response.setSuccess(Boolean.FALSE);
		} catch (Exception e) {
			response.setAcode(httpResponse);
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

}
