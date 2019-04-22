/**
 * 
 */
package com.cc.carousel.form;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class CarouselQueryForm extends QueryForm {

	
	/**
	 * 轮播图名称
	 */
	private String name;
	
	/**
	 * 轮播图状态
	 */
	private String status;
	
	/**
	 * 频道
	 */
	private String channelCode;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
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
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
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

}
