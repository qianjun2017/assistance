package com.cc.push.result;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemplateKeywordResult {
	
	/**
	 * 关键字
	 */
	@JsonProperty(value="keyword_id")
	private Long id;
	
	/**
	 * 关键字
	 */
	private String keyword;
	
	/**
	 * 关键字
	 */
	private String keydata;
	
	/**
	 * 关键字名称
	 */
	private String name;
	
	/**
	 * 关键字示例
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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the keydata
	 */
	public String getKeydata() {
		return keydata;
	}

	/**
	 * @param keydata the keydata to set
	 */
	public void setKeydata(String keydata) {
		this.keydata = keydata;
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
