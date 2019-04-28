/**
 * 
 */
package com.cc.novel.form;

import com.cc.common.form.QueryForm;

/**
 * @author Administrator
 *
 */
public class NovelExchangeQueryForm extends QueryForm {

	/**
	 * 会员
	 */
	private Long leaguerId;
	
	/**
	 * 小说
	 */
	private Long novelId;

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}
	
	/**
	 * @return the novelId
	 */
	public Long getNovelId() {
		return novelId;
	}

	/**
	 * @param novelId the novelId to set
	 */
	public void setNovelId(Long novelId) {
		this.novelId = novelId;
	}

}
