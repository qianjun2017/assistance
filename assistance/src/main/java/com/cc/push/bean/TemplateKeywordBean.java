/**
 * 
 */
package com.cc.push.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_template_keyword")
public class TemplateKeywordBean extends BaseOrm<TemplateKeywordBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1806683671610866065L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 模板
	 */
	private Long templateId;
	
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
