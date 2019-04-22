/**
 * 
 */
package com.cc.api.form;

/**
 * @author Administrator
 *
 */
public class CustomerForm {
	
	/**
	 * 会员
	 */
	private Long customerId;

	/**
	 * 会员姓名
	 */
	private String name;
	
	/**
	 * 会员手机号
	 */
	private String phone;

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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
