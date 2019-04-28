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
public class QueryRefundResponse {
	
	/**
	 * 
	 */
	public QueryRefundResponse() {
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
	@JsonProperty(value="return_code")
	private String returnCode;
	
	/**
	 * 返回信息
	 */
	@XmlElement(name="return_msg")
	@JsonProperty(value="return_msg")
	private String returnMsg;
	
	/**
	 * 小程序ID  调用接口提交的小程序ID
	 */
	@XmlElement
	private String appid;
	
	/**
	 * 退款的商户号
	 */
	@XmlElement(name="mch_id")
	@JsonProperty(value="mch_id")
	private String mchId;
	
	/**
	 * 随机字符串   
	 */
	@XmlElement(name="nonce_str")
	@JsonProperty(value="nonce_str")
	private String nonceStr;
	
	/**
	 * 业务结果   SUCCESS/FAIL 
	 */
	@XmlElement(name="result_code")
	@JsonProperty(value="result_code")
	private String resultCode;
	
	/**
	 * 错误代码
	 */
	@XmlElement(name="err_code")
	@JsonProperty(value="err_code")
	private String errCode;
	
	/**
	 * 错误代码描述
	 */
	@XmlElement(name="err_code_des")
	@JsonProperty(value="err_code_des")
	private String errCodeDes;
	
	/**
	 * 签名   微信返回的签名值 
	 */
	@XmlElement
	private String sign;
	
	/**
	 * 订单总退款次数
	 */
	@XmlElement(name="total_refund_count")
	@JsonProperty(value="total_refund_count")
	private String totalRefundCount;
	
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
	 * 货币种类
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
	 * 退款笔数
	 */
	@XmlElement(name="refund_count")
	@JsonProperty(value="refund_count")
	private Integer refundCount;
	
	/**
	 * 商户退款单号
	 */
	@XmlElement(name="out_refund_no_0")
	@JsonProperty(value="out_refund_no_0")
	private String outRefundNo0;
	
	/**
	 * 微信退款单号
	 */
	@XmlElement(name="refund_id_0")
	@JsonProperty(value="refund_id_0")
	private String refundId0;
	
	/**
	 * 退款渠道
	 */
	@XmlElement(name="refund_channel_0")
	@JsonProperty(value="refund_channel_0")
	private String refundChannel0;
	
	/**
	 * 申请退款金额
	 */
	@XmlElement(name="refund_fee_0")
	@JsonProperty(value="refund_fee_0")
	private Integer refundFee0;
	
	/**
	 * 退款金额
	 */
	@XmlElement(name="settlement_refund_fee_0")
	@JsonProperty(value="settlement_refund_fee_0")
	private Integer settlementRefundFee0;
	
	/**
	 * 总代金券退款金额
	 */
	@XmlElement(name="coupon_refund_fee_0")
	@JsonProperty(value="coupon_refund_fee_0")
	private Integer couponRefundFee0;
	
	/**
	 * 退款状态
	 */
	@XmlElement(name="refund_status_0")
	@JsonProperty(value="refund_status_0")
	private String refundStatus0;
	
	/**
	 * 退款资金来源
	 */
	@XmlElement(name="refund_account_0")
	@JsonProperty(value="refund_account_0")
	private String refundAccount0;
	
	/**
	 * 退款入账账户
	 */
	@XmlElement(name="refund_recv_accout_0")
	@JsonProperty(value="refund_recv_accout_0")
	private String refundRecvAccout0;
	
	/**
	 * 退款成功时间
	 */
	@XmlElement(name="refund_success_time_0")
	@JsonProperty(value="refund_success_time_0")
	private String refundSuccessTime0;
	
	/**
	 * 代金券类型
	 */
	@XmlElement(name="coupon_type_0_0")
	@JsonProperty(value="coupon_type_0_0")
	private String couponType0_0;
	
	/**
	 * 退款代金券ID
	 */
	@XmlElement(name="coupon_refund_id_0_0")
	@JsonProperty(value="coupon_refund_id_0_0")
	private String couponRefundId0_0;
	
	/**
	 * 单个代金券退款金额
	 */
	@XmlElement(name="coupon_refund_fee_0_0")
	@JsonProperty(value="coupon_refund_fee_0_0")
	private Integer couponRefundFee0_0;
	
	/**
	 * 代金券类型
	 */
	@XmlElement(name="coupon_type_0_1")
	@JsonProperty(value="coupon_type_0_1")
	private String couponType0_1;
	
	/**
	 * 退款代金券ID
	 */
	@XmlElement(name="coupon_refund_id_0_1")
	@JsonProperty(value="coupon_refund_id_0_1")
	private String couponRefundId0_1;
	
	/**
	 * 单个代金券退款金额
	 */
	@XmlElement(name="coupon_refund_fee_0_1")
	@JsonProperty(value="coupon_refund_fee_0_1")
	private Integer couponRefundFee0_1;

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
	 * @return the totalRefundCount
	 */
	@XmlTransient
	public String getTotalRefundCount() {
		return totalRefundCount;
	}

	/**
	 * @param totalRefundCount the totalRefundCount to set
	 */
	public void setTotalRefundCount(String totalRefundCount) {
		this.totalRefundCount = totalRefundCount;
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
	 * @return the refundCount
	 */
	@XmlTransient
	public Integer getRefundCount() {
		return refundCount;
	}

	/**
	 * @param refundCount the refundCount to set
	 */
	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	/**
	 * @return the outRefundNo0
	 */
	@XmlTransient
	public String getOutRefundNo0() {
		return outRefundNo0;
	}

	/**
	 * @param outRefundNo0 the outRefundNo0 to set
	 */
	public void setOutRefundNo0(String outRefundNo0) {
		this.outRefundNo0 = outRefundNo0;
	}

	/**
	 * @return the refundId0
	 */
	@XmlTransient
	public String getRefundId0() {
		return refundId0;
	}

	/**
	 * @param refundId0 the refundId0 to set
	 */
	public void setRefundId0(String refundId0) {
		this.refundId0 = refundId0;
	}

	/**
	 * @return the refundChannel0
	 */
	@XmlTransient
	public String getRefundChannel0() {
		return refundChannel0;
	}

	/**
	 * @param refundChannel0 the refundChannel0 to set
	 */
	public void setRefundChannel0(String refundChannel0) {
		this.refundChannel0 = refundChannel0;
	}

	/**
	 * @return the refundFee0
	 */
	@XmlTransient
	public Integer getRefundFee0() {
		return refundFee0;
	}

	/**
	 * @param refundFee0 the refundFee0 to set
	 */
	public void setRefundFee0(Integer refundFee0) {
		this.refundFee0 = refundFee0;
	}

	/**
	 * @return the settlementRefundFee0
	 */
	@XmlTransient
	public Integer getSettlementRefundFee0() {
		return settlementRefundFee0;
	}

	/**
	 * @param settlementRefundFee0 the settlementRefundFee0 to set
	 */
	public void setSettlementRefundFee0(Integer settlementRefundFee0) {
		this.settlementRefundFee0 = settlementRefundFee0;
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
	 * @return the refundStatus0
	 */
	@XmlTransient
	public String getRefundStatus0() {
		return refundStatus0;
	}

	/**
	 * @param refundStatus0 the refundStatus0 to set
	 */
	public void setRefundStatus0(String refundStatus0) {
		this.refundStatus0 = refundStatus0;
	}

	/**
	 * @return the refundAccount0
	 */
	@XmlTransient
	public String getRefundAccount0() {
		return refundAccount0;
	}

	/**
	 * @param refundAccount0 the refundAccount0 to set
	 */
	public void setRefundAccount0(String refundAccount0) {
		this.refundAccount0 = refundAccount0;
	}

	/**
	 * @return the refundRecvAccout0
	 */
	@XmlTransient
	public String getRefundRecvAccout0() {
		return refundRecvAccout0;
	}

	/**
	 * @param refundRecvAccout0 the refundRecvAccout0 to set
	 */
	public void setRefundRecvAccout0(String refundRecvAccout0) {
		this.refundRecvAccout0 = refundRecvAccout0;
	}

	/**
	 * @return the refundSuccessTime0
	 */
	@XmlTransient
	public String getRefundSuccessTime0() {
		return refundSuccessTime0;
	}

	/**
	 * @param refundSuccessTime0 the refundSuccessTime0 to set
	 */
	public void setRefundSuccessTime0(String refundSuccessTime0) {
		this.refundSuccessTime0 = refundSuccessTime0;
	}

	/**
	 * @return the couponType0_0
	 */
	@XmlTransient
	public String getCouponType0_0() {
		return couponType0_0;
	}

	/**
	 * @param couponType0_0 the couponType0_0 to set
	 */
	public void setCouponType0_0(String couponType0_0) {
		this.couponType0_0 = couponType0_0;
	}

	/**
	 * @return the couponRefundId0_0
	 */
	@XmlTransient
	public String getCouponRefundId0_0() {
		return couponRefundId0_0;
	}

	/**
	 * @param couponRefundId0_0 the couponRefundId0_0 to set
	 */
	public void setCouponRefundId0_0(String couponRefundId0_0) {
		this.couponRefundId0_0 = couponRefundId0_0;
	}

	/**
	 * @return the couponRefundFee0_0
	 */
	@XmlTransient
	public Integer getCouponRefundFee0_0() {
		return couponRefundFee0_0;
	}

	/**
	 * @param couponRefundFee0_0 the couponRefundFee0_0 to set
	 */
	public void setCouponRefundFee0_0(Integer couponRefundFee0_0) {
		this.couponRefundFee0_0 = couponRefundFee0_0;
	}

	/**
	 * @return the couponType0_1
	 */
	@XmlTransient
	public String getCouponType0_1() {
		return couponType0_1;
	}

	/**
	 * @param couponType0_1 the couponType0_1 to set
	 */
	public void setCouponType0_1(String couponType0_1) {
		this.couponType0_1 = couponType0_1;
	}

	/**
	 * @return the couponRefundId0_1
	 */
	@XmlTransient
	public String getCouponRefundId0_1() {
		return couponRefundId0_1;
	}

	/**
	 * @param couponRefundId0_1 the couponRefundId0_1 to set
	 */
	public void setCouponRefundId0_1(String couponRefundId0_1) {
		this.couponRefundId0_1 = couponRefundId0_1;
	}

	/**
	 * @return the couponRefundFee0_1
	 */
	@XmlTransient
	public Integer getCouponRefundFee0_1() {
		return couponRefundFee0_1;
	}

	/**
	 * @param couponRefundFee0_1 the couponRefundFee0_1 to set
	 */
	public void setCouponRefundFee0_1(Integer couponRefundFee0_1) {
		this.couponRefundFee0_1 = couponRefundFee0_1;
	}
}
