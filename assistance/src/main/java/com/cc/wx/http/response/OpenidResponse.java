/**
 * 
 */
package com.cc.wx.http.response;

/**
 * @author Administrator
 *
 */
public class OpenidResponse {
	
	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * openid
	 */
	private String openid;

	/**
	 * 授权凭证
	 */
	private String accessToken;
	
	/**
	 * 刷新授权凭证
	 */
	private String refreshToken;

	/**
	 * 
	 */
	public OpenidResponse() {
		this.success = Boolean.FALSE;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

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
	 * @return the refreshToken
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * @param refreshToken the refreshToken to set
	 */
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
