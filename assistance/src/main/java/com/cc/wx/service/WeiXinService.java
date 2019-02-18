/**
 * 
 */
package com.cc.wx.service;

import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.response.OpenidResponse;

/**
 * @author Administrator
 *
 */
public interface WeiXinService {

	/**
	 * 获取用户对应的openid
	 * @param request
	 * @return
	 */
	OpenidResponse queryOpenid(OpenidRequest request);
	
}
