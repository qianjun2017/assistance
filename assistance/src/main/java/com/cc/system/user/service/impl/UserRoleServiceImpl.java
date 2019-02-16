/**
 * 
 */
package com.cc.system.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.user.bean.UserRoleBean;
import com.cc.system.user.service.UserRoleService;

/**
 * @author Administrator
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateUserRole(Long userId, List<Long> roleIdList) {
		StringBuffer buffer = new StringBuffer();
		List<UserRoleBean> userRoleBeanList = UserRoleBean.findAllByParams(UserRoleBean.class, "userId", userId);
		if (!ListTools.isEmptyOrNull(userRoleBeanList)) {
			List<Long> deleteList = new ArrayList<Long>();
			for (UserRoleBean userRoleBean : userRoleBeanList) {
				Long roleId = userRoleBean.getRoleId();
				if (roleIdList.contains(roleId)) {
					roleIdList.remove(roleId);
				} else {
					int row = userRoleBean.delete();
					if (row != 1) {
						throw new LogicException("E001", "分配角色时,删除角色失败");
					}
					deleteList.add(roleId);
				}
			}
			buffer.append("删除角色").append(ListTools.toString(deleteList));
		}
		if (!ListTools.isEmptyOrNull(roleIdList)) {
			for (Long roleId : roleIdList) {
				UserRoleBean userRoleBean = new UserRoleBean();
				userRoleBean.setRoleId(roleId);
				userRoleBean.setUserId(userId);;
				int row = userRoleBean.insert();
				if (row!=1) {
					throw new LogicException("E001","分配角色时,新增角色失败");
				}
			}
			buffer.append((buffer.length()>0?",":"")+"新增角色").append(ListTools.toString(roleIdList));
		}
		LogContextUtil.setOperContent(buffer.substring(0));
	}

	@Override
	public List<UserRoleBean> queryUserRoleBeanList(Long userId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userId", userId);
		List<UserRoleBean> userRoleBeanList = UserRoleBean.findAllByMap(UserRoleBean.class, paramMap);
		return userRoleBeanList;
	}

}
