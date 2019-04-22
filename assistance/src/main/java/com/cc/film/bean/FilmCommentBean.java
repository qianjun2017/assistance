/**
 * 
 */
package com.cc.film.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_film_comment")
public class FilmCommentBean extends BaseOrm<FilmCommentBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8244734891416589243L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 评论
	 */
	private byte[] comment;
	
	/**
	 * 影片
	 */
	private Long filmId;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 会员昵称
	 */
	private String leaguerName;
	
	/**
	 * 影片名称
	 */
	private String filmName;
	
	/**
	 * 评论时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the comment
	 */
	public byte[] getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(byte[] comment) {
		this.comment = comment;
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
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

}
