/**
 * 
 */
package com.cc.order.bean;

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
@Table(name="t_refund")
public class RefundBean extends BaseOrm<RefundBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4155839694860329768L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	/**
	 * 支付
	 */
	private Long payId;
	
	/**
	 * 订单
	 */
	private Long orderId;
	
	/**
	 * 子订单
	 */
	private Long orderSubId;

    /**
     * 退款流水号
     */
    private String code;

    /**
     * 外部退款流水号
     */
    private String serial;

    /**
     * 应退金额
     */
    private Long refundable;
    
    /**
     * 实退金额
     */
    private Long refund;
    
    /**
     * 退款状态
     */
    private String status;

    /**
     * 发起退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 完成退款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date refundTime;
    
    /**
     * 支付账户
     */
    private String fromAccount;

    /**
     * 收款账户
     */
    private String toAccount;

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
	 * @return the payId
	 */
	public Long getPayId() {
		return payId;
	}

	/**
	 * @param payId the payId to set
	 */
	public void setPayId(Long payId) {
		this.payId = payId;
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

	/**
	 * @return the orderSubId
	 */
	public Long getOrderSubId() {
		return orderSubId;
	}

	/**
	 * @param orderSubId the orderSubId to set
	 */
	public void setOrderSubId(Long orderSubId) {
		this.orderSubId = orderSubId;
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
	 * @return the serial
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * @param serial the serial to set
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * @return the refundable
	 */
	public Long getRefundable() {
		return refundable;
	}

	/**
	 * @param refundable the refundable to set
	 */
	public void setRefundable(Long refundable) {
		this.refundable = refundable;
	}

	/**
	 * @return the refund
	 */
	public Long getRefund() {
		return refund;
	}

	/**
	 * @param refund the refund to set
	 */
	public void setRefund(Long refund) {
		this.refund = refund;
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
	 * @return the refundTime
	 */
	public Date getRefundTime() {
		return refundTime;
	}

	/**
	 * @param refundTime the refundTime to set
	 */
	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	/**
	 * @return the fromAccount
	 */
	public String getFromAccount() {
		return fromAccount;
	}

	/**
	 * @param fromAccount the fromAccount to set
	 */
	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	/**
	 * @return the toAccount
	 */
	public String getToAccount() {
		return toAccount;
	}

	/**
	 * @param toAccount the toAccount to set
	 */
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

}
