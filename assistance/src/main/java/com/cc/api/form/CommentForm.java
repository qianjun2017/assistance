package com.cc.api.form;

public class CommentForm {

	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 影片评论
	 */
	private String comment;

	public Long getLeaguerId() {
		return leaguerId;
	}

	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}

	public Long getFilmId() {
		return filmId;
	}

	public void setFilmId(Long filmId) {
		this.filmId = filmId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
