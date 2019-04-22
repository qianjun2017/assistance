/**
 * 
 */
package com.cc.integration.service;

import java.util.List;
import java.util.Map;

import com.cc.common.web.Page;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.form.IntegrationEventQueryForm;

/**
 * @author Administrator
 *
 */
public interface IntegrationEventService {

	/**
	 * 查询积分事件
	 * @param eventType
	 * @return
	 */
	List<IntegrationEventBean> queryIntegrationEventBeanList(String eventType);
	
	/**
	 * 保存积分事件
	 * @param integrationEventBean
	 */
	void saveIntegrationEvent(IntegrationEventBean integrationEventBean);
	
	/**
	 * 删除积分事件
	 * @param id
	 */
	void deleteIntegrationEvent(Long id);
	
	/**
	 * 分页查询积分事件
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryIntegrationEventPage(IntegrationEventQueryForm form);
}
