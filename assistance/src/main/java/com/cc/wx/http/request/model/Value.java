/**
 * 
 */
package com.cc.wx.http.request.model;

/**
 * @author ws_yu
 *
 */
public class Value {

	/**
	 * 模板消息内容
	 */
	private String value;

	/**
	 * @param value
	 */
	public Value(String value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
