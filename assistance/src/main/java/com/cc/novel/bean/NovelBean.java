/**
 * 
 */
package com.cc.novel.bean;

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
@Table(name="t_novel")
public class NovelBean extends BaseOrm<NovelBean> implements BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9153453713963886172L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 小说名称
	 */
	private String name;
	
	/**
	 * 抓取爬虫
	 */
	private String spiderNo;
	
	/**
	 * 目标网站的主键
	 */
	private String itemId;
	
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
	 * 小说类型
	 */
	private String type;	
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
	 * 下载中
	 */
	private String downloading;

	/**
	 * 小说地址
	 */
	private String url;
	
	/**
	 * 最后更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
	
	/**
	 * 最后更新章节名称
	 */
	private String lastChapter;
	
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
