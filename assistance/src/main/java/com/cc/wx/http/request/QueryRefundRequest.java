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
public class QueryRefundRequest {

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
	 * 微信退款单号
	 */
	@XmlElement(name="refund_id")
	@JsonProperty(value="refund_id")
	private String refundId;
	
	/**
	 * 偏移量
	 */
	private Integer offset;
	
	/**
	 * 商户平台设置的密钥key
	 */
	@XmlTransient
	@JsonIgnore
	private String key;
	
	/**
	 * 微信查询退款URL
	 */
	@XmlTransient
	@JsonIgnore
	private String url = "https://api.mch.weixin.qq.com/pay/refundquery";

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
	 * @return the offset
	 */
	@XmlTransient
	public Integer getOffset() {
		return offset;
	}

	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
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
}
