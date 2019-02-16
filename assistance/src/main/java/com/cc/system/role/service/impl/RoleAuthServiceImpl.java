/**
 * 
 */
package com.cc.system.role.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.role.bean.RoleAuthBean;
import com.cc.system.role.service.RoleAuthService;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class RoleAuthServiceImpl implements RoleAuthService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateRoleAuth(Long roleId, List<Long> authIdList) {
		StringBuffer buffer = new StringBuffer();
		List<RoleAuthBean> roleAuthBeanList = RoleAuthBean.findAllByParams(RoleAuthBean.class, "roleId", roleId);
		if (!ListTools.isEmptyOrNull(roleAuthBeanList)) {
			List<Long> deleteList = new ArrayList<Long>();
			for (RoleAuthBean roleAuthBean : roleAuthBeanList) {
				Long authId = roleAuthBean.getAuthId();
				if (authIdList.contains(authId)) {
					authIdList.remove(authId);
				} else {
					int row = roleAuthBean.delete();
					if (row != 1) {
						throw new LogicException("E001", "分配权限时,删除权限失败");
					}
					deleteList.add(authId);
				}
			}
			buffer.append("删除权限").append(ListTools.toString(deleteList));
		}
		if (!ListTools.isEmptyOrNull(authIdList)) {
			for (Long authId : authIdList) {
				RoleAuthBean roleAuthBean = new RoleAuthBean();
				roleAuthBean.setRoleId(roleId);
				roleAuthBean.setAuthId(authId);
				int row = roleAuthBean.insert();
				if (row!=1) {
					throw new LogicException("E001","分配权限时,新增权限失败");
				}
			}
			buffer.append((buffer.length()>0?",":"")+"新增权限").append(ListTools.toString(authIdList));
		}
		LogContextUtil.setOperContent(buffer.substring(0));
	}

	@Override
	public List<RoleAuthBean> queryRoleAuthBeanList(List<Long> roleList) {
		Example example = new Example(RoleAuthBean.class);
		Example.Criteria criteria = example.createCriteria();
		for (Long roleId : roleList) {
			criteria.orEqualTo("roleId", roleId);
		}
		List<RoleAuthBean> roleAuthBeanList = RoleAuthBean.findByExample(RoleAuthBean.class, example);
		return roleAuthBeanList;
	}

}
