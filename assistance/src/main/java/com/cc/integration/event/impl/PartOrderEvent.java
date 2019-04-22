/**
 * 
 */
package com.cc.integration.event.impl;

import com.cc.integration.event.Event;
import com.cc.order.bean.OrderBean;
import com.cc.order.bean.OrderSubBean;

/**
 * @author Administrator
 *
 */
public class PartOrderEvent extends Event {

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#calcIntegration()
	 */
	@Override
	public void calcIntegration() {
		OrderSubBean orderSubBean = (OrderSubBean) getData();
		long integration = Math.floorDiv(orderSubBean.getTotal(), 10);
		setIntegration(integration);
		setGradeIntegration(integration);
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getHappenedComment()
	 */
	@Override
	public String getHappenedComment() {
		OrderSubBean orderSubBean = (OrderSubBean) getData();
		OrderBean orderBean = OrderBean.get(OrderBean.class, orderSubBean.getOrderId());
		return "支付订单["+orderBean.getCode()+"]部分商品";
	}

	/* (non-Javadoc)
	 * @see com.cc.integration.event.Event#getRollBackComment()
	 */
	@Override
	public String getRollBackComment() {
		OrderSubBean orderSubBean = (OrderSubBean) getData();
		OrderBean orderBean = OrderBean.get(OrderBean.class, orderSubBean.getOrderId());
		return "取消订单["+orderBean.getCode()+"]部分商品";
	}

}
