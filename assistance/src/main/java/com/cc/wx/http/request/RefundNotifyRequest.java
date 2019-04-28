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
public class RefundNotifyRequest {

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
	 * 加密信息
	 */
	@XmlElement(name="req_info")
	@JsonProperty(value="req_info")
	private String reqInfo;
	
	/**
	 * 随机字符串   
	 */
	@XmlElement(name="nonce_str")
	@JsonProperty(value="nonce_str")
	private String nonceStr;
	
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
	 * @return the reqInfo
	 */
	@XmlTransient
	public String getReqInfo() {
		return reqInfo;
	}

	/**
	 * @param reqInfo the reqInfo to set
	 */
	public void setReqInfo(String reqInfo) {
		this.reqInfo = reqInfo;
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
}
