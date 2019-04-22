/**
 * 
 */
package com.cc.channel.form;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class ChannelSubjectItemQueryForm {

	/**
	 * 专题
	 */
	private Long subjectId;
	
	/**
	 * 频道
	 */
	private String channelCode;
	
	/**
	 * 频道专题加载是否启用地域限制
	 */
	private Boolean location = Boolean.FALSE;
	
	/**
	 * 综合频道专题加载是否启用普通频道限制
	 */
	private Boolean ordinary = Boolean.FALSE;
	
	/**
	 * 销售地区
	 */
	private List<Long> locationIdList;
	
	/**
	 * 销售地区
	 */
	private Long locationId;
	
	/**
	 * 销售频道
	 */
	private List<Long> channelIdList;

	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}

	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @return the location
	 */
	public Boolean getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Boolean location) {
		this.location = location;
	}

	/**
	 * @return the ordinary
	 */
	public Boolean getOrdinary() {
		return ordinary;
	}

	/**
	 * @param ordinary the ordinary to set
	 */
	public void setOrdinary(Boolean ordinary) {
		this.ordinary = ordinary;
	}

	/**
	 * @return the locationIdList
	 */
	public List<Long> getLocationIdList() {
		return locationIdList;
	}

	/**
	 * @param locationIdList the locationIdList to set
	 */
	public void setLocationIdList(List<Long> locationIdList) {
		this.locationIdList = locationIdList;
	}

	/**
	 * @return the locationId
	 */
	public Long getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the channelIdList
	 */
	public List<Long> getChannelIdList() {
		return channelIdList;
	}

	/**
	 * @param channelIdList the channelIdList to set
	 */
	public void setChannelIdList(List<Long> channelIdList) {
		this.channelIdList = channelIdList;
	}
	
}
