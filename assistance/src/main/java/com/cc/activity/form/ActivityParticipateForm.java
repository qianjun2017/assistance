/**
 * 
 */
package com.cc.activity.form;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class ActivityParticipateForm {

	/**
	 * 活动
	 */
	private Long activityId; 
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 是否允许自动审批
	 */
	private Boolean autoAudit;
	
	/**
	 * 图片列表
	 */
	private List<ActivityParticipateImageForm> imageList;

	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * @return the imageList
	 */
	public List<ActivityParticipateImageForm> getImageList() {
		return imageList;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param imageList the imageList to set
	 */
	public void setImageList(List<ActivityParticipateImageForm> imageList) {
		this.imageList = imageList;
	}

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
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
}
