/**
 * 
 */
package com.cc.wx.http.request;

/**
 * @author Administrator
 *
 */
public class OpenidRequest {

	private String appid;
	
	private String secret;
	
	private String code;
	
	private String grantType = "authorization_code";
	
	private String url = "https://api.weixin.qq.com/sns/oauth2/access_token";

	/**
	 * @return the appid
	 */
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * @param secret the secret to set
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the grantType
	 */
	public String getGrantType() {
		return grantType;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
