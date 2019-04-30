/**
 * 
 */
package com.cc.system.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.system.auth.bean.AuthBean;
import com.cc.system.auth.service.AuthService;
import com.cc.system.role.service.RoleAuthService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private RoleAuthService roleAuthService;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveAuth(AuthBean authBean) {
		int row = authBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存权限失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteAuth(Long id) {
		List<AuthBean> authBeanList = AuthBean.findAllByParams(AuthBean.class, "parentId", id);
		if(!ListTools.isEmptyOrNull(authBeanList)){
			throw new LogicException("E001", "请先删除子权限");
		}
		roleAuthService.deletRoleAuthByAuthId(id);
		AuthBean authBean = new AuthBean();
		authBean.setId(id);
		int row = authBean.delete();
		if(row!=1){
			throw new LogicException("E002", "删除权限失败");
		}
	}

	@Override
	public List<AuthBean> queryAuthBeanList(List<Long> authList) {
		Example example = new Example(AuthBean.class);
		Example.Criteria criteria = example.createCriteria();
		for (Long authId : authList) {
			criteria.orEqualTo("id", authId);
		}
		List<AuthBean> authBeanList = AuthBean.findByExample(AuthBean.class, example);
		return authBeanList;
	}

}
