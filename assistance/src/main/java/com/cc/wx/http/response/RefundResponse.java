/**
 * 
 */
package com.cc.wx.http.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name="xml")
public class RefundResponse {

	/**
	 * 
	 */
	public RefundResponse() {
		this.success = Boolean.FALSE;
	}

	/**
	 * 返回状态
	 */
	@XmlTransient
	private boolean success;
	
	/**
	 * 返回信息
	 */
	@XmlTransient
	private String message;
	
	/**
	 * 返回状态码
	 */
	@XmlElement(name="return_code")
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@XmlElement(name="return_msg")
	private String returnMsg;
	
	/**
	 * 业务结果   SUCCESS/FAIL 
	 */
	@XmlElement(name="result_code")
	private String resultCode;
	
	/**
	 * 错误代码
	 */
	@XmlElement(name="err_code")
	private String errCode;
	
	/**
	 * 错误代码描述
	 */
	@XmlElement(name="err_code_des")
	private String errCodeDes;
	
	/**
	 * 小程序ID  调用接口提交的小程序ID
	 */
	@XmlElement
	private String appid;
	
	/**
	 * 商户号     调用接口提交的商户号 
	 */
	@XmlElement(name="mch_id")
	private String mchId;

	/**
	 * 随机字符串     微信返回的随机字符串 
	 */
	@XmlElement(name="nonce_str")
	private String nonceStr;
	
	/**
	 * 签名   微信返回的签名值 
	 */
	@XmlElement
	private String sign;
	
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
	 * 退款金额
	 */
	@XmlElement(name="refund_fee")
	@JsonProperty(value="refund_fee")
	private Integer refundFee;
	
	/**
	 * 应结退款金额
	 */
	@XmlElement(name="settlement_refund_fee")
	@JsonProperty(value="settlement_refund_fee")
	private Integer settlementRefundFee;
	
	/**
	 * 标价金额
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
	 * 标价币种
	 */
	@XmlElement(name="fee_type")
	@JsonProperty(value="fee_type")
	private String feeType;
	
	/**
	 * 现金支付金额
	 */
	@XmlElement(name="cash_fee")
	@JsonProperty(value="cash_fee")
	private Integer cashFee;
	
	/**
	 * 现金支付币种
	 */
	@XmlElement(name="cash_fee_type")
	@JsonProperty(value="cash_fee_type")
	private String cashFeeType;
	
	/**
	 * 现金退款金额
	 */
	@XmlElement(name="cash_refund_fee")
	@JsonProperty(value="cash_refund_fee")
	private Integer cashRefundFee;
	
	/**
	 * 代金券退款总金额
	 */
	@XmlElement(name="coupon_refund_fee")
	@JsonProperty(value="coupon_refund_fee")
	private Integer couponRefundFee;
	
	/**
	 * 退款代金券使用数量
	 */
	@XmlElement(name="coupon_refund_count")
	@JsonProperty(value="coupon_refund_count")
	private Integer couponRefundCount;
	
	/**
	 * 单个代金券退款金额
	 */
	@XmlElement(name="coupon_refund_fee_0")
	@JsonProperty(value="coupon_refund_fee_0")
	private Integer couponRefundFee0;
	
	/**
	 * 退款代金券ID
	 */
	@XmlElement(name="coupon_refund_id_0")
	@JsonProperty(value="coupon_refund_id_0")
	private String couponRefundId0;
	
	/**
	 * 单个代金券退款金额
	 */
	@XmlElement(name="coupon_refund_fee_1")
	@JsonProperty(value="coupon_refund_fee_1")
	private Integer couponRefundFee1;
	
	/**
	 * 退款代金券ID
	 */
	@XmlElement(name="coupon_refund_id_1")
	@JsonProperty(value="coupon_refund_id_1")
	private String couponRefundId1;

	/**
	 * @return the success
	 */
	@XmlTransient
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	@XmlTransient
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the returnCode
	 */
	@XmlTransient
	public String getReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode the returnCode to set
	 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return the returnMsg
	 */
	@XmlTransient
	public String getReturnMsg() {
		return returnMsg;
	}

