package com.cc.sms.tenxun.http.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Phone {

	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 国家码
	 */
	@JsonProperty(value="nationcode")
	private String nationCode;

	public Phone(String mobile, String nationCode) {
		this.mobile = mobile;
		this.nationCode = nationCode;
	}

	public Phone(String mobile) {
		this.mobile = mobile;
		this.nationCode = "86";
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the nationCode
	 */
	public String getNationCode() {
		return nationCode;
	}

	/**
	 * @param nationCode the nationCode to set
	 */
	public void setNationCode(String nationCode) {
		this.nationCode = nationCode;
	}
}
