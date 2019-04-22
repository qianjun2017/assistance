/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.common.exception.LogicException;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.event.Event;

/**
 * 注册事件
 * @author Administrator
 *
 */
public class RegisterEvent extends Event {

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

	@Override
	public String getHappenedComment() {
		return "注册送积分";
	}

	@Override
	public String getRollBackComment() {
		throw new LogicException("E001", "注册送积分不支持撤销扣减");
	}

}
