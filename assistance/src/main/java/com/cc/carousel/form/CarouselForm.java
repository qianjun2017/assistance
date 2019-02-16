/**
 * 
 */
package com.cc.carousel.form;

import java.util.List;

/**
 * @author Administrator
 *
 */
public class CarouselForm {

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
	 * 轮播图名称
	 */
	private String name;
	
	/**
	 * 频道
	 */
	List<Integer> channelList;

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
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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
	 * @return the channelList
	 */
	public List<Integer> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(List<Integer> channelList) {
		this.channelList = channelList;
	}
}
