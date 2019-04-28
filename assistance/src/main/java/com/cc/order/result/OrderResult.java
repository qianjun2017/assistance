package com.cc.order.result;

import com.cc.order.bean.OrderBean;
import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/30.
 */
public class OrderResult {

    /**
     * 订单信息
     */
    private OrderBean order;

    /**
     * 子订单列表
     */
    private List<OrderSubResult> orderSubList;
    
    /**
     * 订单支付信息
     */
    private PayBean orderPay;
    
    /**
     * 订单退款信息
     */
    private List<RefundBean> orderRefundList;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

	/**
	 * @return the orderSubList
	 */
	public List<OrderSubResult> getOrderSubList() {
		return orderSubList;
	}

	/**
	 * @param orderSubList the orderSubList to set
	 */
	public void setOrderSubList(List<OrderSubResult> orderSubList) {
		this.orderSubList = orderSubList;
	}

	/**
	 * @return the orderPay
	 */
	public PayBean getOrderPay() {
		return orderPay;
	}

	/**
	 * @param orderPay the orderPay to set
	 */
	public void setOrderPay(PayBean orderPay) {
		this.orderPay = orderPay;
	}

	/**
	 * @return the orderRefundList
	 */
	public List<RefundBean> getOrderRefundList() {
		return orderRefundList;
	}

	/**
	 * @param orderRefundList the orderRefundList to set
	 */
	public void setOrderRefundList(List<RefundBean> orderRefundList) {
		this.orderRefundList = orderRefundList;
	}

}
