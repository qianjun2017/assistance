/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.common.tools.StringTools;
import com.cc.integration.event.Event;

/**
 * @author Administrator
 *
 */
public class DepositEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		setIntegration(Long.valueOf(StringTools.toString(getData())));
		setGradeIntegration(0L);
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		return "充值";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		return "充值-撤销";
	}

}
