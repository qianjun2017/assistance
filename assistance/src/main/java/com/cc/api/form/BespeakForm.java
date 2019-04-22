package com.cc.api.form;

import java.util.Date;

public class BespeakForm {
	
	/**
	 * 会员
	 */
	private Long customerId;

	/**
	 * 看车时间
	 */
	private Date bespeakTime;
	
	/**
	 * 经销商
	 */
	private Long franchiserId;
	
	/**
	 * 汽车顾问
	 */
	private Long consultantId;

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
	 * @return the bespeakTime
	 */
	public Date getBespeakTime() {
		return bespeakTime;
	}

	/**
	 * @param bespeakTime the bespeakTime to set
	 */
	public void setBespeakTime(Date bespeakTime) {
		this.bespeakTime = bespeakTime;
	}

	/**
	 * @return the franchiserId
	 */
	public Long getFranchiserId() {
		return franchiserId;
	}

	/**
	 * @param franchiserId the franchiserId to set
	 */
	public void setFranchiserId(Long franchiserId) {
		this.franchiserId = franchiserId;
	}

	/**
	 * @return the consultantId
	 */
	public Long getConsultantId() {
		return consultantId;
	}

	/**
	 * @param consultantId the consultantId to set
	 */
	public void setConsultantId(Long consultantId) {
		this.consultantId = consultantId;
	}
}
