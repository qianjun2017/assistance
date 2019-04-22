/**
 * 
 */
package com.cc.wx.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class TemplateKeyword {

	/**
	 * 关键词 id，添加模板时需要
	 */
	@JsonProperty(value="keyword_id")
	private Long id;
	
	/**
	 * 关键词内容
	 */
	private String name;
	
	/**
	 * 关键词内容对应的示例
	 */
	private String example;

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
