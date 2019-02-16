/**
 * 
 */
package com.cc.system.user.service;

import java.util.Map;

import com.cc.common.web.Page;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.form.UserQueryForm;

/**
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 保存用户
	 * @param userBean
	 */
	void saveUser(UserBean userBean);
	
	/**
	 * 删除用户
	 * @param id
	 */
	void deleteUser(Long id);
	
	/**
	 * 分页查询用户信息
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryUserPage(UserQueryForm form);
	
	/**
	 * 更新用户部分信息
	 * @param userBean
	 */
	void updateUser(UserBean userBean);
}
