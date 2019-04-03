/**
 * 
 */
package com.cc.system.role.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.system.role.bean.RoleBean;
import com.cc.system.role.form.RoleQueryForm;
import com.cc.system.role.service.RoleAuthService;
import com.cc.system.role.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleAuthService roleAuthService;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveRole(RoleBean roleBean) {
		int row = roleBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存角色失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteRole(Long id) {
		roleAuthService.deletRoleAuth(id);
		RoleBean roleBean = new RoleBean();
		roleBean.setId(id);
		int row = roleBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除角色失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryRolePage(RoleQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(RoleBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getRoleName())){
			criteria.andLike("roleName", "%"+form.getRoleName()+"%");
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<RoleBean> roleBeanList = RoleBean.findByExample(RoleBean.class, example);
		PageInfo<RoleBean> pageInfo = new PageInfo<RoleBean>(roleBeanList);
		if (ListTools.isEmptyOrNull(roleBeanList)) {
			page.setMessage("没有查询到相关角色数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> roleList = new ArrayList<Map<String,Object>>();
		for (RoleBean roleBean : roleBeanList) {
			Map<String, Object> role = new HashMap<String, Object>();
			role.put("id", roleBean.getId());
			role.put("roleName", roleBean.getRoleName());
			role.put("createTime", DateTools.getFormatDate(roleBean.getCreateTime(), DateTools.DATEFORMAT));
			roleList.add(role);
		}
		page.setData(roleList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public List<RoleBean> queryRoleBeanList(List<Long> roleList) {
		Example example = new Example(RoleBean.class);
		Example.Criteria criteria = example.createCriteria();
		for (Long roleId : roleList) {
			criteria.orEqualTo("id", roleId);
		}
		List<RoleBean> roleBeanList = RoleBean.findByExample(RoleBean.class, example);
		return roleBeanList;
	}

}
