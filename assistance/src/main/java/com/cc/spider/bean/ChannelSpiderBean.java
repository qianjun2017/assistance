/**
 * 
 */
package com.cc.spider.bean;

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
@Table(name="t_channel_spider")
public class ChannelSpiderBean extends BaseOrm<ChannelSpiderBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5833043967302481567L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/**
	 * 爬虫编码
	 */
	private String spiderNo;
	
	/**
	 * 爬虫类型
	 */
	private String spiderType;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return channelId;
	}
	/**
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	/**
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}
	/**
	 * @return the spiderType
	 */
	public String getSpiderType() {
		return spiderType;
	}
	/**
	 * @param spiderType the spiderType to set
	 */
	public void setSpiderType(String spiderType) {
		this.spiderType = spiderType;
	}

}
