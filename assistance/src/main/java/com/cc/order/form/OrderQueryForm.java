package com.cc.order.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.form.QueryForm;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
public class OrderQueryForm extends QueryForm {
	
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
     * 订单编码
     */
    private String code;

    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 订单支付方式
     */
    private String payMethod;
    
    /**
     * 订单支付状态
     */
    private String payStatus;
    
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
	 * @return the payStatus
	 */
	public String getPayStatus() {
		return payStatus;
	}

	/**
	 * @param payStatus the payStatus to set
	 */
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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
}
