/**
 * 
 */
package com.cc.system.log.service;

import java.util.Map;

import com.cc.common.web.Page;
import com.cc.system.log.bean.OperationLogBean;
import com.cc.system.log.form.LogQueryForm;

/**
 * @author Administrator
 *
 */
public interface LogService {

	/**
	 * 分页查询操作日志
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryOperationLogPage(LogQueryForm form);

	/**
	 * 查询操作日志详情
	 * @param id
	 * @return
	 */
	OperationLogBean getOperationLogBeanById(Long id);

}
