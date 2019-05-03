/**
 * 
 */
package com.cc.channel.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class ChannelQueryForm extends QueryForm {

	/**
	 * 频道编码
	 */
	private String channelCode;
	
	/**
	 * 频道名称
	 */
	private String channelName;
	
	/**
	 * 频道类型
	 */
	private String channelType;
	
	/**
	 * 频道状态
	 */
	private String status;
	
	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getSort() {
		if(super.getSort().equals("createTime")){
			return "status";
		}else{
			return super.getSort();
		}
	}
}
