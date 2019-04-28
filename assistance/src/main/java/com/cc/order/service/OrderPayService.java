/**
 * 
 */
package com.cc.order.service;

import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;

/**
 * @author ws_yu
 *
 */
public interface OrderPayService {


    /**
     * 通知支付结果
     * @param payBean
     */
    void notifyPay(PayBean payBean);
    
    /**
     * 通知退款结果
     * @param refundBean
     */
    void notifyRefund(RefundBean refundBean);
    
    /**
     * 查询订单的支付信息
     * @param orderId
     * @return
     */
    PayBean queryPayBeanByOrderId(Long orderId);
    
    /**
     * 查询订单的支付信息
     * @param orderId
     * @param orderSubId
     * @return
     */
    PayBean queryPayBeanByOrderSubId(Long orderId, Long orderSubId);
    
    /**
     * 查询支付信息
     * @param code
     * @return
     */
    PayBean queryPayBeanByCode(String code);
    
    /**
     * 查询退款信息
     * @param code
     * @return
     */
    RefundBean queryRefundBeanByCode(String code);
}
