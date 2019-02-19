/**
 * 
 */
package com.cc.lottery.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_lottery_customer")
public class LotteryCustomerBean extends BaseOrm<LotteryCustomerBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1875018808191075345L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * 是否中奖
	 */
	private Boolean prize;
	
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
