/**
 * 
 */
package com.cc.spider.http.request;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class SpiderRequest {

	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 爬虫编码
	 */
	private String spiderNo;
	
	/**
	 * 爬虫类型
	 */
	private String spiderType;
	
	/**
	 * 爬虫地址
	 */
	private String url;
	
	/**
	 * 请求参数
	 */
	private Map<String, Object> paramMap;

	/**
	 * 
	 */
	public SpiderRequest() {
		this.paramMap = new HashMap<String, Object>();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @return the spiderNo
	 */
	public String getSpiderNo() {
		return spiderNo;
	}

	/**
	 * @return the paramMap
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @param spiderNo the spiderNo to set
	 */
	public void setSpiderNo(String spiderNo) {
		this.spiderNo = spiderNo;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 设置请求参数
	 * @param paramName
	 * @param paramValue
	 */
	public void putParam(String paramName, Object paramValue){
		this.paramMap.put(paramName, paramValue);
	}

	/**
	 * @return the spiderType
	 */
	public String getSpiderType() {
		return spiderType;
	}

	/**
	 * @param spiderType the spiderType to set
	 */
	public void setSpiderType(String spiderType) {
		this.spiderType = spiderType;
	}
}
