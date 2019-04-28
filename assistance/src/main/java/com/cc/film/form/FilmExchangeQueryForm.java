/**
 * 
 */
package com.cc.film.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class FilmExchangeQueryForm extends QueryForm {

	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 影片
	 */
	private Long filmId;

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
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
}
