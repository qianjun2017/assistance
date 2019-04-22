/**
 * 
 */
package com.cc.channel.bean;

import java.util.Date;

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
@Table(name="t_channel_subject")
public class ChannelSubjectBean extends BaseOrm<ChannelSubjectBean> implements BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 710135706080926880L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 频道名称
	 */
	private String subjectName;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/**
	 * 频道状态
	 */
	private String status;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 是否单行展示
	 */
	private Boolean single;
	
	/**
	 * 是否展示文本
	 */
	private Boolean text;
	
	/**
	 * 封面
	 */
	private String cover;
	
	/**
	 * 每行展示个数
	 */
	private Integer number;
	
	/**
	 * 封面高度
	 */
	private Long height;
	
	/**
	 * 展示规格
	 */
	private String scale;

	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
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
	 * @return the single
	 */
	public Boolean getSingle() {
		return single;
	}

	/**
	 * @param single the single to set
	 */
	public void setSingle(Boolean single) {
		this.single = single;
	}

	/**
	 * @return the text
	 */
	public Boolean getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(Boolean text) {
		this.text = text;
	}

	/**
	 * @return the cover
	 */
	public String getCover() {
		return cover;
	}

	/**
	 * @param cover the cover to set
	 */
	public void setCover(String cover) {
		this.cover = cover;
	}

	/**
	 * @return the number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @return the height
	 */
	public Long getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(Long height) {
		this.height = height;
	}

	/**
	 * @return the scale
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

}
