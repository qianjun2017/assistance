/**
 * 
 */
package com.cc.wx.http.response;

import java.util.List;

import com.cc.wx.http.response.model.TemplateLibrary;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class TemplateLibraryListResponse {
	
	/**
	 * 返回状态
	 */
	private boolean success;
	/**
	 * 返回信息
	 */
	private String message;
	
	/**
	 * 错误码
	 */
	private Integer errcode;
	
	/**
	 * 错误信息
	 */
	private String errmsg;
	
	/**
	 * 帐号下已存在的模板列表
	 */
	private List<TemplateLibrary> list;
	
	/**
	 * 模板库标题总数
	 */
	@JsonProperty(value="total_count")
	private Integer total;

	public TemplateLibraryListResponse() {
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
	 * @return the errcode
	 */
	public Integer getErrcode() {
		return errcode;
	}

	/**
	 * @param errcode the errcode to set
	 */
	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	/**
	 * @return the errmsg
	 */
	public String getErrmsg() {
		return errmsg;
	}

	/**
	 * @param errmsg the errmsg to set
	 */
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @return the list
	 */
	public List<TemplateLibrary> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<TemplateLibrary> list) {
		this.list = list;
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

}
