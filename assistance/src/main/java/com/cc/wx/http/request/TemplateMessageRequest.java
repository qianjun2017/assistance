/**
 * 
 */
package com.cc.wx.http.request;

import java.util.Map;

import com.cc.wx.http.request.model.Value;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class TemplateMessageRequest {

	/**
	 * 小程序调用凭证
	 */
	@JsonIgnore
	private String accessToken;
	
	/**
	 * 接收者（用户）的 openid
	 */
	@JsonProperty(value="touser")
	private String toUser;
	
	/**
	 * 所需下发的模板消息的id
	 */
	@JsonProperty(value="template_id")
	private String templateId;
	
	/**
	 * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
	 */
	private String page;
	
	/**
	 * 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
	 */
	@JsonProperty(value="form_id")
	private String formId;
	
	/**
	 * 模板内容，不填则下发空模板
	 */
	private Map<String, Value> data;
	
	/**
	 * 模板需要放大的关键词，不填则默认无放大
	 */
	@JsonProperty(value="emphasis_keyword")
	private String emphasisKeyword;
	
	@JsonIgnore
	private String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the toUser
	 */
	public String getToUser() {
		return toUser;
	}

	/**
	 * @param toUser the toUser to set
	 */
	public void setToUser(String toUser) {
		this.toUser = toUser;
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
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the formId
	 */
	public String getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(String formId) {
		this.formId = formId;
	}

	/**
	 * @return the data
	 */
	public Map<String, Value> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Map<String, Value> data) {
		this.data = data;
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
