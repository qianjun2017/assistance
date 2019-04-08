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
@Table(name="t_carousel_plot")
public class CarouselPlotBean extends BaseOrm<CarouselPlotBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3303467084693327980L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 情节
	 */
	private byte[] plot;
	
	/**
	 * 轮播
	 */
	private Long carouselId;
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
	 * @return the carouselId
	 */
	public Long getCarouselId() {
		return carouselId;
	}
	/**
	 * @param carouselId the carouselId to set
	 */
	public void setCarouselId(Long carouselId) {
		this.carouselId = carouselId;
	}

}
