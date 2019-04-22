/**
 * 
 */
package com.cc.api.form;

/**
 * @author Administrator
 *
 */
public class LeaguerForm {
	
	/**
	 * 会员
	 */
	private Long leaguerId;

	/**
	 * 会员姓名
	 */
	private String name;
	
	/**
	 * 会员手机号
	 */
	private String phone;

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
