/**
 * 
 */
package com.cc.system.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author Administrator
 * 登录凭证
 */
public class MyAuthenticationToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6418895721570671166L;
	
	/**
	 * 是否验证密码
	 */
	private Boolean needPassWord;
	
	/**
	 * 登录用户类型
	 */
	private String userType;
	
	public MyAuthenticationToken(final String username, final String password, final String userType){
		super(username, password);
		this.needPassWord = Boolean.TRUE;
		this.userType = userType;
	}
	
	public MyAuthenticationToken(final String username, final String userType){
		super();
		setUsername(username);
		this.needPassWord = Boolean.FALSE;
		this.userType = userType;
	}

	/**
	 * @return the needPassWord
	 */
	public Boolean getNeedPassWord() {
		return needPassWord;
	}

	/**
	 * @param needPassWord the needPassWord to set
	 */
	public void setNeedPassWord(Boolean needPassWord) {
		this.needPassWord = needPassWord;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}
}
