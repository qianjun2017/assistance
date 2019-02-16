/**
 * 
 */
package com.cc.carousel.bean;

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
@Table(name="t_carousel")
public class CarouselBean extends BaseOrm<CarouselBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6106075986549313070L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 轮播图片
	 */
	private String imageUrl;
	
	/**
	 * 跳转路径
	 */
	private String path;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 轮播图名称
	 */
	private String name;
	
	/**
	 * 点击次数
	 */
	private Integer clicked;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the clicked
	 */
	public Integer getClicked() {
		return clicked;
	}

	/**
	 * @param clicked the clicked to set
	 */
	public void setClicked(Integer clicked) {
		this.clicked = clicked;
	}

}
