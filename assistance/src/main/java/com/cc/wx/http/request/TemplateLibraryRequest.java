/**
 * 
 */
package com.cc.wx.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ws_yu
 *
 */
public class TemplateLibraryRequest {

	/**
	 * 小程序调用凭证
	 */
	@JsonIgnore
	private String accessToken;
	
	/**
	 * 模板标题id，可通过接口获取，也可登录小程序后台查看获取
	 */
	private String id;
	
	@JsonIgnore
	private String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get";

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
