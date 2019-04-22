/**
 * 
 */
package com.cc.integration.service;

/**
 * @author Administrator
 *
 */
public interface IntegrationService {

	/**
	 * 创建积分账户
	 * @param leaguerId
	 */
	void createIntegration(Long leaguerId);
	
	/**
	 * 保存积分
	 * @param leaguerId
	 * @param integration
	 * @param gradeIntegration
	 */
	void deposit(Long leaguerId, Long integration, Long gradeIntegration);
	
	/**
	 * 消费积分
	 * @param leaguerId
	 * @param integration
	 */
	void withdraw(Long leaguerId, Long integration);
	
	/**
	 * 创建积分账户
	 */
	void createIntegrations();
}
