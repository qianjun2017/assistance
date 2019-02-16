/**
 * 
 */
package com.cc.system.shiro;

import com.cc.common.tools.RSATools;
import com.cc.common.tools.StringTools;
import com.cc.system.user.bean.UserBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author Administrator
 *
 */
public class SecurityContextUtil {

	/**
	 * 获取当前登录用户
	 * @return
	 */
	public static UserBean getCurrentUser(){
		Subject subject = SecurityUtils.getSubject();
		UserBean userBean = (UserBean) subject.getPrincipal();
		return userBean;
	}

	/**
	 * 缓存用户RSA私钥
	 * @param privateKey
	 */
	public static void setRSAPrivateKey(String privateKey){
		SecurityUtils.getSubject().getSession().setAttribute(RSATools.PRIVATEKEY, privateKey);
	}

	/**
	 * 获取RSA私钥
	 * @return
	 */
	public static String getRSAPrivateKey(){
		return StringTools.toString(SecurityUtils.getSubject().getSession().getAttribute(RSATools.PRIVATEKEY));
	}

	/**
	 * 缓存用户DES秘钥
	 * @param key
	 */
	public static void setDESKey(String key){
		SecurityUtils.getSubject().getSession().setAttribute("desKey", key);
	}

	/**
	 * 获取DES秘钥
	 * @return
	 */
	public static String getDESKey(){
		return StringTools.toString(SecurityUtils.getSubject().getSession().getAttribute("desKey"));
	}
}
