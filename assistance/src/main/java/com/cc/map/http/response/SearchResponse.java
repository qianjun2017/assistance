/**
 * 
 */
package com.cc.map.http.response;

import java.util.List;

import com.cc.map.http.response.model.POI;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class SearchResponse {

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
	 * POI检索总数，开发者请求中设置了page_num字段才会出现total字段。出于数据保护目的，单次请求total最多为400
	 */
	private Integer total;
	
	/**
	 * POI列表
	 */
	@JsonProperty(value="results")
	private List<POI> poiList;
	
	/**
	 * 
	 */
	public SearchResponse() {
		this.success = Boolean.FALSE;
	}

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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the poiList
	 */
	public List<POI> getPoiList() {
		return poiList;
	}

	/**
	 * @param poiList the poiList to set
	 */
	public void setPoiList(List<POI> poiList) {
		this.poiList = poiList;
	}
}
