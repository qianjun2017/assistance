/**
 * 
 */
package com.cc.system.auth.web;

import java.util.HashMap;
import java.util.Map;

import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.service.UserAuthService;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.common.web.Tree;
import com.cc.system.auth.bean.AuthBean;
import com.cc.system.auth.enums.AuthTypeEnum;
import com.cc.system.auth.service.AuthService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/auth")
public class AuthController {
	
	private static Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthService authService;

	@Autowired
	private UserAuthService userAuthService;
	/**
	 * 新增权限
	 * @param authMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.auth.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.AUTHMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增权限")
	public Response<String> addAuth(@RequestBody Map<String, String> authMap){
		Response<String> response = new Response<String>();
		AuthBean authBean = new AuthBean();
		String authName = authMap.get("authName");
		if (StringTools.isNullOrNone(authName)) {
			response.setMessage("请输入权限名称");
			return response;
		}
		authBean.setAuthName(authName);
		String authCode = authMap.get("authCode");
		if (StringTools.isNullOrNone(authCode)) {
			response.setMessage("请输入权限编码");
			return response;
		}
		authBean.setAuthCode(authCode);
		String authType = authMap.get("authType");
		if (StringTools.isNullOrNone(authType)) {
			response.setMessage("请选择权限类型");
			return response;
		}
		AuthTypeEnum authTypeEnum = AuthTypeEnum.getAuthTypeEnumByCode(authType);
		if (authTypeEnum==null) {
			response.setMessage("权限类型[code:"+authType+"]错误");
			return response;
		}
		authBean.setAuthType(authType);
		String parentId = authMap.get("parentId");
		if (!StringTools.isNullOrNone(parentId)) {
			AuthBean parentAuthBean = AuthBean.get(AuthBean.class, Long.valueOf(parentId));
			if (parentAuthBean==null) {
				response.setMessage("父权限不存在,请重新选择");
				return response;
			}
			authBean.setParentId(Long.valueOf(parentId));
			authBean.setLevel(parentAuthBean.getLevel()+1);
		}else {
			authBean.setLevel(0);
		}
		authBean.setCreateTime(DateTools.now());
		try {
			authService.saveAuth(authBean);
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
	 * 修改权限
	 * @param authMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.auth.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.AUTHMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改权限")
	public Response<String> updateAuth(@RequestBody Map<String, String> authMap){
		Response<String> response = new Response<String>();
		String id = authMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少权限主键");
			return response;
		}
		AuthBean authBean = AuthBean.get(AuthBean.class, Long.valueOf(id));
		if (authBean==null) {
			response.setMessage("权限不存在");
			return response;
		}
		String authName = authMap.get("authName");
		if (StringTools.isNullOrNone(authName)) {
			response.setMessage("请输入权限名称");
			return response;
		}
		authBean.setAuthName(authName);
		String authCode = authMap.get("authCode");
		if (StringTools.isNullOrNone(authCode)) {
			response.setMessage("请输入权限编码");
			return response;
		}
		authBean.setAuthCode(authCode);
		String authType = authMap.get("authType");
		if (StringTools.isNullOrNone(authType)) {
			response.setMessage("请选择权限类型");
			return response;
		}
		AuthTypeEnum authTypeEnum = AuthTypeEnum.getAuthTypeEnumByCode(authType);
		if (authTypeEnum==null) {
			response.setMessage("权限类型[code:"+authType+"]错误");
			return response;
		}
		authBean.setAuthType(authType);
		String parentId = authMap.get("parentId");
		if (!StringTools.isNullOrNone(parentId)) {
			AuthBean parentAuthBean = AuthBean.get(AuthBean.class, Long.valueOf(parentId));
			if (parentAuthBean==null) {
				response.setMessage("父权限不存在,请重新选择");
				return response;
			}
			authBean.setParentId(Long.valueOf(parentId));
			authBean.setLevel(parentAuthBean.getLevel()+1);
		}else {
			authBean.setLevel(0);
		}
		try {
			authService.saveAuth(authBean);
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
	 * 删除权限
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.auth.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.AUTHMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除权限", paramNames = {"id"})
	public Response<String> deleteAuth(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			authService.deleteAuth(id);
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
	 * 查询权限信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.auth.detail", "system.auth.update" }, logical = Logical.OR)
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryAuth(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		Map<String, Object> authMap = new HashMap<String, Object>();
		try {
			AuthBean authBean = AuthBean.get(AuthBean.class, id);
			if (authBean==null) {
				response.setMessage("权限不存在");
				return response;
			}
			authMap.put("id", authBean.getId());
			authMap.put("authCode", authBean.getAuthCode());
			authMap.put("authName", authBean.getAuthName());
			authMap.put("authType", authBean.getAuthType());
			authMap.put("authTypeName", AuthTypeEnum.getNameByCode(authBean.getAuthType()));
			authMap.put("level", authBean.getLevel());
			authMap.put("createTime", DateTools.getFormatDate(authBean.getCreateTime(), DateTools.DATEFORMAT));
			Long parentId = authBean.getParentId();
			authMap.put("parentId", parentId);
			while (parentId!=null) {
				AuthBean parentAuthBean = AuthBean.get(AuthBean.class, Long.valueOf(parentId));
				if (parentAuthBean!=null) {
					authMap.put("parent"+parentAuthBean.getLevel(), parentAuthBean.getId());
					parentId = parentAuthBean.getParentId();
				}else {
					logger.error("查询权限详情时，找不到父权限[id:"+parentId+"]");
					parentId = null;
				}
			}
			response.setData(authMap);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询权限树
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.auth" })
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public Tree<Map<String, Object>> queryAuthTree(){
		UserBean userBean = SecurityContextUtil.getCurrentUser();
		Tree<Map<String, Object>> tree = userAuthService.queryUserAuthTree(userBean.getId());
		return tree;
	}
}
