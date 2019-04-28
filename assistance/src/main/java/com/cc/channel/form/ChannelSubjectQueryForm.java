/**
 * 
 */
package com.cc.channel.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class ChannelSubjectQueryForm extends QueryForm {

	/**
	 * 专题名称
	 */
	private String subjectName;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/**
	 * 频道编码
	 */
	private String channelCode;
	
	/**
	 * 专题状态
	 */
	private String status;
	
	/**
	 * @return the subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * @param subjectName the subjectName to set
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
}
