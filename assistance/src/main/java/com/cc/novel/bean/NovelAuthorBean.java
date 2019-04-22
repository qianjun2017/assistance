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
@Table(name="t_novel_author")
public class NovelAuthorBean extends BaseOrm<NovelAuthorBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6427451970537235189L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 作者
	 */
	private Long authorId;
	
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
	 * @return the authorId
	 */
	public Long getAuthorId() {
		return authorId;
	}
	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
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
