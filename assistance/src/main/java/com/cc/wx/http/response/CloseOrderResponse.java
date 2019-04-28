/**
 * 
 */
package com.cc.wx.http.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name="xml")
public class CloseOrderResponse {

	/**
	 * 
	 */
	public CloseOrderResponse() {
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
	 * 业务结果描述
	 */
	@XmlElement(name="result_msg")
	private String resultMsg;
	
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
	 * @return the resultMsg
	 */
	@XmlTransient
	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
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
}
