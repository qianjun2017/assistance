/**
 * 
 */
package com.cc.wx.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class Template {

	/**
	 * 模板id，发送小程序模板消息时所需
	 */
	@JsonProperty(value="template_id")
	private String id;
	
	/**
	 * 模板标题
	 */
	private String title;
	
	/**
	 * 模板内容
	 */
	private String content;
	
	/**
	 * 模板内容示例
	 */
	private String example;

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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the example
	 */
	public String getExample() {
		return example;
	}

	/**
	 * @param example the example to set
	 */
	public void setExample(String example) {
		this.example = example;
	}
}
