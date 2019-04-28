/**
 * 
 */
package com.cc.wx.http.request.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name="root")
public class ReqInfo {

	/**
	 * 微信订单号
	 */
	@XmlElement(name="transaction_id")
	@JsonProperty(value="transaction_id")
	private String transactionId;
	
	/**
	 * 商户订单号
	 */
	@XmlElement(name="out_trade_no")
	@JsonProperty(value="out_trade_no")
	private String outTradeNo;
	
	/**
	 * 商户退款单号
	 */
	@XmlElement(name="out_refund_no")
	@JsonProperty(value="out_refund_no")
	private String outRefundNo;
	
	/**
	 * 微信退款单号
	 */
	@XmlElement(name="refund_id")
	@JsonProperty(value="refund_id")
	private String refundId;
	
	/**
	 * 申请退款金额
	 */
	@XmlElement(name="refund_fee")
	@JsonProperty(value="refund_fee")
	private Integer refundFee;
	
	/**
	 * 退款金额
	 */
	@XmlElement(name="settlement_refund_fee")
	@JsonProperty(value="settlement_refund_fee")
	private Integer settlementRefundFee;
	
	/**
	 * 订单金额
	 */
	@XmlElement(name="total_fee")
	@JsonProperty(value="total_fee")
	private Integer totalFee;
	
	/**
	 * 应结订单金额
	 */
	@XmlElement(name="settlement_total_fee")
	@JsonProperty(value="settlement_total_fee")
	private Integer settlementTotalFee;
	
	/**
	 * 退款状态
	 */
	@XmlElement(name="refund_status")
	@JsonProperty(value="refund_status")
	private String refundStatus;

	/**
	 * 退款成功时间
	 */
	@XmlElement(name="success_time")
	@JsonProperty(value="success_time")
	private String successTime;
	
	/**
	 * 退款入账账户
	 */
	@XmlElement(name="refund_recv_accout")
	@JsonProperty(value="refund_recv_accout")
	private String refundRecvAccout;
	
	/**
	 * 退款资金来源
	 */
	@XmlElement(name="refund_account")
	@JsonProperty(value="refund_account")
	private String refundAccount;
	
	/**
	 * 退款发起来源
	 */
	@XmlElement(name="refund_request_source")
	@JsonProperty(value="refund_request_source")
	private String refundRequestSource;

	/**
	 * @return the transactionId
	 */
	@XmlTransient
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the outTradeNo
	 */
	@XmlTransient
	public String getOutTradeNo() {
		return outTradeNo;
	}

	/**
	 * @param outTradeNo the outTradeNo to set
	 */
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	/**
	 * @return the outRefundNo
	 */
	@XmlTransient
	public String getOutRefundNo() {
		return outRefundNo;
	}

	/**
	 * @param outRefundNo the outRefundNo to set
	 */
	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	/**
	 * @return the refundId
	 */
	@XmlTransient
	public String getRefundId() {
		return refundId;
	}

	/**
	 * @param refundId the refundId to set
	 */
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	/**
	 * @return the refundFee
	 */
	@XmlTransient
	public Integer getRefundFee() {
		return refundFee;
	}

	/**
	 * @param refundFee the refundFee to set
	 */
	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * @return the settlementRefundFee
	 */
	@XmlTransient
	public Integer getSettlementRefundFee() {
		return settlementRefundFee;
	}

	/**
	 * @param settlementRefundFee the settlementRefundFee to set
	 */
	public void setSettlementRefundFee(Integer settlementRefundFee) {
		this.settlementRefundFee = settlementRefundFee;
	}

	/**
	 * @return the totalFee
	 */
	@XmlTransient
	public Integer getTotalFee() {
		return totalFee;
	}

	/**
	 * @param totalFee the totalFee to set
	 */
	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * @return the settlementTotalFee
	 */
	@XmlTransient
	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	/**
	 * @param settlementTotalFee the settlementTotalFee to set
	 */
	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	/**
	 * @return the refundStatus
	 */
	@XmlTransient
	public String getRefundStatus() {
		return refundStatus;
	}

	/**
	 * @param refundStatus the refundStatus to set
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * @return the successTime
	 */
	@XmlTransient
	public String getSuccessTime() {
		return successTime;
	}

	/**
	 * @param successTime the successTime to set
	 */
	public void setSuccessTime(String successTime) {
		this.successTime = successTime;
	}

	/**
	 * @return the refundRecvAccout
	 */
	@XmlTransient
	public String getRefundRecvAccout() {
		return refundRecvAccout;
	}

	/**
	 * @param refundRecvAccout the refundRecvAccout to set
	 */
	public void setRefundRecvAccout(String refundRecvAccout) {
		this.refundRecvAccout = refundRecvAccout;
	}

	/**
	 * @return the refundAccount
	 */
	@XmlTransient
	public String getRefundAccount() {
		return refundAccount;
	}

	/**
	 * @param refundAccount the refundAccount to set
	 */
	public void setRefundAccount(String refundAccount) {
		this.refundAccount = refundAccount;
	}

	/**
	 * @return the refundRequestSource
	 */
	@XmlTransient
	public String getRefundRequestSource() {
		return refundRequestSource;
	}

	/**
	 * @param refundRequestSource the refundRequestSource to set
	 */
	public void setRefundRequestSource(String refundRequestSource) {
		this.refundRequestSource = refundRequestSource;
	}
}
