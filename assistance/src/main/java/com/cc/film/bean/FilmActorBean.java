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
 * 影片演员
 * @author Administrator
 *
 */
@Table(name="t_film_actor")
public class FilmActorBean extends BaseOrm<FilmActorBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 452998002444158391L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 演员
	 */
	private Long actorId;
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
	 * @return the actorId
	 */
	public Long getActorId() {
		return actorId;
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
	 * @param actorId the actorId to set
	 */
	public void setActorId(Long actorId) {
		this.actorId = actorId;
	}

}
