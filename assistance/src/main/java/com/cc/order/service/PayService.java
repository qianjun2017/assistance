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
public interface PayService {

	/**
	 * 发起支付
	 * @param payBean
	 * @return
	 */
	Object pay(PayBean payBean);
	
	/**
	 * 支付退款
	 * @param refundBean
	 */
	void refund(RefundBean refundBean);
	
	/**
	 * 关闭支付
	 * @param payBean
	 */
	void close(PayBean payBean);
	
	/**
	 * 查询支付
	 * @param payBean
	 */
	void queryPay(PayBean payBean);
	
	/**
	 * 查询退款
	 * @param refundBean
	 */
	void queryRefund(RefundBean refundBean);
}
