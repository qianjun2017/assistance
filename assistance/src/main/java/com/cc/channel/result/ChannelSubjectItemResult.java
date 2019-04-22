/**
 * 
 */
package com.cc.channel.result;

/**
 * @author Administrator
 *
 */
public class ChannelSubjectItemResult {

	private Long id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 链接
	 */
	private String path;
	
	/**
	 * 主图
	 */
	private String imgUrl;
	
	/**
	 * 内容主键
	 */
	private Long itemId;

	/**
	 * 专题描述文本
	 */
	private String text;
	
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
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(byte[] text) {
		this.text = new String(text);
	}

}
