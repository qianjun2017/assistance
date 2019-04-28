package com.cc.order.form;

import java.util.List;

public class SubOrderForm {

	/**
	 * 商家
	 */
	private Long sellerId;
	
	/**
	 * 卖家
	 */
	private String sellerName;
	
	/**
	 * 订单金额
	 */
	private Long total;
	
	/**
	 * 优惠金额
	 */
	private Long discount;
	
	/**
	 * 订单商品
	 */
	private List<SubOrderGoodsForm> goodsList;

	/**
	 * 备注
	 */
	private String remark;

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
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the discount
	 */
	public Long getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	/**
	 * @return the goodsList
	 */
	public List<SubOrderGoodsForm> getGoodsList() {
		return goodsList;
	}

	/**
	 * @param goodsList the goodsList to set
	 */
	public void setGoodsList(List<SubOrderGoodsForm> goodsList) {
		this.goodsList = goodsList;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
