/**
 * 
 */
package com.cc.interfaces.service;

import java.util.Map;

import com.cc.common.web.Page;
import com.cc.interfaces.bean.InterfaceBean;
import com.cc.interfaces.enums.InterfaceEnum;
import com.cc.interfaces.form.InterfaceQueryForm;

/**
 * @author ws_yu
 *
 */
public interface InterfaceService {

	/**
	 * 保存接口信息
	 * @param interfaceBean
	 */
	void saveInterface(InterfaceBean interfaceBean);
	
	/**
	 * 保存接口信息
	 * @param interfaceEnum
	 * @param requestBody
	 * @param responseBody
	 */
	void saveInterface(InterfaceEnum interfaceEnum, String requestBody, String responseBody);
	
	/**
	 * 分页查询接口
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryInterfacePage(InterfaceQueryForm form);
}
