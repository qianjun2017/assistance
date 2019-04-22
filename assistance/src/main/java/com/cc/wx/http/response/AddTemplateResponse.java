/**
 * 
 */
package com.cc.wx.http.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class AddTemplateResponse {

	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 错误码
	 */
	private Integer errcode;
	
	/**
	 * 错误信息
	 */
	private String errmsg;
	
	/**
	 * 添加至帐号下的模板id，发送小程序模板消息时所需
	 */
	@JsonProperty(value="template_id")
	private String templateId;

	public AddTemplateResponse() {
		this.success = Boolean.FALSE;
	}

	/**
	 * @return the success
	 */
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
	 * @return the errcode
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
}
