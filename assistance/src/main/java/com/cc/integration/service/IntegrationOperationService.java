/**
 * 
 */
package com.cc.integration.service;

import java.util.Map;

import com.cc.common.web.Page;
import com.cc.integration.bean.IntegrationOperationBean;
import com.cc.integration.form.IntegrationOperationQueryForm;

/**
 * @author Administrator
 *
 */
public interface IntegrationOperationService {

	/**
	 * 保存积分记录
	 * @param integrationOperationBean
	 */
	void saveIntegrationOperation(IntegrationOperationBean integrationOperationBean);
	
	/**
	 * 分页查询积分记录
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryIntegrationOperationPage(IntegrationOperationQueryForm form);
}
