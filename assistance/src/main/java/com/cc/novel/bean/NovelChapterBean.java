/**
 * 
 */
package com.cc.novel.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_novel_chapter")
public class NovelChapterBean extends BaseOrm<NovelChapterBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7974826409853622183L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 小说章节名称
	 */
	private String name;
	
	/**
	 * 抓取爬虫
	 */
	private String spiderNo;
	
	/**
	 * 目标网站的主键
	 */
	private String itemId;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/**
	 * 积分
	 */
	private Long integration;

	/**
	 * 小说
	 */
	private Long novelId;
	
	/**
	 * 上一章节
	 */
	private Long preId;
	
	/**
	 * 下一章节
	 */
	private Long nextId;

	/**
	 * 章节顺序
	 */
	private Long orderNo;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
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
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}

	/**
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
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
	 * @return the integration
	 */
	public Long getIntegration() {
		return integration;
	}

	/**
	 * @param integration the integration to set
	 */
	public void setIntegration(Long integration) {
		this.integration = integration;
	}

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
	 * @return the preId
	 */
	public Long getPreId() {
		return preId;
	}

	/**
	 * @param preId the preId to set
	 */
	public void setPreId(Long preId) {
		this.preId = preId;
	}

	/**
	 * @return the nextId
	 */
	public Long getNextId() {
		return nextId;
	}

	/**
	 * @param nextId the nextId to set
	 */
	public void setNextId(Long nextId) {
		this.nextId = nextId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}
}
