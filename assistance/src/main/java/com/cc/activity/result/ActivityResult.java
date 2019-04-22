/**
 * 
 */
package com.cc.activity.result;

import java.util.List;

import com.cc.activity.bean.ActivityBean;
import com.cc.activity.bean.ActivityImageBean;
import com.cc.activity.bean.ActivityImageSampleBean;
import com.cc.system.location.bean.LocationBean;

/**
 * @author Administrator
 *
 */
public class ActivityResult {

	/**
	 * 活动
	 */
	private ActivityBean activity;
	
	/**
	 * 活动介绍
	 */
	private String plot;
	
	/**
	 * 活动详情
	 */
	private String description;
	
	/**
     * 活动区域
     */
    private LocationBean locationBean;
    
    /**
     * 活动区域
     */
    private Long[] locationArray;
	
	/**
	 * 活动图片
	 */
	private List<ActivityImageBean> activityImageList;
	
	/**
	 * 频道列表
	 */
	private List<Long> channelList;
	
	/**
	 * 活动示例
	 */
	private List<ActivityImageSampleBean> activityImageSampleList;

	/**
	 * @return the activity
	 */
	public ActivityBean getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(ActivityBean activity) {
		this.activity = activity;
	}

	/**
	 * @return the plot
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(String plot) {
		this.plot = plot;
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

	/**
	 * @return the locationBean
	 */
	public LocationBean getLocationBean() {
		return locationBean;
	}

	/**
	 * @param locationBean the locationBean to set
	 */
	public void setLocationBean(LocationBean locationBean) {
		this.locationBean = locationBean;
	}

	/**
	 * @return the locationArray
	 */
	public Long[] getLocationArray() {
		return locationArray;
	}

	/**
	 * @param locationArray the locationArray to set
	 */
	public void setLocationArray(Long[] locationArray) {
		this.locationArray = locationArray;
	}

	/**
	 * @return the activityImageList
	 */
	public List<ActivityImageBean> getActivityImageList() {
		return activityImageList;
	}

	/**
	 * @param activityImageList the activityImageList to set
	 */
	public void setActivityImageList(List<ActivityImageBean> activityImageList) {
		this.activityImageList = activityImageList;
	}

	/**
	 * @return the activityImageSampleList
	 */
	public List<ActivityImageSampleBean> getActivityImageSampleList() {
		return activityImageSampleList;
	}

	/**
	 * @param activityImageSampleList the activityImageSampleList to set
	 */
	public void setActivityImageSampleList(List<ActivityImageSampleBean> activityImageSampleList) {
		this.activityImageSampleList = activityImageSampleList;
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
}
