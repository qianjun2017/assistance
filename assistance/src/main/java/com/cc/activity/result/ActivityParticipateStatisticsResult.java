/**
 * 
 */
package com.cc.activity.result;

/**
 * @author Administrator
 *
 */
public class ActivityParticipateStatisticsResult {
	
	/**
	 * 活动
	 */
	private Long activityId;
	
	/**
	 * 活动
	 */
	private String activityName;
	
	/**
	 * 活动类型
	 */
	private String type;
	
	/**
	 * 参与总数
	 */
	private Integer total;
	
	/**
	 * 成功
	 */
	private Integer success;
	
	/**
	 * 未审核
	 */
	private Integer unconfirmed;
	
	/**
	 * 待定
	 */
	private Integer pending;
	
	/**
	 * 拒绝数
	 */
	private Integer reject;
	
	/**
	 * 欺诈数
	 */
	private Integer fake;

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @return the reject
	 */
	public Integer getReject() {
		return reject;
	}

	/**
	 * @return the fake
	 */
	public Integer getFake() {
		return fake;
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
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @param reject the reject to set
	 */
	public void setReject(Integer reject) {
		this.reject = reject;
	}

	/**
	 * @param fake the fake to set
	 */
	public void setFake(Integer fake) {
		this.fake = fake;
	}

	/**
	 * @return the pending
	 */
	public Integer getPending() {
		return pending;
	}

	/**
	 * @param pending the pending to set
	 */
	public void setPending(Integer pending) {
		this.pending = pending;
	}

	/**
	 * @return the success
	 */
	public Integer getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Integer success) {
		this.success = success;
	}

	/**
	 * @return the unconfirmed
	 */
	public Integer getUnconfirmed() {
		return unconfirmed;
	}

	/**
	 * @param unconfirmed the unconfirmed to set
	 */
	public void setUnconfirmed(Integer unconfirmed) {
		this.unconfirmed = unconfirmed;
	}
	
}
