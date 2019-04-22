/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.integration.event.Event;
import com.cc.order.bean.OrderBean;

/**
 * @author Administrator
 *
 */
public class OrderEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		OrderBean orderBean = (OrderBean) getData();
		long integration = Math.floorDiv(orderBean.getTotal(), 10);
		setIntegration(integration);
		setGradeIntegration(integration);
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		OrderBean orderBean = (OrderBean) getData();
		return "支付订单["+orderBean.getCode()+"]";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		OrderBean orderBean = (OrderBean) getData();
		return "取消订单["+orderBean.getCode()+"]";
	}

}
