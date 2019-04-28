package com.cc.api.form;

public class EmailForm {

	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 会员
	 */
	private Long leaguerId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getLeaguerId() {
		return leaguerId;
	}

	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}
}
