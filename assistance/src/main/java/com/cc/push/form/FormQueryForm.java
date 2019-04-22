/**
 * 
 */
package com.cc.push.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author ws_yu
 *
 */
public class FormQueryForm {

	/**
	 * 用户
	 */
	private Long userId;
	
	/**
	 * 有效开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

}
