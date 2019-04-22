package com.cc.map.http.response;

import com.cc.map.http.response.model.IpLocationContent;

public class IpLocationResponse {

	/**
	 * 返回状态
	 */
	private boolean success;
	
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 结果状态返回码
	 */
	private String status;
	
	/**
	 * 详细地址信息
	 */
	private String address;
	
	/**
	 * IP地址内容
	 */
	private IpLocationContent content;
	
	/**
	 * 
	 */
	public IpLocationResponse() {
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the content
	 */
	public IpLocationContent getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(IpLocationContent content) {
		this.content = content;
	}
}
