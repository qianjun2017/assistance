/**
 * 
 */
package com.cc.order.result;

import java.util.List;

import com.cc.order.bean.OrderSubBean;
import com.cc.order.bean.OrderSubGoodsBean;
import com.cc.order.bean.PayBean;

/**
 * @author ws_yu
 *
 */
public class OrderSubResult {
	
	/**
	 * 子订单信息
	 */
	private OrderSubBean orderSub;
	
	/**
	 * 子订单商品列表
	 */
	private List<OrderSubGoodsBean> orderSubGoodsList;
	
	/**
     * 子订单支付信息
     */
    private PayBean orderPay;

	/**
	 * @return the orderSub
	 */
	public OrderSubBean getOrderSub() {
		return orderSub;
	}

	/**
	 * @param orderSub the orderSub to set
	 */
	public void setOrderSub(OrderSubBean orderSub) {
		this.orderSub = orderSub;
	}

	/**
	 * @return the orderSubGoodsList
	 */
	public List<OrderSubGoodsBean> getOrderSubGoodsList() {
		return orderSubGoodsList;
	}

	/**
	 * @param orderSubGoodsList the orderSubGoodsList to set
	 */
	public void setOrderSubGoodsList(List<OrderSubGoodsBean> orderSubGoodsList) {
		this.orderSubGoodsList = orderSubGoodsList;
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
}
