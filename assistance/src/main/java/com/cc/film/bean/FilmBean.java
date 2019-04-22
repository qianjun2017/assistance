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
 * 影片
 * @author Administrator
 *
 */
@Table(name="t_film")
public class FilmBean extends BaseOrm<FilmBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6448467645960314972L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 影片名称
	 */
	private String name;
	
	/**
	 * 影片年份
	 */
	private String year;
	
	/**
	 * 抓取爬虫
	 */
	private String spiderNo;
	
	/**
	 * 目标网站的主键
	 */
	private String itemId;
	
	/**
	 * 列表主图
	 */
	private String listImgUrl;
	
	/**
	 * 详情主图
	 */
	private String imgUrl;
	
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
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 积分
	 */
	private Long integration;
	
	/**
	 * 点赞数
	 */
	private Long thumbUp;
	
	/**
	 * 反对数
	 */
	private Long thumbDown;
	
	/**
	 * 目标网站播放地址
	 */
	private String url;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
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
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}
	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * @return the listImgUrl
	 */
	public String getListImgUrl() {
		return listImgUrl;
	}
	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * @param listImgUrl the listImgUrl to set
	 */
	public void setListImgUrl(String listImgUrl) {
		this.listImgUrl = listImgUrl;
	}
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
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
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}
	/**
	 * @return the thumbUp
	 */
	public Long getThumbUp() {
		return thumbUp;
	}
	/**
	 * @return the thumbDown
	 */
	public Long getThumbDown() {
		return thumbDown;
	}
	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}
	/**
	 * @param thumbUp the thumbUp to set
	 */
	public void setThumbUp(Long thumbUp) {
		this.thumbUp = thumbUp;
	}
	/**
	 * @param thumbDown the thumbDown to set
	 */
	public void setThumbDown(Long thumbDown) {
		this.thumbDown = thumbDown;
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

}
