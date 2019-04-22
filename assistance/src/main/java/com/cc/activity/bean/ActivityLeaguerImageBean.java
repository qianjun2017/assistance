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
@Table(name="t_activity_leaguer_image")
public class ActivityLeaguerImageBean extends BaseOrm<ActivityLeaguerImageBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5849925589361048504L;

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
	 * 参与
	 */
	private Long participateId;
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
	 * @return the participateId
	 */
	public Long getParticipateId() {
		return participateId;
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
	 * @param participateId the participateId to set
	 */
	public void setParticipateId(Long participateId) {
		this.participateId = participateId;
	}
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

}
