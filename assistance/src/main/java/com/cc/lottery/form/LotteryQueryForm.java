/**
 * 
 */
package com.cc.lottery.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class LotteryQueryForm extends QueryForm {
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 抽奖
	 */
	private Long lotteryId;
	
	/**
	 * 所属会员微信昵称
	 */
	private String nickName;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;
	
	/**
	 * 是否需要分享
	 */
	private Boolean share;

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
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
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

}
