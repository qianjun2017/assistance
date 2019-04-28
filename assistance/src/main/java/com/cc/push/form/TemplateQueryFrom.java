/**
 * 
 */
package com.cc.push.form;

import com.cc.common.form.QueryForm;

/**
 * @author ws_yu
 *
 */
public class TemplateQueryFrom extends QueryForm {
	
	/**
	 * 模板Id
	 */
	private String templateId;
	
	/**
	 * 模板标题
	 */
	private String title;
	
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
}
