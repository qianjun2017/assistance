package com.cc.push.form;

import java.util.List;
import java.util.Map;

public class PushForm {

	/**
	 * 发送模板
	 */
	private Long templateId;
	
	/**
	 * 跳转详情页面
	 */
	private Long pageId;
	
	/**
	 * 关键字
	 */
	private Map<String, String> keywords;
	
	/**
	 * 放大的关键词
	 */
	private String emphasisKeyword;
	
	/**
	 * 详情页面参数
	 */
	private String param;
	
	/**
	 * 接收用户
	 */
	private List<Long> userList;

	/**
	 * @return the templateId
	 */
	public Long getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId the templateId to set
	 */
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the pageId
	 */
	public Long getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	/**
	 * @return the keywords
	 */
	public Map<String, String> getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(Map<String, String> keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return the emphasisKeyword
	 */
	public String getEmphasisKeyword() {
		return emphasisKeyword;
	}

	/**
	 * @param emphasisKeyword the emphasisKeyword to set
	 */
	public void setEmphasisKeyword(String emphasisKeyword) {
		this.emphasisKeyword = emphasisKeyword;
	}

	/**
	 * @return the param
	 */
	public String getParam() {
		return param;
	}

	/**
	 * @param param the param to set
	 */
	public void setParam(String param) {
		this.param = param;
	}

	/**
	 * @return the userList
	 */
	public List<Long> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<Long> userList) {
		this.userList = userList;
	}
}
