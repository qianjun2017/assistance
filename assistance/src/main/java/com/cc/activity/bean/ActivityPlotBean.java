/**
 * 
 */
package com.cc.activity.bean;

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
@Table(name="t_activity_plot")
public class ActivityPlotBean extends BaseOrm<ActivityPlotBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4836485600851037130L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 活动介绍
	 */
	private byte[] plot;
	
	/**
	 * 活动详情
	 */
	private byte[] description;
	
	/**
	 * 活动
	 */
	private Long activityId;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the plot
	 */
	public byte[] getPlot() {
		return plot;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(byte[] plot) {
		this.plot = plot;
	}

	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the description
	 */
	public byte[] getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(byte[] description) {
		this.description = description;
	}

}
