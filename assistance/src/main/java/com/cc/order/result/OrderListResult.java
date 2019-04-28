/**
 * 
 */
package com.cc.order.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class OrderListResult {

	/**
	 * 订单
	 */
	private Long id;
	
	/**
	 * 订单编号
	 */
	private String code;
	
	/**
	 * 子订单
	 */
	private Long subId;
	
	/**
	 * 卖家
	 */
	private Long sellerId;
	
	/**
	 * 卖家名称
	 */
	private String sellerName;
	
	/**
	 * 店铺
	 */
	private Long shopId;
	
	/**
	 * 店铺名称
	 */
	private String shopName;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 会员昵称
	 */
	private String leaguerName;
	
	/**
	 * 订单状态
	 */
	private String status;
	
	/**
	 * 订单状态名称
	 */
	private String statusName;
	
	/**
	 * 订单支付方式
	 */
	private String payMethod;
	
	/**
	 * 订单支付方式名称
	 */
	private String payMethodName;
	
	/**
	 * 订单金额（元）
	 */
	private String total;
	
	/**
     * 下单时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the subId
	 */
	public Long getSubId() {
		return subId;
	}

	/**
	 * @param subId the subId to set
	 */
	public void setSubId(Long subId) {
		this.subId = subId;
	}

	/**
	 * @return the sellerId
	 */
	public Long getSellerId() {
		return sellerId;
	}

	/**
	 * @param sellerId the sellerId to set
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * @return the sellerName
	 */
	public String getSellerName() {
		return sellerName;
	}

	/**
	 * @param sellerName the sellerName to set
	 */
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	/**
	 * @return the shopId
	 */
	public Long getShopId() {
		return shopId;
	}

	/**
	 * @param shopId the shopId to set
	 */
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	/**
	 * @return the shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * @param shopName the shopName to set
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	 * @return the leaguerName
	 */
	public String getLeaguerName() {
		return leaguerName;
	}

	/**
	 * @param leaguerName the leaguerName to set
	 */
	public void setLeaguerName(String leaguerName) {
		this.leaguerName = leaguerName;
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
	 * @return the statusName
	 */
	public String getStatusName() {
		return statusName;
	}

	/**
	 * @param statusName the statusName to set
	 */
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	/**
	 * @return the payMethod
	 */
	public String getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return the payMethodName
	 */
	public String getPayMethodName() {
		return payMethodName;
	}

	/**
	 * @param payMethodName the payMethodName to set
	 */
	public void setPayMethodName(String payMethodName) {
		this.payMethodName = payMethodName;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
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
}
