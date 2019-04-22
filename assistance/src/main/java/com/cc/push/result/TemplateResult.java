/**
 * 
 */
package com.cc.push.result;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author ws_yu
 *
 */
public class TemplateResult {

	private Long id;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 模板Id
	 */
	private String templateId;
	
	/**
	 * 模板标题
	 */
	private String title;
	
	/**
	 * 模板关键字列表
	 */
	private List<TemplateKeywordResult> keywordList;

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
	 * @return the templateId
	 */
	public String getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the keywordList
	 */
	public List<TemplateKeywordResult> getKeywordList() {
		return keywordList;
	}

	/**
	 * @param keywordList the keywordList to set
	 */
	public void setKeywordList(List<TemplateKeywordResult> keywordList) {
		this.keywordList = keywordList;
	}
}
