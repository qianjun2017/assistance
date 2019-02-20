/**
 * 
 */
package com.cc.wx.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
public class AccessTokenResponse {

	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 小程序调用凭证
	 */
	@JsonProperty(value="access_token")
	private String accessToken;
	
	/**
	 * 小程序调用凭证有效时间
	 */
	@JsonProperty(value="expires_in")
	private Integer expiresIn;
	
	/**
	 * 错误码
	 */
	private Integer errcode;
	
	/**
	 * 错误信息
	 */
	private String errmsg;
	/**
	 * 
	 */
	public AccessTokenResponse() {
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
	 * @return the expiresIn
	 */
	public Integer getExpiresIn() {
		return expiresIn;
	}
	/**
	 * @param expiresIn the expiresIn to set
	 */
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	/**
	 * @return the errcode
	 */
	public Integer getErrcode() {
		return errcode;
	}
	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}
	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}
	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
