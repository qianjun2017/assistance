package com.cc.push.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PushListResult {

	private Long id;
	
	/**
	 * 模板名称
	 */
	private String title;
	
	/**
	 * 推送时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 推送状态
	 */
	private String status;
	
	/**
	 * 推送条数
	 */
	private Long num;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * @return the num
	 */
	public Long getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(Long num) {
		this.num = num;
	}
}
