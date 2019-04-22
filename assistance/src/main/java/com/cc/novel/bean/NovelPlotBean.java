/**
 * 
 */
package com.cc.novel.bean;

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
@Table(name="t_novel_plot")
public class NovelPlotBean extends BaseOrm<NovelPlotBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2533253168870015744L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 简介
	 */
	private byte[] plot;
	
	/**
	 * 小说
	 */
	private Long novelId;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the plot
	 */
	public byte[] getPlot() {
		return plot;
	}
	/**
	 * @param plot the plot to set
	 */
	public void setPlot(byte[] plot) {
		this.plot = plot;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
