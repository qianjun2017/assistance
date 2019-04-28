/**
 * 
 */
package com.cc.order.form;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class OrderForm {

	/**
	 * 收货地址
	 */
	private Long addressId;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 订单编号
	 */
	private String code;
	
	/**
	 * 支付方式
	 */
	private String payMethod;
	
	/**
	 * 订单
	 */
	private List<SubOrderForm> orderList;

	/**
	 * @return the addressId
	 */
	public Long getAddressId() {
		return addressId;
	}

	/**
	 * @param addressId the addressId to set
	 */
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
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
	 * @return the orderList
	 */
	public List<SubOrderForm> getOrderList() {
		return orderList;
	}

	/**
	 * @param orderList the orderList to set
	 */
	public void setOrderList(List<SubOrderForm> orderList) {
		this.orderList = orderList;
	}
}
