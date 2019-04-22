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
@Table(name="t_activity_image")
public class ActivityImageBean extends BaseOrm<ActivityImageBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3997410110612754519L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 图片地址
	 */
	private String imageUrl;
	
	/**
	 * 图片序号
	 */
	private Integer imageNo;
	
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
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the imageNo
	 */
	public Integer getImageNo() {
		return imageNo;
	}

	/**
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @param imageNo the imageNo to set
	 */
	public void setImageNo(Integer imageNo) {
		this.imageNo = imageNo;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

}
