/**
 * 
 */
package com.cc.wx.http.request;

/**
 * @author Administrator
 *
 */
public class AccessTokenRequest {

	private String appid;
	
	private String secret;
	
	private String grantType = "client_credential";
	
	private String url = "https://api.weixin.qq.com/cgi-bin/token";

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
