/**
 * 
 */
package com.cc.wx.http.response;

import java.util.List;

import com.cc.wx.http.response.model.TemplateKeyword;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class TemplateLibraryResponse {

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
	 * 模板标题 id
	 */
	private String id;
	
	/**
	 * 模板标题
	 */
	private String title;
	
	/**
	 * 关键词列表
	 */
	@JsonProperty(value="keyword_list")
	private List<TemplateKeyword> keywordList;

	public TemplateLibraryResponse() {
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the keywordList
	 */
	public List<TemplateKeyword> getKeywordList() {
		return keywordList;
	}

	/**
	 * @param keywordList the keywordList to set
	 */
	public void setKeywordList(List<TemplateKeyword> keywordList) {
		this.keywordList = keywordList;
	}
}
