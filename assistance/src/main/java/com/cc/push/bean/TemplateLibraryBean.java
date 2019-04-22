/**
 * 
 */
package com.cc.push.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author ws_yu
 *
 */
@Table(name="t_template_library")
public class TemplateLibraryBean extends BaseOrm<TemplateLibraryBean> implements BaseEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5845421796404754659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 模板标题id（获取模板标题下的关键词库时需要）
	 */
	private String templateId;
	
	/**
	 * 模板标题内容
	 */
	private String title;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 同步状态
	 */
	private Boolean sync;

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

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the sync
	 */
	public Boolean getSync() {
		return sync;
	}

	/**
	 * @param sync the sync to set
	 */
	public void setSync(Boolean sync) {
		this.sync = sync;
	}
}
