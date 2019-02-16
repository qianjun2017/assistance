/**
 * 
 */
package com.cc.system.role.service;

import java.util.List;
import java.util.Map;

import com.cc.common.web.Page;
import com.cc.system.role.bean.RoleBean;
import com.cc.system.role.form.RoleQueryForm;

/**
 * @author Administrator
 *
 */
public interface RoleService {

	/**
	 * 保存角色
	 * @param roleBean
	 */
	void saveRole(RoleBean roleBean);
	
	/**
	 * 删除角色
	 * @param id
	 */
	void deleteRole(Long id);
	
	/**
	 * 分页查询角色信息
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryRolePage(RoleQueryForm form);
	
	/**
	 * 查询角色列表
	 * @param roleList
	 * @return
	 */
	List<RoleBean> queryRoleBeanList(List<Long> roleList);
}
