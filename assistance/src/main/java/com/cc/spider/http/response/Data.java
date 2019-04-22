/**
 * 
 */
package com.cc.spider.http.response;

/**
 * @author Administrator
 *
 */
public class Data {

	/**
	 * 是否成功
	 */
	private boolean success = Boolean.TRUE;
	
	/**
	 * 成功的返回数据
	 */
	private String data;
	
	/**
	 * 失败的错误信息
	 */
	private String message;

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
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
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
}
