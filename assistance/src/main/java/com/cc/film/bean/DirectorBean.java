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
 * 导演
 * @author Administrator
 *
 */
@Table(name="t_director")
public class DirectorBean extends BaseOrm<DirectorBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8419220532437234044L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 导演名称
	 */
	private String directorName;
	
	/**
	 * 导演描述
	 */
	private byte[] directorDesc;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the directorName
	 */
	public String getDirectorName() {
		return directorName;
	}
	/**
	 * @return the directorDesc
	 */
	public byte[] getDirectorDesc() {
		return directorDesc;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param directorName the directorName to set
	 */
	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}
	/**
	 * @param directorDesc the directorDesc to set
	 */
	public void setDirectorDesc(byte[] directorDesc) {
		this.directorDesc = directorDesc;
	}

}
