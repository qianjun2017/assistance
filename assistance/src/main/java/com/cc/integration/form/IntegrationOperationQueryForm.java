/**
 * 
 */
package com.cc.integration.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class IntegrationOperationQueryForm extends QueryForm{

	/**
	 * 积分类型
	 */
	private String eventType;
	
	/**
	 * 备注
	 */
	private String comment;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;
	
	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
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

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

}
