/**
 * 
 */
package com.cc.integration.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_integration_event")
public class IntegrationEventBean extends BaseOrm<IntegrationEventBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1863883118311138187L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 积分事件类型
	 */
	private String eventType;
	
	/**
	 * 积分值类型
	 */
	private String integrationType;
	
	/**
	 * 积分值参数
	 */
	private Float integration;
	
	/**
	 * 事件状态
	 */
	private String status;
	
	/**
	 * @return the integrationType
	 */
	public String getIntegrationType() {
		return integrationType;
	}
	/**
	 * @param integrationType the integrationType to set
	 */
	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}
	/**
	 * @return the integration
	 */
	public Float getIntegration() {
		return integration;
	}
	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Float integration) {
		this.integration = integration;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
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
