/**
 * 
 */
package com.cc.wx.http.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author ws_yu
 *
 */
@XmlRootElement(name="xml")
public class UnifiedOrderResponse {
	
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
	 * 设备号    自定义参数，可以为请求支付的终端设备号等 
	 */
	@XmlElement(name="device_info")
	private String deviceInfo;
	
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
	 * 交易类型      交易类型，取值为：JSAPI，NATIVE，APP等
	 */
	@XmlElement(name="trade_type")
	private String tradeType;
	
	/**
	 * 预支付交易会话标识    微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
	 */
	@XmlElement(name="prepay_id")
	private String prepayId;
	
	/**
	 * 二维码链接    trade_type=NATIVE时有返回，此url用于生成支付二维码，然后提供给用户进行扫码支付     code_url的值并非固定，使用时按照URL格式转成二维码即可
	 */
	@XmlElement(name="code_url")
	private String codeUrl;

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
	 * @return the prepayId
	 */
	@XmlTransient
	public String getPrepayId() {
		return prepayId;
	}

	/**
	 * @param prepayId the prepayId to set
	 */
	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	/**
	 * @return the codeUrl
	 */
	@XmlTransient
	public String getCodeUrl() {
		return codeUrl;
	}

	/**
	 * @param codeUrl the codeUrl to set
	 */
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	
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

	public UnifiedOrderResponse(){
		this.success = Boolean.FALSE;
	}
}
