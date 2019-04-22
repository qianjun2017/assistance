/**
 * 
 */
package com.cc.integration.event;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.spring.SpringContextUtil;
import com.cc.common.tools.DateTools;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.bean.IntegrationOperationBean;
import com.cc.integration.service.IntegrationOperationService;
import com.cc.integration.service.IntegrationService;

/**
 * @author Administrator
 *
 */
public abstract class Event {

	private IntegrationOperationService integrationOperationService;
	
	private IntegrationService integrationService;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 时间对应消费积分
	 */
	private Long integration;
	
	/**
	 * 事件对应等级积分
	 */
	private Long gradeIntegration;
	
	/**
	 * 积分事件
	 */
	private IntegrationEventBean integrationEventBean;
	
	/**
	 * 相关数据
	 */
	private Object data;
	
	public Event() {
		this.integrationService = (IntegrationService) SpringContextUtil.getBean(IntegrationService.class);
		this.integrationOperationService = (IntegrationOperationService) SpringContextUtil.getBean(IntegrationOperationService.class);
	}
	
	/**
	 * 事件发生
	 */
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void happened() {
		calcIntegration();
		if (integration == 0l && gradeIntegration == 0l) {
			return;
		}
		integrationService.deposit(leaguerId, integration, gradeIntegration);
		if (integration!=0l) {
			IntegrationOperationBean integrationOperationBean = new IntegrationOperationBean();
			integrationOperationBean.setEventType(integrationEventBean.getEventType());
			integrationOperationBean.setIntegration(integration);
			integrationOperationBean.setComment(getHappenedComment());
			integrationOperationBean.setLeaguerId(leaguerId);
			integrationOperationBean.setCreateTime(DateTools.now());
			integrationOperationService.saveIntegrationOperation(integrationOperationBean);
		}
	}
	
	/**
	 * 事件回滚
	 */
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void rollBack() {
		calcIntegration();
		if (integration == 0l && gradeIntegration == 0l) {
			return;
		}
		integrationService.deposit(leaguerId, -integration, -gradeIntegration);
		if (integration!=0l) {
			IntegrationOperationBean integrationOperationBean = new IntegrationOperationBean();
			integrationOperationBean.setEventType(integrationEventBean.getEventType());
			integrationOperationBean.setIntegration(-integration);
			integrationOperationBean.setComment(getRollBackComment());
			integrationOperationBean.setLeaguerId(leaguerId);
			integrationOperationBean.setCreateTime(DateTools.now());
			integrationOperationService.saveIntegrationOperation(integrationOperationBean);
		}
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @param gradeIntegration the gradeIntegration to set
	 */
	public void setGradeIntegration(Long gradeIntegration) {
		this.gradeIntegration = gradeIntegration;
	}

	/**
	 * @param integrationEventBean the integrationEventBean to set
	 */
	public void setIntegrationEventBean(IntegrationEventBean integrationEventBean) {
		this.integrationEventBean = integrationEventBean;
	}

	/**
	 * @return the integrationEventBean
	 */
	public IntegrationEventBean getIntegrationEventBean() {
		return integrationEventBean;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	public abstract void calcIntegration();

	public abstract String getHappenedComment();
	
	public abstract String getRollBackComment();
}
