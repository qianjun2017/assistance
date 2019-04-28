/**
 * 
 */
package com.cc.film.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class FilmCommentQueryForm extends QueryForm {

	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 会员昵称
	 */
	private String leaguerName;
	
	/**
	 * 影片名称
	 */
	private String filmName;

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
	 * @return the filmName
	 */
	public String getFilmName() {
		return filmName;
	}

	/**
	 * @param filmName the filmName to set
	 */
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}

	/**
	 * @return the leaguerName
	 */
	public String getLeaguerName() {
		return leaguerName;
	}

	/**
	 * @param leaguerName the leaguerName to set
	 */
	public void setLeaguerName(String leaguerName) {
		this.leaguerName = leaguerName;
	}
}
