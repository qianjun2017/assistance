/**
 * 
 */
package com.cc.system.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

/**
 * @author Administrator
 *
 */
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {

	/* (non-Javadoc)
	 * @see org.apache.shiro.authc.credential.HashedCredentialsMatcher#doCredentialsMatch(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationInfo)
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		MyAuthenticationToken myAuthenticationToken = (MyAuthenticationToken)token;
		if(Boolean.FALSE.equals(myAuthenticationToken.getNeedPassWord())){
			return true;
		}
		return super.doCredentialsMatch(token, info);
	}

}
