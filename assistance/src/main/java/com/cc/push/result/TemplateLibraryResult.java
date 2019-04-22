/**
 * 
 */
package com.cc.push.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class TemplateLibraryResult {

	/**
	 * 模板标题 id
	 */
	private String id;
	
	/**
	 * 模板标题
	 */
	private String title;
	
	@JsonProperty(value="keyword_list")
	private List<TemplateKeywordResult> keywordList;

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
