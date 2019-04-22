/**
 * 
 */
package com.cc.map.http.response;

import com.cc.map.http.response.model.GeocoderInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class GeocoderResponse {

	/**
	 * 返回状态
	 */
	private boolean success;
	
	/**
	 * 对API访问状态值的英文说明，如果成功返回"ok"，并返回结果字段，如果失败返回错误说明
	 */
	private String message;
	
	/**
	 * 本次API访问状态，如果成功返回0，如果失败返回其他数字
	 */
	private Integer status;
	
	/**
	 * 地理信息
	 */
	@JsonProperty(value="result")
	private GeocoderInfo geocoderInfo;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the geocoderInfo
	 */
	public GeocoderInfo getGeocoderInfo() {
		return geocoderInfo;
	}

	/**
	 * @param geocoderInfo the geocoderInfo to set
	 */
	public void setGeocoderInfo(GeocoderInfo geocoderInfo) {
		this.geocoderInfo = geocoderInfo;
	}
}
