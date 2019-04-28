/**
 * 
 */
package com.cc.integration.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class IntegrationEventQueryForm extends QueryForm {

	/**
	 * 积分类型
	 */
	private String eventType;
	
	/**
	 * 状态
	 */
	private String status;
	
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

}
