/**
 * 
 */
package com.cc.film.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * 演员
 * @author Administrator
 *
 */
@Table(name="t_actor")
public class ActorBean extends BaseOrm<ActorBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8230727422784679608L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 演员名称
	 */
	private String actorName;
	
	/**
	 * 演员介绍
	 */
	private byte[] actorDesc;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the actorName
	 */
	public String getActorName() {
		return actorName;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param actorName the actorName to set
	 */
	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	/**
	 * @return the actorDesc
	 */
	public byte[] getActorDesc() {
		return actorDesc;
	}

	/**
	 * @param actorDesc the actorDesc to set
	 */
	public void setActorDesc(byte[] actorDesc) {
		this.actorDesc = actorDesc;
	}

}
