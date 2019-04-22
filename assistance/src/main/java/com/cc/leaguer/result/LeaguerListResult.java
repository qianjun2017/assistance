/**
 * 
 */
package com.cc.leaguer.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class LeaguerListResult {

	private Long id;
	
	/**
	 * 会员姓名
	 */
	private String name;
	
	/**
	 * 会员微信头像
	 */
	private String avatarUrl;
	
	/**
	 * 会员状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;
	
	/**
	 * 会员卡级别
	 */
	private String cardLevel;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 积分
	 */
	private Long integration;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the cardLevel
	 */
	public String getCardLevel() {
		return cardLevel;
	}

	/**
	 * @param cardLevel the cardLevel to set
	 */
	public void setCardLevel(String cardLevel) {
		this.cardLevel = cardLevel;
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

	/**
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}
}
