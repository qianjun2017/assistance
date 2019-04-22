/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.common.exception.LogicException;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.event.Event;

/**
 * @author Administrator
 *
 */
public class CheckinEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		IntegrationEventBean integrationEventBean = getIntegrationEventBean();
		Float integration = integrationEventBean.getIntegration();
		String valueOf = String.valueOf(integration);
		int index = valueOf.indexOf(".");
		if (index==-1) {
			index = valueOf.length() - 1;
		}
		long parseLong = Long.parseLong(valueOf.substring(0, index));
		setIntegration(parseLong);
		setGradeIntegration(0l);
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		return "签到送积分";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		throw new LogicException("E001", "签到送积分不支持撤销扣减");
	}

}
