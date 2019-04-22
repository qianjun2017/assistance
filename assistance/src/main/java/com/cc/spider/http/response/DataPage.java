/**
 * 
 */
package com.cc.spider.http.response;

import java.util.List;

/**
 * 数据页
 * @author Administrator
 *
 */
public class DataPage {
	
	/**
	 * 是否成功
	 */
	private boolean success = Boolean.TRUE;

	/**
	 * 失败的错误信息
	 */
	private String message;
	
	/**
	 * 数据列表
	 */
	private List<String> data;
	
	/**
	 * 剩余数量
	 */
	private long remain;
	
	/**
	 * 是否还有更多
	 */
	private boolean more;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**

	/**
	 * @return the remain
	 */
	public long getRemain() {
		return remain;
	}

	/**
	 * @return the more
	 */
	public boolean isMore() {
		return more;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the data
	 */
	public List<String> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<String> data) {
		this.data = data;
	}

	/**
	 * @param remain the remain to set
	 */
	public void setRemain(long remain) {
		this.remain = remain;
	}

	/**
	 * @param more the more to set
	 */
	public void setMore(boolean more) {
		this.more = more;
	}

}
