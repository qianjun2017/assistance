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
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.response.OpenidResponse;
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
			Map<String, String> map = JsonTools.toObject(httpResponse, Map.class);
			if(map.containsKey("openid")){
				response.setSuccess(Boolean.TRUE);
				response.setOpenid(map.get("openid"));
			}else{
				response.setMessage(map.get("errmsg"));
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

}
