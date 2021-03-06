/**
 * 
 */
package com.cc.novel.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class NovelQueryForm extends QueryForm {

	/**
	 * 小说名称
	 */
	private String name;
	
	/**
	 * 小说状态
	 */
	private String status;
	
	/**
	 * 小说完结状态
	 */
	private String novelStatus;
	
	/**
	 * 作者名
	 */
	private String authorName;
	
	/**
	 * 关键字
	 */
	private String keywords;
	
	/**
	 * 小说类型
	 */
	private String type;
	
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
	 * 是否下载
	 */
	private String downloading;
	
	/**
	 * 最新章节名称
	 */
	private String lastChapter;
	
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
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public String getDownloading() {
		return downloading;
	}

	public void setDownloading(String downloading) {
		this.downloading = downloading;
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
