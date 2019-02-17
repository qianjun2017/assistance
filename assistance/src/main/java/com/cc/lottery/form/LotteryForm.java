/**
 * 
 */
package com.cc.lottery.form;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class LotteryForm {
	
	private Long id;
	
	/**
	 * 所属客户
	 */
	private Long customerId;
	
	/**
	 * 所属客户微信openid
	 */
	private String openid;
	
	/**
	 * 期数
	 */
	private Long no;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 每个人最多抽奖次数
	 */
	private Integer count;
	
	/**
	 * 奖项
	 */
	private List<LotteryPrizeForm> prizeList;
	
	/**
	 * 最后兑奖时间
	 */
	private Date lastExchangeTime;

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
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the prizeList
	 */
	public List<LotteryPrizeForm> getPrizeList() {
		return prizeList;
	}

	/**
	 * @param prizeList the prizeList to set
	 */
	public void setPrizeList(List<LotteryPrizeForm> prizeList) {
		this.prizeList = prizeList;
	}

	/**
	 * @return the lastExchangeTime
	 */
	public Date getLastExchangeTime() {
		return lastExchangeTime;
	}

	/**
	 * @param lastExchangeTime the lastExchangeTime to set
	 */
	public void setLastExchangeTime(Date lastExchangeTime) {
		this.lastExchangeTime = lastExchangeTime;
	}
}
