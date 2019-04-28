package com.cc.order.service;

import com.cc.common.web.Page;
import com.cc.order.form.CartGoodsForm;
import com.cc.order.form.OrderForm;
import com.cc.order.form.OrderQueryForm;
import com.cc.order.result.OrderListResult;
import com.cc.order.result.PreOrderResult;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
public interface OrderService {

    /**
     * 保存订单
     * @param orderForm
     */
	Long saveOrder(OrderForm orderForm);

    /**
     * 取消订单
     * @param id
     */
    void cancelOrder(Long id);
    
    /**
     * 取消子订单
     * @param orderSubId
     */
    void cancelSubOrder(Long orderSubId);

    /**
     * 预下单
     * @param cartGoodsFormList
     * @return
     */
    PreOrderResult preOrder(List<CartGoodsForm> cartGoodsFormList);
    
    /**
     * 订单发货
     * @param orderSubId 子订单号
     */
    void deliverySubOrder(Long orderSubId);
    
    /**
     * 订单收货
     * @param orderSubId
     */
    void receiveSubOrder(Long orderSubId);
    
    /**
     * 子订单退货
     * @param orderSubId
     */
    void backSubOrder(Long orderSubId);
    
    /**
     * 支付订单
     * @param id
     * @return
     */
    Object payOrder(Long id);

	/**
	 * 分页查询订单
	 * @param form
	 * @return
	 */
	Page<OrderListResult> queryOrderPage(OrderQueryForm form);
	
	/**
     * 支付子订单
     * @param subId
     * @return
     */
    void paySubOrder(Long subId);
    
    /**
     * 子订单备货
     * @param orderSubId
     */
    void readySubOrder(Long orderSubId);
    
    /**
     * 子订单送达
     * @param orderSubId
     */
    void arriveSubOrder(Long orderSubId);
    
    /**
     * 子订单退款
     * @param orderSubId
     */
    void refundSubOrder(Long orderSubId);
}
