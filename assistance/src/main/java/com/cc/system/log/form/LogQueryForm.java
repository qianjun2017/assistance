/**
 * 
 */
package com.cc.system.log.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class LogQueryForm extends QueryForm {

	/**
	 * 模块编码
	 */
	private String module;
	
	/**
	 * 操作类型编码
	 */
	private String operType;

	/**
	 * 操作人
	 */
	private Long userId;
	
	/**
	 * 操作人名称
	 */
	private String userName;
	
	/**
	 * 日志内容
	 */
	private String content;
	
	/**
	 * 操作时间
	 */
	private String operateTime;
	
	/**
	 * 操作简介
	 */
	private String title;

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * @return the operType
	 */
	public String getOperType() {
		return operType;
	}

	/**
	 * @param operType the operType to set
	 */
	public void setOperType(String operType) {
		this.operType = operType;
	}

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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the operateTime
	 */
	public String getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime the operateTime to set
	 */
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
