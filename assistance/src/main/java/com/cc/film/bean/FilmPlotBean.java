/**
 * 
 */
package com.cc.film.bean;

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
@Table(name="t_film_plot")
public class FilmPlotBean extends BaseOrm<FilmPlotBean> implements BaseEntity {

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
	 * 影片
	 */
	private Long filmId;
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
	 * @return the filmId
	 */
	public Long getFilmId() {
		return filmId;
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
	 * @param filmId the filmId to set
	 */
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

}
