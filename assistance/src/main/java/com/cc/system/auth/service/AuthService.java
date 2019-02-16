/**
 * 
 */
package com.cc.system.auth.service;

import java.util.List;

import com.cc.system.auth.bean.AuthBean;

/**
 * @author Administrator
 *
 */
public interface AuthService {

	/**
	 * 保存权限
	 * @param authBean
	 */
	void saveAuth(AuthBean authBean);
	
	/**
	 * 删除权限
	 * @param id
	 */
	void deleteAuth(Long id);

	/**
	 * 查询权限列表
	 * @param authList
	 * @return
	 */
	List<AuthBean> queryAuthBeanList(List<Long> authList);
}
