/**
 * 
 */
package com.cc.activity.bean;

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
@Table(name="t_activity")
public class ActivityBean extends BaseOrm<ActivityBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8304203468046126767L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 活动名称
	 */
	private String name;
	
	/**
	 * 活动参与类型  daily-每日参与1次  whole-整个活动参与1次
	 */
	private String participateType;
	
	/**
	 * 活动状态
	 */
	private String status;
	
	/**
	 * 活动发布时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 活动开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	
	/**
	 * 活动结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
	
	/**
	 * 积分
	 */
	private Long integration;
	
	/**
	 * 活动类型
	 */
	private String type;
	
	/**
	 * 允许自动审批
	 */
	private Boolean autoAudit;
	
	/**
	 * 列表图
	 */
	private String imgUrl;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the autoAudit
	 */
	public Boolean getAutoAudit() {
		return autoAudit;
	}

	/**
	 * @param autoAudit the autoAudit to set
	 */
	public void setAutoAudit(Boolean autoAudit) {
		this.autoAudit = autoAudit;
	}

	/**
	 * @return the participateType
	 */
	public String getParticipateType() {
		return participateType;
	}

	/**
	 * @param participateType the participateType to set
	 */
	public void setParticipateType(String participateType) {
		this.participateType = participateType;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
