/**
 * 
 */
package com.cc.lottery.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class LotteryLeaguerQueryForm extends QueryForm {
	
	/**
	 * 抽奖
	 */
	private Long lotteryId;
	
	/**
	 * 中奖会员
	 */
	private Long leaguerId;
	
	/**
	 * 中奖会员微信昵称
	 */
	private String nickName;
	
	/**
	 * 中奖状态
	 */
	private String status;
	
	/**
	 * 商家会员
	 */
	private Long retailerId;
	
	/**
	 * 是否中奖
	 */
	private Boolean prize;
	
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
	 * @return the retailerId
	 */
	public Long getRetailerId() {
		return retailerId;
	}

	/**
	 * @param retailerId the retailerId to set
	 */
	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
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

}
