/**
 * 
 */
package com.cc.wx.http.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class WeiXinPayInfo {
	
	/**
	 * 商户注册具有支付权限的公众号成功后即可获得
	 */
	private String appId;

	/**
	 * 时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
	 */
	private String timeStamp;
	
	/**
	 * 随机字符串，长度为32个字符以下
	 */
	private String nonceStr;
	
	/**
	 * 统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
	 */
	@JsonProperty(value="package")
	private String pkg;
	
	/**
	 * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
	 */
	private String signType;
	
	/**
	 * 签名
	 */
	private String paySign;

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the timeStamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp the timeStamp to set
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * @return the nonceStr
	 */
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
	 * @return the pkg
	 */
	public String getPkg() {
		return pkg;
	}

	/**
	 * @param pkg the pkg to set
	 */
	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	/**
	 * @return the signType
	 */
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
	 * @return the paySign
	 */
	public String getPaySign() {
		return paySign;
	}

	/**
	 * @param paySign the paySign to set
	 */
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}
}
