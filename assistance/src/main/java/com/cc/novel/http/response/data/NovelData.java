/**
 * 
 */
package com.cc.novel.http.response.data;

/**
 * @author Administrator
 *
 */
public class NovelData {

	/**
	 * 目标网站主键
	 */
	private String id;
	
	/**
	 * 小说名称
	 */
	private String name;
	
	/**
	 * 作者名
	 */
	private String author;
	
	/**
	 * 小说简介
	 */
	private String intro;
	
	/**
	 * 小说图片
	 */
	private String img;
	
	/**
	 * 小说状态
	 */
	private String status;
	
	/**
	 * 爬虫编码
	 */
	private String spiderNo;

	/**
	 * 目标网站小说地址
	 */
	private String url;
	
	/**
	 * 小说类型
	 */
	private String type;
	
	/**
	 * 最后更新章节名称
	 */
	private String lastChapter;
	
	/**
	 * 最后更新时间
	 */
	private String lastTime;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the intro
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * @param intro the intro to set
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public void setImg(String img) {
		this.img = img;
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

	public String getUrl() {
		return url;
	}

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

	/**
	 * @return the lastTime
	 */
	public String getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime the lastTime to set
	 */
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
}
