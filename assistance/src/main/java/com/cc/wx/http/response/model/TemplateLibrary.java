/**
 * 
 */
package com.cc.wx.http.response.model;

/**
 * @author ws_yu
 *
 */
public class TemplateLibrary {

	/**
	 * 模板标题id（获取模板标题下的关键词库时需要）
	 */
	private String id;
	
	/**
	 * 模板标题内容
	 */
	private String title;

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
}
