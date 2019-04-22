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
 * 影片导演
 * @author Administrator
 *
 */
@Table(name="t_film_director")
public class FilmDirectorBean extends BaseOrm<FilmDirectorBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497926948626816875L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 导演
	 */
	private Long directorId;
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
	 * @return the directorId
	 */
	public Long getDirectorId() {
		return directorId;
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
	 * @param directorId the directorId to set
	 */
	public void setDirectorId(Long directorId) {
		this.directorId = directorId;
	}

}
