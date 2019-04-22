/**
 * 
 */
package com.cc.film.bean;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_film_evaluate")
public class FilmEvaluateBean extends BaseOrm<FilmEvaluateBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6137755379029030292L;
	@Id
	private Long id;
	/**
	 * 推荐
	 */
	private Boolean recommend;
	
	/**
	 * 美女数
	 */
	private Integer belle;
	
	/**
	 * 次数
	 */
	private Integer frequency;
	
	/**
	 * 画质
	 */
	private Integer quality;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

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
	 * @return the recommend
	 */
	public Boolean getRecommend() {
		return recommend;
	}

	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}

	/**
	 * @return the belle
	 */
	public Integer getBelle() {
		return belle;
	}

	/**
	 * @param belle the belle to set
	 */
	public void setBelle(Integer belle) {
		this.belle = belle;
	}

	/**
	 * @return the frequency
	 */
	public Integer getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the quality
	 */
	public Integer getQuality() {
		return quality;
	}

	/**
	 * @param quality the quality to set
	 */
	public void setQuality(Integer quality) {
		this.quality = quality;
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
}
