/**
 * 
 */
package com.cc.activity.form;

import java.util.List;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class ActivityQueryForm extends QueryForm {

	/**
	 * 活动名称
	 */
	private String name;
	
	/**
	 * 活动区域
	 */
	private List<Long> locationIdList;
	
	/**
	 * 活动状态
	 */
	private String status;
	
	/**
	 * 活动类型
	 */
	private String type;
	
	/**
	 * 频道列表
	 */
	private List<Long> channelList;
	
	/**
	 * 会员-执行当前查询的会员
	 */
	private Long leaguerId;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the channelList
	 */
	public List<Long> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(List<Long> channelList) {
		this.channelList = channelList;
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
}
