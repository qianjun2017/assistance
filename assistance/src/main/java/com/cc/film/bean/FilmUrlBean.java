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
@Table(name="t_film_url")
public class FilmUrlBean extends BaseOrm<FilmUrlBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -419021357862295473L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 播放地址
	 */
	private String url;
	
	/**
	 * 是否为默认地址
	 */
	private Boolean defaultUrl = Boolean.FALSE;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the filmId
	 */
	public Long getFilmId() {
		return filmId;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the defaultUrl
	 */
	public Boolean getDefaultUrl() {
		return defaultUrl;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param filmId the filmId to set
	 */
	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(Boolean defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

}
