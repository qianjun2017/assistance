/**
 * 
 */
package com.cc.system.role.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_system_role_auth")
public class RoleAuthBean extends BaseOrm<RoleAuthBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8823002221112950093L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 角色id
	 */
	private Long roleId;
	
	/**
	 * 权限id
	 */
	private Long authId;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the roleId
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * @return the authId
	 */
	public Long getAuthId() {
		return authId;
	}

	/**
	 * @param authId the authId to set
	 */
	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
