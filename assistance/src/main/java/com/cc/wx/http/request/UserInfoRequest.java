/**
 * 
 */
package com.cc.wx.http.request;

/**
 * @author Administrator
 *
 */
public class UserInfoRequest {

	/**
	 * openid
	 */
	private String openid;

	/**
	 * 授权凭证
	 */
	private String accessToken;
	
	/**
	 * 用户信息地址
	 */
	private String url = "https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * 语言版本
	 */
	private String lang = "zh_CN";

	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}
}
