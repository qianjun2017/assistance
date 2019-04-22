/**
 * 
 */
package com.cc.activity.form;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class ActivityForm {

	private Long id;
	
	/**
	 * 允许自动审批
	 */
	private Boolean autoAudit;
	
	/**
	 * 活动名称
	 */
	private String name;
	
	/**
     * 活动区域
     */
    private Long locationId;
	
	/**
	 * 活动开始时间
	 */
	private Date startTime;
	
	/**
	 * 活动结束时间
	 */
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
	 * 活动参与类型
	 */
	private String participateType;
	
	/**
	 * 活动介绍
	 */
	private String plot;
	
	/**
	 * 活动详情
	 */
	private String description;
	
	/**
	 * 活动图片
	 */
	List<String> imageUrlList;
	
	/**
	 * 活动提交示例图片
	 */
	List<ActivityImageSampleForm> sampleImageList;
	
	/**
	 * 频道
	 */
	List<Integer> channelList;

	/**
	 * @return the id
	 */
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
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the plot
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * @return the imageUrlList
	 */
	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	/**
	 * @return the sampleImageList
	 */
	public List<ActivityImageSampleForm> getSampleImageList() {
		return sampleImageList;
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
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(String plot) {
		this.plot = plot;
	}

	/**
	 * @param imageUrlList the imageUrlList to set
	 */
	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}

	/**
	 * @param sampleImageList the sampleImageList to set
	 */
	public void setSampleImageList(List<ActivityImageSampleForm> sampleImageList) {
		this.sampleImageList = sampleImageList;
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
	 * @return the channelList
	 */
	public List<Integer> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(List<Integer> channelList) {
		this.channelList = channelList;
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

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
