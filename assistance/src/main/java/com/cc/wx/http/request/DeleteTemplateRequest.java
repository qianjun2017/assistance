/**
 * 
 */
package com.cc.wx.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class DeleteTemplateRequest {

	/**
	 * 小程序调用凭证
	 */
	@JsonIgnore
	private String accessToken;
	
	/**
	 * 要删除的模板id
	 */
	@JsonProperty(value="template_id")
	private String templateId;
	
	@JsonIgnore
	private String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/del";

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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
