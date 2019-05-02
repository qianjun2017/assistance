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
@Table(name="t_lottery_retailer")
public class LotteryRetailerBean extends BaseOrm<LotteryRetailerBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -138282722991550659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 店铺名称
	 */
	private String store;
	
	/**
	 * 店铺地址
	 */
	private String address;
	
	/**
	 * 服务截止时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date retailerTime;

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
	 * @return the retailerTime
	 */
	public Date getRetailerTime() {
		return retailerTime;
	}

	/**
	 * @param retailerTime the retailerTime to set
	 */
	public void setRetailerTime(Date retailerTime) {
		this.retailerTime = retailerTime;
	}
}
