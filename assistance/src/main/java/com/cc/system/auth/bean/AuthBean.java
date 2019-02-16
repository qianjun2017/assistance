/**
 * 
 */
package com.cc.system.auth.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_system_auth")
public class AuthBean extends BaseOrm<AuthBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9162570443388923857L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 权限编码
	 */
	private String authCode;
	
	/**
	 * 权限名称
	 */
	private String authName;
	
	/**
	 * 权限类型
	 */
	private String authType;
	
	/**
	 * 父权限id
	 */
	private Long parentId;
	
	/**
	 * 层级
	 */
	private Integer level;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}


	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}


	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}


	/**
	 * @return the authName
	 */
	public String getAuthName() {
		return authName;
	}


	/**
	 * @param authName the authName to set
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}


	/**
	 * @return the authType
	 */
	public String getAuthType() {
		return authType;
	}


	/**
	 * @param authType the authType to set
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}


	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}


	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}


	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}


	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
