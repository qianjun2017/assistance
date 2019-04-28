/**
 * 
 */
package com.cc.wx.http.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name="xml")
public class RefundRequest {

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
	 * 订单金额
	 */
	@XmlElement(name="total_fee")
	@JsonProperty(value="total_fee")
	private Integer totalFee;
	
	/**
	 * 退款金额
	 */
	@XmlElement(name="refund_fee")
	@JsonProperty(value="refund_fee")
	private Integer refundFee;
	
	/**
	 * 货币种类
	 */
	@XmlElement(name="refund_fee_type")
	@JsonProperty(value="refund_fee_type")
	private String refundFeeType;
	
	/**
	 * 退款原因
	 */
	@XmlElement(name="refund_desc")
	@JsonProperty(value="refund_desc")
	private String refundDesc;
	
	/**
	 * 退款资金来源
	 */
	@XmlElement(name="refund_account")
	@JsonProperty(value="refund_account")
	private String refundAccount;
	
	/**
	 * 退款结果通知url
	 */
	@XmlElement(name="notify_url")
	@JsonProperty(value="notify_url")
	private String notifyUrl;
	
	/**
	 * 微信申请退款URL
	 */
	@XmlTransient
	@JsonIgnore
	private String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	
	/**
	 * 商户平台设置的密钥key
	 */
	@XmlTransient
	@JsonIgnore
	private String key;

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
	 * @return the refundFeeType
	 */
	@XmlTransient
	public String getRefundFeeType() {
		return refundFeeType;
	}

	/**
	 * @param refundFeeType the refundFeeType to set
	 */
	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}

	/**
	 * @return the refundDesc
	 */
	@XmlTransient
	public String getRefundDesc() {
		return refundDesc;
	}

	/**
	 * @param refundDesc the refundDesc to set
	 */
	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
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
	 * @return the notifyUrl
	 */
	@XmlTransient
	public String getNotifyUrl() {
		return notifyUrl;
	}

	/**
	 * @param notifyUrl the notifyUrl to set
	 */
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	/**
	 * @return the url
	 */
	@XmlTransient
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the key
	 */
	@XmlTransient
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
