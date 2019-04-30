package com.cc.sms.tenxun.http.request;

import com.cc.sms.tenxun.http.request.model.Phone;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TenXunSmsSendRequest {

	/**
	 * 用户的 session 内容，腾讯 server 回包中会原样返回，可选字段，不需要就是设置为空
	 */
	private String ext;
	
	/**
	 * 短信码号扩展号，格式为纯数字串，其他格式无效。默认没有开通，开通请联系 腾讯云短信技术支持
	 */
	private String extend;
	
	/**
	 * 模板参数，具体使用方法可参考注【1】。若模板没有参数，请设置为空数组
	 */
	private String[] params;
	
	/**
	 * App 凭证，具体计算方式见下注
	 */
	private String sig;
	
	/**
	 * 短信签名，此处应填写审核通过的签名内容，非签名 ID，如果使用默认签名，该字段可缺省
	 */
	private String sign;
	
	/**
	 * 国际电话号码，格式依据 e.164 标准为: +[国家码][手机号] ，示例如：+8613711112222， 其中前面有一个 + 符号 ，86 为国家码，13711112222 为手机号
	 */
	@JsonProperty(value="tel")
	private Phone phone;
	
	/**
	 * 请求发起时间，UNIX 时间戳（单位：秒），如果和系统时间相差超过 10 分钟则会返回失败
	 */
	private Long time;
	
	/**
	 * 模板 ID，在控制台审核通过的模板 ID
	 */
	@JsonProperty(value="tpl_id")
	private Long templateId;
	
	/**
	 * 应用
	 */
	@JsonIgnore
	private String sdkAppid;
	
	/**
	 * 随机数
	 */
	@JsonIgnore
	private String random;
	
	@JsonIgnore
	private String url = "https://yun.tim.qq.com/v5/tlssmssvr/sendsms";

	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}

	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}

	/**
	 * @return the extend
	 */
	public String getExtend() {
		return extend;
	}

	/**
	 * @param extend the extend to set
	 */
	public void setExtend(String extend) {
		this.extend = extend;
	}

	/**
	 * @return the params
	 */
	public String[] getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(String[] params) {
		this.params = params;
	}

	/**
	 * @return the sig
	 */
	public String getSig() {
		return sig;
	}

	/**
	 * @param sig the sig to set
	 */
	public void setSig(String sig) {
		this.sig = sig;
	}

	/**
	 * @return the sign
	 */
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
	 * @return the phone
	 */
	public Phone getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	/**
	 * @return the time
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Long time) {
		this.time = time;
	}

	/**
	 * @return the templateId
	 */
	public Long getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the sdkAppid
	 */
	public String getSdkAppid() {
		return sdkAppid;
	}

	/**
	 * @param sdkAppid the sdkAppid to set
	 */
	public void setSdkAppid(String sdkAppid) {
		this.sdkAppid = sdkAppid;
	}

	/**
	 * @return the random
	 */
	public String getRandom() {
		return random;
	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(String random) {
		this.random = random;
	}
}
