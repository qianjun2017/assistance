/**
 * 
 */
package com.cc.novel.result;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
public class NovelResult {

	private Long id;
	
	/**
	 * 小说名称
	 */
	private String name;
	
	/**
	 * 详情主图
	 */
	private String imgUrl;
	
	/**
	 * 小说状态
	 */
	private String novelStatus;
	
	/**
	 * 小说上架状态
	 */
	private String status;
	
	/**
	 * 积分
	 */
	private Long integration;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	/**
	 * 小说简介
	 */
	private String plot;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 下载中
	 */
	private String downloading;
	
	/**
	 * 小说源地址
	 */
	private String url;
	
	/**
	 * 小说类型
	 */
	private String type;
	
	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
	
	/**
	 * 最后更新章节名称
	 */
	private String lastChapter;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the novelStatus
	 */
	public String getNovelStatus() {
		return novelStatus;
	}

	/**
	 * @param novelStatus the novelStatus to set
	 */
	public void setNovelStatus(String novelStatus) {
		this.novelStatus = novelStatus;
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
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the plot
	 */
	public String getPlot() {
		return plot;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(String plot) {
		this.plot = plot;
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
	 * @return the downloading
	 */
	public String getDownloading() {
		return downloading;
	}

	/**
	 * @param downloading the downloading to set
	 */
	public void setDownloading(String downloading) {
		this.downloading = downloading;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the lastTime
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return the lastChapter
	 */
	public String getLastChapter() {
		return lastChapter;
	}

	/**
	 * @param lastChapter the lastChapter to set
	 */
	public void setLastChapter(String lastChapter) {
		this.lastChapter = lastChapter;
	}
}
