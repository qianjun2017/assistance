/**
 * 
 */
package com.cc.leaguer.bean;

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
@Table(name="t_leaguer_channel")
public class LeaguerChannelBean extends BaseOrm<LeaguerChannelBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2500867131651218048L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
