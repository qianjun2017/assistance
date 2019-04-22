/**
 * 
 */
package com.cc.map.http.response;

import java.util.List;

import com.cc.map.http.response.model.SuggestionPOI;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class SuggestionResponse {

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
	 * 提示POI列表
	 */
	@JsonProperty(value="result")
	private List<SuggestionPOI> poiList;

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
	 * @return the poiList
	 */
	public List<SuggestionPOI> getPoiList() {
		return poiList;
	}

	/**
	 * @param poiList the poiList to set
	 */
	public void setPoiList(List<SuggestionPOI> poiList) {
		this.poiList = poiList;
	}
}
