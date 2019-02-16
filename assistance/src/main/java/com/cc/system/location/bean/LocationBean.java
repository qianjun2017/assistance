/**
 * 
 */
package com.cc.system.location.bean;

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
@Table(name="t_system_location")
public class LocationBean extends BaseOrm<LocationBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4504626375467710491L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 地区编码
	 */
	private String locationCode;
	
	/**
	 * 地区名称
	 */
	private String locationName;
	
	/**
	 * 地区路径
	 */
	private String locationCodePath;

	/**
	 * 地区路径
	 */
	private String locationNamePath;
	
	/**
	 * 父级地区
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
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}
	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	/**
	 * @return the locationCodePath
	 */
	public String getLocationCodePath() {
		return locationCodePath;
	}
	/**
	 * @param locationCodePath the locationCodePath to set
	 */
	public void setLocationCodePath(String locationCodePath) {
		this.locationCodePath = locationCodePath;
	}
	/**
	 * @return the locationNamePath
	 */
	public String getLocationNamePath() {
		return locationNamePath;
	}
	/**
	 * @param locationNamePath the locationNamePath to set
	 */
	public void setLocationNamePath(String locationNamePath) {
		this.locationNamePath = locationNamePath;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

}
