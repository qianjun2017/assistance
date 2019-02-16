/**
 * 
 */
package com.cc.system.shiro.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cc.system.user.service.UserAuthService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cc.common.tools.ListTools;
import com.cc.system.auth.bean.AuthBean;
import com.cc.system.user.bean.UserBean;

/**
 * @author Administrator
 *
 */
public class MyRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);
	
	@Autowired
	private UserAuthService userAuthService;

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserBean userBean = (UserBean) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		Set<String> stringPermissions = new HashSet<String>();
		List<AuthBean> authBeanList = userAuthService.queryUserAuthList(userBean.getId());
		if(!ListTools.isEmptyOrNull(authBeanList)) {
			for (AuthBean authBean : authBeanList) {
				stringPermissions.add(authBean.getAuthCode());
			}
		}
		simpleAuthorizationInfo.setStringPermissions(stringPermissions);
		return simpleAuthorizationInfo;
	}

	/**
	 * 登陆认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
		logger.info("验证当前Subject的token为:"+token.toString());
		String userName = token.getUsername();
		List<UserBean> userBeanList = UserBean.findAllByParams(UserBean.class, "userName", userName);
		if (ListTools.isEmptyOrNull(userBeanList)) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		UserBean userBean = userBeanList.get(0);
		ByteSource credentialsSalt = ByteSource.Util.bytes(userBean.getSalt());
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userBean, userBean.getPassword(), credentialsSalt, getName());
        return authenticationInfo;
	}

}
