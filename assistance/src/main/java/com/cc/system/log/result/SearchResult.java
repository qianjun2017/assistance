/**
 * 
 */
package com.cc.system.log.result;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class SearchResult {

	/**
	 * 关键词
	 */
	private String keywords;
	
	/**
	 * 搜索次数
	 */
	private Integer cnt;
	
	/**
	 * 最后搜索时间
	 */
	private Date lastSearchTime;

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

	/**
	 * @return the cnt
	 */
	public Integer getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(Integer cnt) {
		this.cnt = cnt;
	}

	/**
	 * @return the lastSearchTime
	 */
	public Date getLastSearchTime() {
		return lastSearchTime;
	}

	/**
	 * @param lastSearchTime the lastSearchTime to set
	 */
	public void setLastSearchTime(Date lastSearchTime) {
		this.lastSearchTime = lastSearchTime;
	}
	
}
