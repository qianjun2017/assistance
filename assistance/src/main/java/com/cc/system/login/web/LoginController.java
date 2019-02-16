/**
 * 
 */
package com.cc.system.login.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DESTools;
import com.cc.common.tools.RSATools;
import com.cc.system.user.service.UserAuthService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.system.auth.bean.AuthBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping
public class LoginController {
	
	@Autowired
	private UserAuthService userAuthService;

	/**
	 * 登录
	 * @param loginMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOGINMANAGEMENT, operType = OperTypeEnum.LOGIN, title = "登录")
	public Response<String> login(@RequestBody Map<String, String> loginMap){
		Response<String> response = new Response<String>();
		if (StringTools.isAnyNullOrNone(new String[]{loginMap.get("userName"),loginMap.get("password")})) {
			response.setMessage("用户名或密码不能为空");
			return response;
		}
		String userName = DESTools.decrypt(loginMap.get("userName"), SecurityContextUtil.getDESKey());
		String password = DESTools.decrypt(loginMap.get("password"), SecurityContextUtil.getDESKey());
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			response.setSuccess(Boolean.TRUE);
		} catch (IncorrectCredentialsException e) {
			response.setMessage("用户名或密码错误");
		} catch (AuthenticationException e) {
			response.setMessage(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			response.setMessage("未知错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 退出
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOGINMANAGEMENT, operType = OperTypeEnum.LOGOUT, title = "退出")
	public Response<String> logout(){
		Response<String> response = new Response<String>();
		UserBean sysUser = SecurityContextUtil.getCurrentUser();
		if (sysUser==null) {
			response.setMessage("您尚未登录");
			return response;
		}
		SecurityUtils.getSubject().logout();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 首页
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public Response<String> index(){
		Response<String> response = new Response<String>();
		response.setSuccess(Boolean.TRUE);
		UserBean sysUser = SecurityContextUtil.getCurrentUser();
		if (sysUser==null) {
			response.setMessage("您尚未登录");
			return response;
		}
		response.setData("欢迎您,"+sysUser.getNickName()+"!");
		return response;
	}
	
	/**
	 * 未授权
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/unauth", method = RequestMethod.GET)
	public Response<String> unauth(){
		Response<String> response = new Response<String>();
		response.setMessage("没有权限");
		return response;
	}
	
	/**
	 * 未登陆
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/", method = RequestMethod.GET)
	public Response<String> unlogin(){
		Response<String> response = new Response<String>();
		response.setMessage("没有登陆或session已过期");
		return response;
	}
	
	/**
	 * 当前登录的用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/currUser", method = RequestMethod.GET)
	public Response<UserBean> queryCurrUser(){
		Response<UserBean> response = new Response<UserBean>();
		UserBean sysUser = SecurityContextUtil.getCurrentUser();
		if (sysUser==null) {
			response.setMessage("您尚未登录");
			return response;
		}
		sysUser.setSalt(null);
		sysUser.setPassword(null);
		response.setData(sysUser);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 保持session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/keepSession", method = RequestMethod.POST)
	public Response<String> keepSession(){
		Response<String> response = new Response<String>();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 当前登录的用户权限信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/currUserPermission", method = RequestMethod.GET)
	public Response<Set<String>> queryCurrUserPermission(){
		Response<Set<String>> response = new Response<Set<String>>();
		UserBean sysUser = SecurityContextUtil.getCurrentUser();
		Set<String> stringPermissions = new HashSet<String>();
		List<AuthBean> authBeanList = userAuthService.queryUserAuthList(sysUser.getId());
		if(!ListTools.isEmptyOrNull(authBeanList)) {
			for (AuthBean authBean : authBeanList) {
				stringPermissions.add(authBean.getAuthCode());
			}
		}
		response.setData(stringPermissions);
		response.setSuccess(Boolean.TRUE);
		return response;
	}

	/**
	 * 获取RSA公钥
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/key", method = RequestMethod.GET)
	public Response<String> createRSAPublicKey(){
		Response<String> response = new Response<String>();
		Map<String, String> keyMap = RSATools.initKey(1024);
		SecurityContextUtil.setRSAPrivateKey(keyMap.get(RSATools.PRIVATEKEY));
		response.setData(keyMap.get(RSATools.PUBLICKEY));
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 设置数据密钥
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/key", method = RequestMethod.POST)
	public Response<String> setKey(@RequestBody Map<String, String> keyMap){
		Response<String> response = new Response<String>();
		try {
			String key = RSATools.privateDecrypt(keyMap.get("key"), SecurityContextUtil.getRSAPrivateKey());
			SecurityContextUtil.setDESKey(key);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("未知错误");
			e.printStackTrace();
		}
		return response;
	}
	
}