	/**
	 * @param returnMsg the returnMsg to set
	 */
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	/**
	 * @return the resultCode
	 */
	@XmlTransient
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * @param resultCode the resultCode to set
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * @return the errCode
	 */
	@XmlTransient
	public String getErrCode() {
		return errCode;
	}

	/**
	 * @param errCode the errCode to set
	 */
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	/**
	 * @return the errCodeDes
	 */
	@XmlTransient
	public String getErrCodeDes() {
		return errCodeDes;
	}

	/**
	 * @param errCodeDes the errCodeDes to set
	 */
	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	/**
	 * @return the appid
	 */
	@XmlTransient
	public String getAppid() {
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid) {
		this.appid = appid;
	}

	/**
	 * @return the mchId
	 */
	@XmlTransient
	public String getMchId() {
		return mchId;
	}

	/**
	 * @param mchId the mchId to set
	 */
	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	/**
	 * @return the nonceStr
	 */
	@XmlTransient
	public String getNonceStr() {
		return nonceStr;
	}

	/**
	 * @param nonceStr the nonceStr to set
	 */
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	/**
	 * @return the sign
	 */
	@XmlTransient
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

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
	 * @return the feeType
	 */
	@XmlTransient
	public String getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	/**
	 * @return the cashFee
	 */
	@XmlTransient
	public Integer getCashFee() {
		return cashFee;
	}

	/**
	 * @param cashFee the cashFee to set
	 */
	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}

	/**
	 * @return the cashFeeType
	 */
	@XmlTransient
	public String getCashFeeType() {
		return cashFeeType;
	}

	/**
	 * @param cashFeeType the cashFeeType to set
	 */
	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	/**
	 * @return the cashRefundFee
	 */
	@XmlTransient
	public Integer getCashRefundFee() {
		return cashRefundFee;
	}

	/**
	 * @param cashRefundFee the cashRefundFee to set
	 */
	public void setCashRefundFee(Integer cashRefundFee) {
		this.cashRefundFee = cashRefundFee;
	}

	/**
	 * @return the couponRefundFee
	 */
	@XmlTransient
	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	/**
	 * @param couponRefundFee the couponRefundFee to set
	 */
	public void setCouponRefundFee(Integer couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}

	/**
	 * @return the couponRefundCount
	 */
	@XmlTransient
	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	/**
	 * @param couponRefundCount the couponRefundCount to set
	 */
	public void setCouponRefundCount(Integer couponRefundCount) {
		this.couponRefundCount = couponRefundCount;
	}

	/**
	 * @return the couponRefundFee0
	 */
	@XmlTransient
	public Integer getCouponRefundFee0() {
		return couponRefundFee0;
	}

	/**
	 * @param couponRefundFee0 the couponRefundFee0 to set
	 */
	public void setCouponRefundFee0(Integer couponRefundFee0) {
		this.couponRefundFee0 = couponRefundFee0;
	}

	/**
	 * @return the couponRefundId0
	 */
	@XmlTransient
	public String getCouponRefundId0() {
		return couponRefundId0;
	}

	/**
	 * @param couponRefundId0 the couponRefundId0 to set
	 */
	public void setCouponRefundId0(String couponRefundId0) {
		this.couponRefundId0 = couponRefundId0;
	}

	/**
	 * @return the couponRefundFee1
	 */
	@XmlTransient
	public Integer getCouponRefundFee1() {
		return couponRefundFee1;
	}

	/**
	 * @param couponRefundFee1 the couponRefundFee1 to set
	 */
	public void setCouponRefundFee1(Integer couponRefundFee1) {
		this.couponRefundFee1 = couponRefundFee1;
	}

	/**
	 * @return the couponRefundId1
	 */
	@XmlTransient
	public String getCouponRefundId1() {
		return couponRefundId1;
	}

	/**
	 * @param couponRefundId1 the couponRefundId1 to set
	 */
	public void setCouponRefundId1(String couponRefundId1) {
		this.couponRefundId1 = couponRefundId1;
	}
}
