/**
 * 
 */
package com.cc.novel.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class NovelChapterQueryForm extends QueryForm {

	/**
	 * 小说
	 */
	private Long novelId;

	/**
	 * 章节名称
	 */
	private String name;
	
	/**
	 * @return the novelId
	 */
	public Long getNovelId() {
		return novelId;
	}

	/**
	 * @param novelId the novelId to set
	 */
	public void setNovelId(Long novelId) {
		this.novelId = novelId;
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
}
