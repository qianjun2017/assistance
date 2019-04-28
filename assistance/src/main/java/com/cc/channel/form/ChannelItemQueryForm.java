/**
 * 
 */
package com.cc.channel.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class ChannelItemQueryForm extends QueryForm {

	/**
	 * 频道
	 */
	private Long channelId;
	
	/**
	 * 频道编码
	 */
	private String channelCode;
	
	/**
	 * 关键字
	 */
	private String keywords;

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
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
}
