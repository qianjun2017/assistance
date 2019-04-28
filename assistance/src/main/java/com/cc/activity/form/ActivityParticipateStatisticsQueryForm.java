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
public class ActivityParticipateStatisticsQueryForm extends QueryForm {

	/**
	 * 活动
	 */
	private Long activityId;
	
	/**
	 * 活动名称
	 */
	private String activityName;
	
	/**
	 * 会员
	 */
	private String leaguerName;
	
	/**
	 * 活动省市
	 */
	private String province;
	
	/**
	 * 活动市区
	 */
	private String city;
	
	/**
	 * 活动国家
	 */
	private String country;
	
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
	 * @return the activityId
	 */
	public Long getActivityId() {
		return activityId;
	}

	/**
	 * @return the activityName
	 */
	public String getActivityName() {
		return activityName;
	}

	/**
	 * @return the leaguerName
	 */
	public String getLeaguerName() {
		return leaguerName;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
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
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	/**
	 * @param activityName the activityName to set
	 */
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	/**
	 * @param leaguerName the leaguerName to set
	 */
	public void setLeaguerName(String leaguerName) {
		this.leaguerName = leaguerName;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

}
