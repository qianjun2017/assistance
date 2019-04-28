/**
 * 
 */
package com.cc.activity.form;

import java.util.Date;

import com.cc.common.web.QueryForm;

/**
 * @author Administrator
 *
 */
public class ActivityParticipateQueryForm extends QueryForm {

	/**
	 * 活动名称
	 */
	private String name;
	
	/**
	 * 会员
	 */
	private String leaguerName;
	
	/**
	 * 活动参与状态
	 */
	private String status;
	
	/**
	 * 活动类型
	 */
	private String type;

	/**
	 * 开始时间
	 */
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	private Date createTimeEnd;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * @return the leaguerName
	 */
	public String getLeaguerName() {
		return leaguerName;
	}

	/**
	 * @param leaguerName the leaguerName to set
	 */
	public void setLeaguerName(String leaguerName) {
		this.leaguerName = leaguerName;
	}
}
