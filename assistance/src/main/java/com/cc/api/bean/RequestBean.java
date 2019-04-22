/**
 * 
 */
package com.cc.api.bean;

/**
 * @author Administrator
 *
 */
public class RequestBean {

	/**
	 * 应用
	 */
	private String appCode;
	
	/**
	 * 请求时间错
	 */
	private Long timestamp;
	
	/**
	 * 接口版本
	 */
	private String version;
	
	/**
	 * 参数签名
	 */
	private String sign;
	
	/**
	 * 加密参数
	 */
	private String eValue;
	
	/**
	 * 访问令牌
	 */
	private String token;
	
	/**
	 * 小程序提交的formId
	 */
	private String formId;

	/**
	 * @return the appCode
	 */
	public String getAppCode() {
		return appCode;
	}

	/**
	 * @param appCode the appCode to set
	 */
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	/**
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the eValue
	 */
	public String geteValue() {
		return eValue;
	}

	/**
	 * @param eValue the eValue to set
	 */
	public void seteValue(String eValue) {
		this.eValue = eValue;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}
}
