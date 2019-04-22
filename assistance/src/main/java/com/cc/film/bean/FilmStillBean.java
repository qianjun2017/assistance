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
@Table(name="t_film_still")
public class FilmStillBean extends BaseOrm<FilmStillBean> implements BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -886354667865056208L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 剧照
	 */
	private String stillUrl;

	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	/**
	 * @return the filmId
	 */
	public Long getFilmId() {
		return filmId;
	}

	/**
	 * @param filmId the filmId to set
	 */
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	/**
	 * @return the stillUrl
	 */
	public String getStillUrl() {
		return stillUrl;
	}

	/**
	 * @param stillUrl the stillUrl to set
	 */
	public void setStillUrl(String stillUrl) {
		this.stillUrl = stillUrl;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
