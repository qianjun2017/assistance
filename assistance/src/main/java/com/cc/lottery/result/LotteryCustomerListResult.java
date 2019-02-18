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
public class LotteryCustomerListResult {

	private Long id;
	
	/**
	 * 中奖客户
	 */
	private Long customerId;
	
	/**
	 * 抽奖
	 */
	private Long lotteryId;
	
	/**
	 * 奖项
	 */
	private Long lotteryPrizeId;
	
	/**
	 * 期数
	 */
	private Long no;
	
	/**
	 * 客户微信opernid
	 */
	private String openid;
	
	/**
	 * 客户微信昵称
	 */
	private String nickName;
	
	/**
	 * 奖项名称
	 */
	private String name;
	
	/**
	 * 商家店名
	 */
	private String store;
	
	/**
	 * 商家电话
	 */
	private String phone;
	
	/**
	 * 商家地址
	 */
	private String address;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 中奖时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 兑奖时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date exchangeTime;
	
	/**
	 * 是否中奖
	 */
	private Boolean prize;
	
	/**
	 * 是否已分享
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
	 * @return the lotteryId
	 */
	public Long getLotteryId() {
		return lotteryId;
	}

	/**
	 * @param lotteryId the lotteryId to set
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	/**
	 * @return the lotteryPrizeId
	 */
	public Long getLotteryPrizeId() {
		return lotteryPrizeId;
	}

	/**
	 * @param lotteryPrizeId the lotteryPrizeId to set
	 */
	public void setLotteryPrizeId(Long lotteryPrizeId) {
		this.lotteryPrizeId = lotteryPrizeId;
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
	 * @return the store
	 */
	public String getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(String store) {
		this.store = store;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
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
	 * @return the exchangeTime
	 */
	public Date getExchangeTime() {
		return exchangeTime;
	}

	/**
	 * @param exchangeTime the exchangeTime to set
	 */
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	/**
	 * @return the prize
	 */
	public Boolean getPrize() {
		return prize;
	}

	/**
	 * @param prize the prize to set
	 */
	public void setPrize(Boolean prize) {
		this.prize = prize;
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
