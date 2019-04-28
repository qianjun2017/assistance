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
 * @author ws_yu
 *
 */
@XmlRootElement(name="xml")
public class UnifiedOrderRequest {
	
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
	 * 商品描述
	 */
	@XmlElement
	private String body;
	
	/**
	 * 商品详情
	 */
	@XmlElement
	private String detail;
	
	/**
	 * 附加数据
	 */
	@XmlElement
	private String attach;
	
	/**
	 * 商户订单号
	 */
	@XmlElement(name="out_trade_no")
	@JsonProperty(value="out_trade_no")
	private String outTradeNo;
	
	/**
	 * 标价币种
	 */
	@XmlElement(name="fee_type")
	@JsonProperty(value="fee_type")
	private String feeType;
	
	/**
	 * 标价金额
	 */
	@XmlElement(name="total_fee")
	@JsonProperty(value="total_fee")
	private Integer totalFee;
	
	/**
	 * 终端IP
	 */
	@XmlElement(name="spbill_create_ip")
	@JsonProperty(value="spbill_create_ip")
	private String spbillCreateIp;
	
	/**
	 * 交易起始时间
	 */
	@XmlElement(name="time_start")
	@JsonProperty(value="time_start")
	private String timeStart;

	/**
	 * 交易结束时间
	 */
	@XmlElement(name="time_expire")
	@JsonProperty(value="time_expire")
	private String timeExpire;
	
	/**
	 * 订单优惠标记
	 */
	@XmlElement(name="goods_tag")
	@JsonProperty(value="goods_tag")
	private String goodsTag;
	
	/**
	 * 通知地址
	 */
	@XmlElement(name="notify_url")
	@JsonProperty(value="notify_url")
	private String notifyUrl;
	
	/**
	 * 交易类型
	 */
	@XmlElement(name="trade_type")
	@JsonProperty(value="trade_type")
	private String tradeType;
	
	/**
	 * 商品ID
	 */
	@XmlElement(name="product_id")
	@JsonProperty(value="product_id")
	private String productId;
	
	/**
	 * 指定支付方式
	 */
	@XmlElement(name="limit_pay")
	@JsonProperty(value="limit_pay")
	private String limitPay;
	
	/**
	 * 用户标识
	 */
	@XmlElement
	private String openid;
	
	/**
	 * 电子发票入口开放标识
	 */
	@XmlElement
	private String receipt;
	
	/**
	 * 场景信息
	 */
	@XmlElement(name="scene_info")
	@JsonProperty(value="scene_info")
	private String sceneInfo;
	
	/**
	 * 微信统一下单URL
	 */
	@XmlTransient
	@JsonIgnore
	private String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	
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
	 * @return the body
	 */
	@XmlTransient
	public String getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return the detail
	 */
	@XmlTransient
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
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
	 * @return the spbillCreateIp
	 */
	@XmlTransient
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	/**
	 * @param spbillCreateIp the spbillCreateIp to set
	 */
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	/**
	 * @return the timeStart
	 */
	@XmlTransient
	public String getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeExpire
	 */
	@XmlTransient
	public String getTimeExpire() {
		return timeExpire;
	}

	/**
	 * @param timeExpire the timeExpire to set
	 */
	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	/**
	 * @return the goodsTag
	 */
	@XmlTransient
	public String getGoodsTag() {
		return goodsTag;
	}

	/**
	 * @param goodsTag the goodsTag to set
	 */
	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
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
	 * @return the productId
	 */
	@XmlTransient
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the limitPay
	 */
	@XmlTransient
	public String getLimitPay() {
		return limitPay;
	}

	/**
	 * @param limitPay the limitPay to set
	 */
	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
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
	 * @return the receipt
	 */
	@XmlTransient
	public String getReceipt() {
		return receipt;
	}

	/**
	 * @param receipt the receipt to set
	 */
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	/**
	 * @return the sceneInfo
	 */
	@XmlTransient
	public String getSceneInfo() {
		return sceneInfo;
	}

	/**
	 * @param sceneInfo the sceneInfo to set
	 */
	public void setSceneInfo(String sceneInfo) {
		this.sceneInfo = sceneInfo;
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
