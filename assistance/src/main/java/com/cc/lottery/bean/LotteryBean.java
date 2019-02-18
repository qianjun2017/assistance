/**
 * 
 */
package com.cc.lottery.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_lottery")
public class LotteryBean extends BaseOrm<LotteryBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5654585324331476090L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 所属客户
	 */
	private Long customerId;
	
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
	 * 最后兑奖时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastExchangeTime;
	
	/**
	 * 每个人最多抽奖次数
	 */
	@Column(name="mCount")
	private Integer count;
	
	/**
	 * 奖项
	 */
	@Transient
	private List<LotteryPrizeBean> prizeList;
	
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

	/**
	 * @return the prizeList
	 */
	public List<LotteryPrizeBean> getPrizeList() {
		return prizeList;
	}

	/**
	 * @param prizeList the prizeList to set
	 */
	public void setPrizeList(List<LotteryPrizeBean> prizeList) {
		this.prizeList = prizeList;
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