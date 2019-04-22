/**
 * 
 */
package com.cc.system.role.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class RoleQueryForm extends QueryForm {

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
