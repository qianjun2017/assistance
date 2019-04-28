/**
 * 
 */
package com.cc.wx.http.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name="xml")
public class UnifiedOrderNotifyRequest {

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
	 * 商户号     调用接口提交的商户号 
	 */
	@XmlElement(name="mch_id")
	@JsonProperty(value="mch_id")
	private String mchId;
	
	/**
	 * 设备号    自定义参数，可以为请求支付的终端设备号等 
	 */
	@XmlElement(name="device_info")
	@JsonProperty(value="device_info")
	private String deviceInfo;
	
	/**
	 * 随机字符串     微信返回的随机字符串 
	 */
	@XmlElement(name="nonce_str")
	@JsonProperty(value="nonce_str")
	private String nonceStr;
	
	/**
	 * 签名   微信返回的签名值 
	 */
	@XmlElement
	private String sign;
	
	/**
	 * 签名类型
	 */
	@XmlElement(name="sign_type")
	@JsonProperty(value="sign_type")
	private String signType;
	
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
	 * 用户标识openid
	 */
	@XmlElement
	private String openid;
	
	/**
	 * 是否关注公众账号
	 */
	@XmlElement(name="is_subscribe")
	@JsonProperty(value="is_subscribe")
	private String isSubscribe;
	
	/**
	 * 交易类型      交易类型，取值为：JSAPI，NATIVE，APP等
	 */
	@XmlElement(name="trade_type")
	@JsonProperty(value="trade_type")
	private String tradeType;
	
	/**
	 * 付款银行
	 */
	@XmlElement(name="bank_type")
	@JsonProperty(value="bank_type")
	private String bankType;
	
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
	 * 现金支付货币类型
	 */
	@XmlElement(name="cash_fee_type")
	@JsonProperty(value="cash_fee_type")
	private String cashFeeType;
	
	/**
	 * 总代金券金额
	 */
	@XmlElement(name="coupon_fee")
	@JsonProperty(value="coupon_fee")
	private Integer couponFee;
	
	/**
	 * 代金券使用数量
	 */
	@XmlElement(name="coupon_count")
	@JsonProperty(value="coupon_count")
	private Integer couponCount;
	
	/**
	 * 代金券类型
	 */
	@XmlElement(name="coupon_type_0")
	@JsonProperty(value="coupon_type_0")
	private String couponType0;
	
	/**
	 * 代金券ID
	 */
	@XmlElement(name="coupon_id_0")
	@JsonProperty(value="coupon_id_0")
	private String couponId0;
	
	/**
	 * 单个代金券支付金额
	 */
	@XmlElement(name="coupon_fee_0")
	@JsonProperty(value="coupon_fee_0")
	private Integer couponFee0;

	/**
	 * 代金券类型
	 */
	@XmlElement(name="coupon_type_1")
	@JsonProperty(value="coupon_type_1")
	private String couponType1;
	
	/**
	 * 代金券ID
	 */
	@XmlElement(name="coupon_id_1")
	@JsonProperty(value="coupon_id_1")
	private String couponId1;
	
	/**
	 * 单个代金券支付金额
	 */
	@XmlElement(name="coupon_fee_1")
	@JsonProperty(value="coupon_fee_1")
	private Integer couponFee1;
	
	/**
	 * 微信支付订单号
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
	 * 商家数据包
	 */
	@XmlElement
	private String attach;
	
	/**
	 * 支付完成时间
	 */
	@XmlElement(name="time_end")
	@JsonProperty(value="time_end")
	private String timeEnd;

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
	 * @return the deviceInfo
	 */
	@XmlTransient
	public String getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo the deviceInfo to set
	 */
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
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
	 * @return the signType
	 */
	@XmlTransient
	public String getSignType() {
		return signType;
	}

	/**
	 * @param signType the signType to set
	 */
	public void setSignType(String signType) {
		this.signType = signType;
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
	 * @return the openid
	 */
	@XmlTransient
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @return the isSubscribe
	 */
	@XmlTransient
	public String getIsSubscribe() {
		return isSubscribe;
	}

	/**
	 * @param isSubscribe the isSubscribe to set
	 */
	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	/**
	 * @return the tradeType
	 */
	@XmlTransient
	public String getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType the tradeType to set
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return the bankType
	 */
	@XmlTransient
	public String getBankType() {
		return bankType;
	}

	/**
	 * @param bankType the bankType to set
	 */
	public void setBankType(String bankType) {
		this.bankType = bankType;
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
	 * @return the couponFee
	 */
	@XmlTransient
	public Integer getCouponFee() {
		return couponFee;
	}

	/**
	 * @param couponFee the couponFee to set
	 */
	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	/**
	 * @return the couponCount
	 */
	@XmlTransient
	public Integer getCouponCount() {
		return couponCount;
	}

	/**
	 * @param couponCount the couponCount to set
	 */
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	/**
	 * @return the couponType0
	 */
	@XmlTransient
	public String getCouponType0() {
		return couponType0;
	}

	/**
	 * @param couponType0 the couponType0 to set
	 */
	public void setCouponType0(String couponType0) {
		this.couponType0 = couponType0;
	}

	/**
	 * @return the couponId0
	 */
	@XmlTransient
	public String getCouponId0() {
		return couponId0;
	}

	/**
	 * @param couponId0 the couponId0 to set
	 */
	public void setCouponId0(String couponId0) {
		this.couponId0 = couponId0;
	}

	/**
	 * @return the couponFee0
	 */
	@XmlTransient
	public Integer getCouponFee0() {
		return couponFee0;
	}

	/**
	 * @param couponFee0 the couponFee0 to set
	 */
	public void setCouponFee0(Integer couponFee0) {
		this.couponFee0 = couponFee0;
	}

	/**
	 * @return the couponType1
	 */
	@XmlTransient
	public String getCouponType1() {
		return couponType1;
	}

	/**
	 * @param couponType1 the couponType1 to set
	 */
	public void setCouponType1(String couponType1) {
		this.couponType1 = couponType1;
	}

	/**
	 * @return the couponId1
	 */
	@XmlTransient
	public String getCouponId1() {
		return couponId1;
	}

	/**
	 * @param couponId1 the couponId1 to set
	 */
	public void setCouponId1(String couponId1) {
		this.couponId1 = couponId1;
	}

	/**
	 * @return the couponFee1
	 */
	@XmlTransient
	public Integer getCouponFee1() {
		return couponFee1;
	}

	/**
	 * @param couponFee1 the couponFee1 to set
	 */
	public void setCouponFee1(Integer couponFee1) {
		this.couponFee1 = couponFee1;
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
	 * @return the attach
	 */
	@XmlTransient
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach the attach to set
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @return the timeEnd
	 */
	@XmlTransient
	public String getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}
	
}
