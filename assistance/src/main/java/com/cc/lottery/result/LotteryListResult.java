/**
 * 
 */
package com.cc.lottery.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class LotteryListResult {

	private Long id;
	
	/**
	 * 所属客户
	 */
	private Long customerId;
	
	/**
	 * 客户微信opernid
	 */
	private String openid;
	
	/**
	 * 客户微信昵称
	 */
	private String nickName;
	
	/**
	 * 期数
	 */
	private Long no;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 是否需要分享
	 */
	private Boolean share;

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
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the no
	 */
	public Long getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(Long no) {
		this.no = no;
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
	 * @return the share
	 */
	public Boolean getShare() {
		return share;
	}

	/**
	 * @param share the share to set
	 */
	public void setShare(Boolean share) {
		this.share = share;
	}
}
