/**
 * 
 */
package com.cc.system.role.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.role.bean.RoleAuthBean;
import com.cc.system.role.bean.RoleBean;
import com.cc.system.role.form.RoleQueryForm;
import com.cc.system.role.service.RoleAuthService;
import com.cc.system.role.service.RoleService;

/**
 * 系统角色管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleAuthService roleAuthService;
	
	/**
	 * 新增角色
	 * @param roleMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.role.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ROLEMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增角色")
	public Response<String> addRole(@RequestBody Map<String, String> roleMap){
		Response<String> response = new Response<String>();
		RoleBean roleBean = new RoleBean();
		String roleName = roleMap.get("roleName");
		if (StringTools.isNullOrNone(roleName)) {
			response.setMessage("请输入角色名称");
			return response;
		}
		List<RoleBean> roleBeanList = RoleBean.findAllByParams(RoleBean.class, "roleName", roleName);
		if (!ListTools.isEmptyOrNull(roleBeanList)) {
			response.setMessage("角色名称已存在");
			return response;
		}
		roleBean.setRoleName(roleName);
		roleBean.setCreateTime(DateTools.now());
		try {
			roleService.saveRole(roleBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 修改角色
	 * @param roleMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.role.edit" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ROLEMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改角色")
	public Response<String> updateUser(@RequestBody Map<String, String> roleMap){
		Response<String> response = new Response<String>();
		String id = roleMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少角色主键");
			return response;
		}
		RoleBean roleBean = RoleBean.get(RoleBean.class, Long.valueOf(id));
		if (roleBean==null) {
			response.setMessage("角色不存在");
			return response;
		}
		roleBean.setId(Long.valueOf(id));
		String roleName = roleMap.get("roleName");
		if (StringTools.isNullOrNone(roleName)) {
			response.setMessage("请输入角色名称");
			return response;
		}
		List<RoleBean> roleBeanList = RoleBean.findAllByParams(RoleBean.class, "roleName", roleName);
		if (!ListTools.isEmptyOrNull(roleBeanList) && (roleBeanList.stream().filter(role->!role.getId().equals(roleBean.getId())).count()>0)) {
			response.setMessage("角色名称已存在");
			return response;
		}
		roleBean.setRoleName(roleName);
		try {
			roleService.saveRole(roleBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询角色信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<RoleBean> queryRole(@PathVariable Long id){
		Response<RoleBean> response = new Response<RoleBean>();
		try {
			RoleBean roleBean = RoleBean.get(RoleBean.class, id);
			if (roleBean==null) {
				response.setMessage("角色不存在");
				return response;
			}
			response.setData(roleBean);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.role.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ROLEMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除角色", paramNames = {"id"})
	public Response<String> deleteRole(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			roleService.deleteRole(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询角色信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryRolePage(@ModelAttribute RoleQueryForm form){
		Page<Map<String, Object>> page = roleService.queryRolePage(form);
		return page;
	}
	
	/**
	 * 权限分配
	 * @param authorizeMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.role.authorize" })
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ROLEMANAGEMENT, operType = OperTypeEnum.AUTHORIZE, title = "权限分配")
	public Response<String> authorizeAuth(@RequestBody Map<String, Object> authorizeMap){
		Response<String> response = new Response<String>();
		Object roleId = authorizeMap.get("roleId");
		if (roleId==null) {
			response.setMessage("请选择角色");
			return response;
		}
		RoleBean roleBean = RoleBean.get(RoleBean.class, Long.valueOf(StringTools.toString(roleId)));
		if (roleBean==null) {
			response.setMessage("角色[id:"+roleId+"]不存在");
			return response;
		}
		Object object = authorizeMap.get("authList");
		if (object==null) {
			response.setMessage("请选择权限");
			return response;
		}
		List<Integer> authList = (List<Integer>) object;
		if (ListTools.isEmptyOrNull(authList)) {
			response.setMessage("请选择权限");
			return response;
		}
		List<Long> authIdList = new ArrayList<Long>();
		for (Integer auth : authList) {
			authIdList.add(Long.valueOf(StringTools.toString(auth)));
		}
		try {
			roleAuthService.updateRoleAuth(Long.valueOf(StringTools.toString(roleId)), authIdList);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询全部角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Response<Object> queryAllRoles(){
		Response<Object> response = new Response<Object>();
		List<RoleBean> roleBeanList = RoleBean.findAllByParams(RoleBean.class);
		if (!ListTools.isEmptyOrNull(roleBeanList)) {
			response.setSuccess(Boolean.TRUE);
			response.setData(roleBeanList);
		}
		return response;
	}
	
	/**
	 * 获取角色的权限集合
	 * @param roleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{roleId}/auths", method = RequestMethod.GET)
	public Response<Object> queryUserRoles(@PathVariable Long roleId){
		Response<Object> response = new Response<Object>();
		List<Long> roleList = new ArrayList<Long>();
		roleList.add(roleId);
		List<RoleAuthBean> roleAuthBeanList = roleAuthService.queryRoleAuthBeanList(roleList);
		List<Long> authList = new ArrayList<Long>();
		if (!ListTools.isEmptyOrNull(roleAuthBeanList)) {
			for (RoleAuthBean roleAuthBean : roleAuthBeanList) {
				authList.add(roleAuthBean.getAuthId());
			}
		}
		response.setSuccess(Boolean.TRUE);
		response.setData(authList);
		return response;
	}
}
