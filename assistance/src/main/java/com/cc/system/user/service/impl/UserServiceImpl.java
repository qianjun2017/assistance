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
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.form.UserQueryForm;
import com.cc.system.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveUser(UserBean userBean) {
		int row = userBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存用户失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteUser(Long id) {
		UserBean userBean = new UserBean();
		userBean.setId(id);
		int row = userBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除用户失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryUserPage(UserQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(UserBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getUserName())){
			criteria.andLike("userName", "%"+form.getUserName()+"%");
		}
		if(!StringTools.isNullOrNone(form.getNickName())){
			criteria.andLike("nickName", "%"+form.getNickName()+"%");
		}
		if(!StringTools.isNullOrNone(form.getRealName())){
			criteria.andLike("realName", "%"+form.getRealName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getPhone())) {
			criteria.andEqualTo("phone", form.getPhone());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<UserBean> userBeanList = UserBean.findByExample(UserBean.class, example);
		PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(userBeanList);
		if (ListTools.isEmptyOrNull(userBeanList)) {
			page.setMessage("没有查询到相关用户数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
		for (UserBean userBean : userBeanList) {
			Map<String, Object> user = new HashMap<String, Object>();
			user.put("id", userBean.getId());
			user.put("userName", userBean.getUserName());
			user.put("nickName", userBean.getNickName());
			user.put("realName", userBean.getRealName());
			user.put("phone", userBean.getPhone());
			user.put("orgId", userBean.getOrgId());
			user.put("openId", userBean.getOpenId());
			user.put("createTime", DateTools.getFormatDate(userBean.getCreateTime(), DateTools.DATEFORMAT));
			userList.add(user);
		}
		page.setData(userList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateUser(UserBean userBean) {
		int row = userBean.update();
		if(row!=1){
			throw new LogicException("E001", "更新用户失败");
		}
	}

}
