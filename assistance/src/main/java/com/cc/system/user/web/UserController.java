/**
 * 
 */
package com.cc.system.user.web;

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
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.bean.UserRoleBean;
import com.cc.system.user.form.UserQueryForm;
import com.cc.system.user.service.UserRoleService;
import com.cc.system.user.service.UserService;
import com.cc.system.user.utils.PasswordUtil;

/**
 * 系统人员管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 新增用户
	 * @param userMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.USERMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增用户", excludeParamNames = {"password"})
	public Response<String> addUser(@RequestBody Map<String, String> userMap){
		Response<String> response = new Response<String>();
		UserBean userBean = new UserBean();
		String userName = userMap.get("userName");
		if (StringTools.isNullOrNone(userName)) {
			response.setMessage("请输入用户名");
			return response;
		}
		List<UserBean> userBeanList = UserBean.findAllByParams(UserBean.class, "userName", userName);
		if (!ListTools.isEmptyOrNull(userBeanList)) {
			response.setMessage("用户名已存在");
			return response;
		}
		userBean.setUserName(userName);
		String password = userMap.get("password");
		if (StringTools.isNullOrNone(password)) {
			response.setMessage("请输入密码");
			return response;
		}
		userBean.setSalt(PasswordUtil.getSalt(5));
		userBean.setPassword(PasswordUtil.encrypt(password, userBean.getSalt()));
		userBean.setNickName(userMap.get("nickName"));
		if (StringTools.isNullOrNone(userBean.getNickName())) {
			userBean.setNickName(userName);
		}
		userBean.setRealName(userMap.get("realName"));
		userBean.setPhone(userMap.get("phone"));
		String orgId = userMap.get("orgId");
		if (!StringTools.isNullOrNone(orgId)) {
			userBean.setOrgId(Long.valueOf(orgId));
		}
		userBean.setCreateTime(DateTools.now());
		try {
			userService.saveUser(userBean);
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
	 * 修改用户
	 * @param userMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user.edit" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.USERMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改用户", excludeParamNames = {"password"})
	public Response<String> updateUser(@RequestBody Map<String, String> userMap){
		Response<String> response = new Response<String>();
		String id = userMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少用户主键");
			return response;
		}
		UserBean userBean = UserBean.get(UserBean.class, Long.valueOf(id));
		if (userBean==null) {
			response.setMessage("用户不存在");
			return response;
		}
		userBean.setId(Long.valueOf(id));
		String userName = userMap.get("userName");
		if (StringTools.isNullOrNone(userName)) {
			response.setMessage("请输入用户名");
			return response;
		}
		List<UserBean> userBeanList = UserBean.findAllByParams(UserBean.class, "userName", userName);
		if (!ListTools.isEmptyOrNull(userBeanList) && (userBeanList.stream().filter(user->!user.getId().equals(userBean.getId())).count()>0)) {
			response.setMessage("用户名已存在");
			return response;
		}
		userBean.setUserName(userName);
		String password = userMap.get("password");
		if(!StringTools.isNullOrNone(password)){
			userBean.setSalt(PasswordUtil.getSalt(5));
			userBean.setPassword(PasswordUtil.encrypt(password, userBean.getSalt()));
		}
		userBean.setNickName(userMap.get("nickName"));
		if (StringTools.isNullOrNone(userBean.getNickName())) {
			userBean.setNickName(userName);
		}
		userBean.setRealName(userMap.get("realName"));
		userBean.setPhone(userMap.get("phone"));
		String orgId = userMap.get("orgId");
		if (!StringTools.isNullOrNone(orgId)) {
			userBean.setOrgId(Long.valueOf(orgId));
		}
		try {
			userService.saveUser(userBean);
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
	 * 查询用户信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user.edit" })
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<UserBean> queryUser(@PathVariable Long id){
		Response<UserBean> response = new Response<UserBean>();
		try {
			UserBean userBean = UserBean.get(UserBean.class, id);
			if (userBean==null) {
				response.setMessage("用户不存在");
				return response;
			}
			userBean.setPassword(null);
			response.setData(userBean);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.USERMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除用户", paramNames = {"id"})
	public Response<String> deleteUser(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			userService.deleteUser(id);
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
	 * 分页查询用户信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user" })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryUserPage(@ModelAttribute UserQueryForm form){
		Page<Map<String, Object>> page = userService.queryUserPage(form);
		return page;
	}
	
	/**
	 * 角色分配
	 * @param authorizeMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.user.authorize" })
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.USERMANAGEMENT, operType = OperTypeEnum.AUTHORIZE, title = "角色分配")
	public Response<String> authorizeRole(@RequestBody Map<String, Object> authorizeMap){
		Response<String> response = new Response<String>();
		Object userId = authorizeMap.get("userId");
		if (userId==null) {
			response.setMessage("请选择用户");
			return response;
		}
		UserBean userBean = UserBean.get(UserBean.class, Long.valueOf(StringTools.toString(userId)));
		if (userBean==null) {
			response.setMessage("角色[id:"+userId+"]不存在");
			return response;
		}
		Object object = authorizeMap.get("roleList");
		if (object==null) {
			response.setMessage("请选择权限");
			return response;
		}
		List<Integer> roleList = (List<Integer>) object;
		if (ListTools.isEmptyOrNull(roleList)) {
			response.setMessage("请选择角色");
			return response;
		}
		List<Long> roleIdList = new ArrayList<Long>();
		for (Integer role : roleList) {
			roleIdList.add(Long.valueOf(StringTools.toString(role)));
		}
		try {
			userRoleService.updateUserRole(Long.valueOf(StringTools.toString(userId)), roleIdList);
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
	 * 获取人员的角色集合
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{userId}/roles", method = RequestMethod.GET)
	public Response<Object> queryUserRoles(@PathVariable Long userId){
		Response<Object> response = new Response<Object>();
		List<UserRoleBean> userRoleBeanList = userRoleService.queryUserRoleBeanList(userId);
		List<Long> roleList = new ArrayList<Long>();
		if (!ListTools.isEmptyOrNull(userRoleBeanList)) {
			for (UserRoleBean userRoleBean : userRoleBeanList) {
				roleList.add(userRoleBean.getRoleId());
			}
		}
		response.setSuccess(Boolean.TRUE);
		response.setData(roleList);
		return response;
	}
	
	/**
	 * 用户个人设置
	 * @param userMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.setting" })
	@RequestMapping(value = "/setting/save", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.USERMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "个人设置", excludeParamNames = {"password"})
	public Response<String> updateUserSetting(@RequestBody Map<String, String> userMap){
		Response<String> response = new Response<String>();
		String id = userMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少用户主键");
			return response;
		}
		UserBean userBean = UserBean.get(UserBean.class, Long.valueOf(id));
		if (userBean==null) {
			response.setMessage("用户不存在");
			return response;
		}
		UserBean updateUserBean = new UserBean();
		updateUserBean.setId(userBean.getId());
		String nickName = userMap.get("nickName");
		if(!StringTools.isNullOrNone(nickName)){
			LogContextUtil.setOperContent("修改用户昵称:从【"+userBean.getNickName()+"】变更为【"+nickName+"】");
		}
		updateUserBean.setNickName(nickName);
		String realName = userMap.get("realName");
		if(!StringTools.isNullOrNone(realName)){
			LogContextUtil.setOperContent("用户实名:【"+realName+"】");
		}
		updateUserBean.setRealName(realName);
		String password = userMap.get("password");
		if(!StringTools.isNullOrNone(password)){
			updateUserBean.setSalt(PasswordUtil.getSalt(5));
			updateUserBean.setPassword(PasswordUtil.encrypt(password, updateUserBean.getSalt()));
		}
		String phone = userMap.get("phone");
		if(!StringTools.isNullOrNone(phone)){
			if(!StringTools.matches(phone, "^1[34578]\\d{9}$")){
	        	response.setMessage("请输入11位有效手机号码");
	            return response;
	        }
			LogContextUtil.setOperContent(StringTools.isNullOrNone(userBean.getPhone())?"绑定用户手机号:【"+phone+"】":"修改用户手机号:从【"+userBean.getPhone()+"】变更为【"+phone+"】");
		}
		updateUserBean.setPhone(phone);
		String imageUrl = userMap.get("imageUrl");
		if(!StringTools.isNullOrNone(imageUrl)){
			LogContextUtil.setOperContent(StringTools.isNullOrNone(userBean.getImageUrl())?"上传用户头像:【"+imageUrl+"】":"修改用户头像:从【"+userBean.getImageUrl()+"】变更为【"+imageUrl+"】");
		}
		updateUserBean.setImageUrl(imageUrl);
		try {
			userService.updateUser(updateUserBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
