/**
 * 
 */
package com.cc.carousel.bean;

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
@Table(name="t_carousel_channel")
public class CarouselChannelBean extends BaseOrm<CarouselChannelBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411865979654768468L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 轮播
	 */
	private Long carouselId;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * @return the carouselId
	 */
	public Long getCarouselId() {
		return carouselId;
	}

	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param carouselId the carouselId to set
	 */
	public void setCarouselId(Long carouselId) {
		this.carouselId = carouselId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
