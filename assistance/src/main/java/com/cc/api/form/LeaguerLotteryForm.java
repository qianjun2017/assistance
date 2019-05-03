package com.cc.api.form;

public class LeaguerLotteryForm {

	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 抽奖
	 */
	private Long lotteryId;
	
	/**
	 * 分享抽奖
	 */
	private Long shareId;

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
	 * @return the shareId
	 */
	public Long getShareId() {
		return shareId;
	}

	/**
	 * @param shareId the shareId to set
	 */
	public void setShareId(Long shareId) {
		this.shareId = shareId;
	}
}
