/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.activity.bean.ActivityBean;
import com.cc.integration.event.Event;

/**
 * @author Administrator
 *
 */
public class ActivityEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		ActivityBean activityBean = (ActivityBean) getData();
		setIntegration(activityBean.getIntegration());
		setGradeIntegration(activityBean.getIntegration());
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		ActivityBean activityBean = (ActivityBean) getData();
		return "参与活动["+activityBean.getName()+"]";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		ActivityBean activityBean = (ActivityBean) getData();
		return "欺诈参与活动["+activityBean.getName()+"]";
	}

}
