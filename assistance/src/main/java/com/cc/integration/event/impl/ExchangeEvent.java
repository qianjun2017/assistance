/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.exchange.bean.ExchangeBean;
import com.cc.integration.event.Event;

/**
 * @author Administrator
 *
 */
public class ExchangeEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		ExchangeBean exchangeBean = (ExchangeBean) getData();
		setIntegration(exchangeBean.getIntegration()*(-1l));
		setGradeIntegration(exchangeBean.getIntegration());
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		ExchangeBean exchangeBean = (ExchangeBean) getData();
		return "兑换["+exchangeBean.getItemName()+"]";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		ExchangeBean exchangeBean = (ExchangeBean) getData();
		return "取消兑换["+exchangeBean.getItemName()+"]";
	}

}
