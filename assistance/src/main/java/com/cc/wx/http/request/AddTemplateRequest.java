/**
 * 
 */
package com.cc.wx.http.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class AddTemplateRequest {

	/**
	 * 小程序调用凭证
	 */
	@JsonIgnore
	private String accessToken;
	
	/**
	 * 模板标题id，可通过接口获取，也可登录小程序后台查看获取
	 */
	private String id;
	
	/**
	 * 开发者自行组合好的模板关键词列表，关键词顺序可以自由搭配（例如[3,5,4]或[4,5,3]），最多支持10个关键词组合
	 */
	@JsonProperty(value="keyword_id_list")
	private Long[] keywordIdList;
	
	@JsonIgnore
	private String url = "https://api.weixin.qq.com/cgi-bin/wxopen/template/add";

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
	 * @return the keywordIdList
	 */
	public Long[] getKeywordIdList() {
		return keywordIdList;
	}

	/**
	 * @param keywordIdList the keywordIdList to set
	 */
	public void setKeywordIdList(Long[] keywordIdList) {
		this.keywordIdList = keywordIdList;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
}
