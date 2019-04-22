/**
 * 
 */
package com.cc.film.result;

import java.util.List;

import com.cc.film.bean.ActorBean;
import com.cc.film.bean.DirectorBean;
import com.cc.film.bean.FilmBean;
import com.cc.film.bean.FilmUrlBean;

/**
 * @author Administrator
 *
 */
public class FilmResult {

	/**
	 * 影片信息
	 */
	private FilmBean film;
	
	/**
	 * 演员
	 */
	private List<ActorBean> actors;
	
	/**
	 * 导演
	 */
	private List<DirectorBean> directors;
	
	/**
	 * 播放列表
	 */
	private List<FilmUrlBean> urls;
	
	/**
	 * 默认播放地址
	 */
	private String defaultUrl;
	
	/**
	 * 剧情
	 */
	private String plot;
	
	/**
	 * 播放数
	 */
	private Integer played;

	/**
	 * @return the film
	 */
	public FilmBean getFilm() {
		return film;
	}

	/**
	 * @return the actors
	 */
	public List<ActorBean> getActors() {
		return actors;
	}

	/**
	 * @return the directors
	 */
	public List<DirectorBean> getDirectors() {
		return directors;
	}

	/**
	 * @return the urls
	 */
	public List<FilmUrlBean> getUrls() {
		return urls;
	}

	/**
	 * @return the plot
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * @return the played
	 */
	public Integer getPlayed() {
		return played;
	}

	/**
	 * @param played the played to set
	 */
	public void setPlayed(Integer played) {
		this.played = played;
	}

	/**
	 * @param film the film to set
	 */
	public void setFilm(FilmBean film) {
		this.film = film;
	}

	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<ActorBean> actors) {
		this.actors = actors;
	}

	/**
	 * @param directors the directors to set
	 */
	public void setDirectors(List<DirectorBean> directors) {
		this.directors = directors;
	}

	/**
	 * @param urls the urls to set
	 */
	public void setUrls(List<FilmUrlBean> urls) {
		this.urls = urls;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(String plot) {
		this.plot = plot;
	}

	/**
	 * @return the defaultUrl
	 */
	public String getDefaultUrl() {
		return defaultUrl;
	}

	/**
	 * @param defaultUrl the defaultUrl to set
	 */
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
}
