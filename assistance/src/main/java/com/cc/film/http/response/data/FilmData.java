/**
 * 
 */
package com.cc.film.http.response.data;

import java.util.List;

/**
 * 影片数据
 * @author Administrator
 *
 */
public class FilmData {

	/**
	 * 目标网站主键
	 */
	private String id;
	
	/**
	 * 列表主图
	 */
	private String listImg;
	
	/**
	 * 详情主图
	 */
	private String img;
	
	/**
	 * 影片名称
	 */
	private String name;
	
	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * 年份
	 */
	private String year;
	
	/**
	 * 语言
	 */
	private String language;
	
	/**
	 * 情节
	 */
	private String plot;
	
	/**
	 * 爬虫编码
	 */
	private String spiderNo;
	
	/**
	 * 演员列表
	 */
	private List<FilmActorData> actors;
	
	/**
	 * 导演列表
	 */
	private List<FilmDirectorData> directors;
	
	/**
	 * 播放地址列表
	 */
	private List<FilmUrlData> urls;
	
	/**
	 * 播放地址
	 */
	private String url;
	
	/**
	 * 目标详情页面地址
	 */
	private String detailUrl;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the listImg
	 */
	public String getListImg() {
		return listImg;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the plot
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * @return the actors
	 */
	public List<FilmActorData> getActors() {
		return actors;
	}

	/**
	 * @return the directors
	 */
	public List<FilmDirectorData> getDirectors() {
		return directors;
	}

	/**
	 * @return the urls
	 */
	public List<FilmUrlData> getUrls() {
		return urls;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param listImg the listImg to set
	 */
	public void setListImg(String listImg) {
		this.listImg = listImg;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(String plot) {
		this.plot = plot;
	}

	/**
	 * @param actors the actors to set
	 */
	public void setActors(List<FilmActorData> actors) {
		this.actors = actors;
	}

	/**
	 * @param directors the directors to set
	 */
	public void setDirectors(List<FilmDirectorData> directors) {
		this.directors = directors;
	}

	/**
	 * @param urls the urls to set
	 */
	public void setUrls(List<FilmUrlData> urls) {
		this.urls = urls;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}

	/**
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}

	/**
	 * @return the detailUrl
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * @param detailUrl the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
}
