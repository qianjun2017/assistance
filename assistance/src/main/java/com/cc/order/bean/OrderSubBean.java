/**
 * 
 */
package com.cc.order.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author ws_yu
 *
 */
@Table(name="t_order_sub")
public class OrderSubBean extends BaseOrm<OrderSubBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497803249825769286L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	/**
	 * 父订单
	 */
	private Long orderId;
	
	/**
     * 订单总额   = 应收金额   + 优惠金额
     */
    private Long total;
    
    /**
     * 应收金额 
     */
    private Long receivable;
    
    /**
     * 实收金额   = 应收金额 - 退款金额
     */
    private Long receipts;
    
    /**
     * 优惠金额
     */
    private Long discount;

    /**
     * 退款金额
     */
    private Long refund;
    
    /**
     * 店铺
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 卖家
     */
    private Long sellerId;

    /**
     * 卖家名称
     */
    private String sellerName;
    
    /**
     * 订单状态
     */
    private String status;
    
    /**
     * 订单备注
     */
    private String remark;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the orderId
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getReceivable() {
		return receivable;
	}

	public void setReceivable(Long receivable) {
		this.receivable = receivable;
	}

	public Long getReceipts() {
		return receipts;
	}

	public void setReceipts(Long receipts) {
		this.receipts = receipts;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getRefund() {
		return refund;
	}

	public void setRefund(Long refund) {
		this.refund = refund;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void setId(Long id) {
		this.id = id;
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
