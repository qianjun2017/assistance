/**
 * 
 */
package com.cc.film.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class FilmQueryForm extends QueryForm {

	/**
	 * 影片名称
	 */
	private String name;
	
	/**
	 * 影片年份
	 */
	private String year;
	
	/**
	 * 影片国家
	 */
	private String country;
	
	/**
	 * 影片语言
	 */
	private String language;
	
	/**
	 * 影片状态
	 */
	private String status;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;
	
	/**
	 * 演员名称
	 */
	private String actorName;
	
	/**
	 * 导演名称
	 */
	private String directorName;
	
	/**
	 * 关键字
	 */
	private String keywords;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the actorName
	 */
	public String getActorName() {
		return actorName;
	}

	/**
	 * @return the directorName
	 */
	public String getDirectorName() {
		return directorName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @param actorName the actorName to set
	 */
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	/**
	 * @param directorName the directorName to set
	 */
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	/**
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
