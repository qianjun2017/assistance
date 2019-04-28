/**
 * 
 */
package com.cc.order.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.tools.ListTools;
import com.cc.order.bean.OrderBean;
import com.cc.order.bean.OrderSubBean;
import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;
import com.cc.order.enums.OrderStatusEnum;
import com.cc.order.enums.PayStatusEnum;
import com.cc.order.service.OrderPayService;

/**
 * @author ws_yu
 *
 */
@Service
public class OrderPayServiceImpl implements OrderPayService {

	@Override
	@Transactional
	public void notifyPay(PayBean payBean) {
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		if(PayStatusEnum.PAYING.equals(payStatusEnum) || PayStatusEnum.PAY.equals(payStatusEnum)){
			return;
		}
		OrderBean orderBean = OrderBean.get(OrderBean.class, payBean.getOrderId());
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderBean.getStatus());
		if(!(OrderStatusEnum.TOBEPAY.equals(orderStatusEnum) || OrderStatusEnum.TRADE.equals(orderStatusEnum))){
			return;
		}
		switch (payStatusEnum) {
			case PAYED:
				if(payBean.getOrderSubId()==null){
					orderBean.setStatus(OrderStatusEnum.TOBESHIPPED.getCode());
					orderBean.setReceipts(payBean.getReceipts());
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", payBean.getOrderId());
					for(OrderSubBean orderSubBean: orderSubBeanList){
						if(!OrderStatusEnum.TOBEPAY.equals(OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus()))){
							return;
						}
						orderSubBean.setStatus(OrderStatusEnum.TOBESHIPPED.getCode());
						orderSubBean.setReceipts(orderSubBean.getReceivable());
						orderSubBean.update();
					}
				}else{
					OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, payBean.getOrderSubId());
					orderSubBean.setStatus(OrderStatusEnum.COMPLETED.getCode());
					orderSubBean.setReceipts(payBean.getReceipts());
					orderSubBean.update();
					orderBean.setReceipts(orderBean.getReceipts()+payBean.getReceipts());
					Boolean over = Boolean.TRUE;
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", payBean.getOrderId());
					for(OrderSubBean subBean: orderSubBeanList){
						OrderStatusEnum orderSubStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(subBean.getStatus());
						if(!(OrderStatusEnum.CLOSED.equals(orderSubStatusEnum) || OrderStatusEnum.COMPLETED.equals(orderSubStatusEnum))){
							over = Boolean.FALSE;
							break;
						}
					}
					if(over){
						orderBean.setStatus(OrderStatusEnum.OVER.getCode());
					}
				}
				break;
				
			case NOPAY:
				orderBean.setStatus(OrderStatusEnum.OVER.getCode());
				if(payBean.getOrderSubId()==null){
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", payBean.getOrderId());
					for(OrderSubBean orderSubBean: orderSubBeanList){
						if(!OrderStatusEnum.TOBEPAID.equals(OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus()))){
							return;
						}
						orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
						orderSubBean.update();
					}
				}else{
					OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, payBean.getOrderSubId());
					orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
					orderSubBean.update();
				}
				break;
	
			default:
				break;
		}
		orderBean.update();
	}

	@Override
	@Transactional
	public void notifyRefund(RefundBean refundBean) {
		PayStatusEnum refundStatusEnum = PayStatusEnum.getPayStatusEnumByCode(refundBean.getStatus());
		if(PayStatusEnum.FUNDING.equals(refundStatusEnum) || PayStatusEnum.FUND.equals(refundStatusEnum)){
			return;
		}
		OrderBean orderBean = OrderBean.get(OrderBean.class, refundBean.getOrderId());
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderBean.getStatus());
		if(!OrderStatusEnum.TRADE.equals(orderStatusEnum)){
			return;
		}
		switch (refundStatusEnum) {
			case FUNDED:
				if(refundBean.getOrderSubId()==null){
					orderBean.setStatus(OrderStatusEnum.OVER.getCode());
					orderBean.setRefund(refundBean.getRefund());
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", refundBean.getOrderId());
					for(OrderSubBean orderSubBean: orderSubBeanList){
						if(!OrderStatusEnum.REFUND.equals(OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus()))){
							return;
						}
						orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
						orderSubBean.setRefund(orderSubBean.getReceivable());
						orderSubBean.update();
					}
				}else{
					OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, refundBean.getOrderSubId());
					orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
					orderSubBean.setRefund(refundBean.getRefund());
					orderSubBean.update();
					orderBean.setRefund(orderBean.getRefund()+refundBean.getRefund());
					Boolean over = Boolean.TRUE;
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", refundBean.getOrderId());
					for(OrderSubBean subBean: orderSubBeanList){
						OrderStatusEnum orderSubStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(subBean.getStatus());
						if(!(OrderStatusEnum.CLOSED.equals(orderSubStatusEnum) || OrderStatusEnum.COMPLETED.equals(orderSubStatusEnum))){
							over = Boolean.FALSE;
							break;
						}
					}
					if(over){
						orderBean.setStatus(OrderStatusEnum.OVER.getCode());
					}
				}
				break;
				
			case NOFUND:
				orderBean.setStatus(OrderStatusEnum.OVER.getCode());
				if(refundBean.getOrderSubId()==null){
					List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", refundBean.getOrderId());
					for(OrderSubBean orderSubBean: orderSubBeanList){
						if(!OrderStatusEnum.REFUND.equals(OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus()))){
							return;
						}
						orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
						orderSubBean.update();
					}
				}else{
					OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, refundBean.getOrderSubId());
					orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
					orderSubBean.update();
				}
				break;
	
			default:
				break;
		}
		orderBean.update();
	}

	@Override
	public PayBean queryPayBeanByOrderId(Long orderId) {
		List<PayBean> payBeanList = PayBean.findAllByParams(PayBean.class, "orderId", orderId);
		if(ListTools.isEmptyOrNull(payBeanList)){
			return null;
		}
		for(PayBean payBean: payBeanList){
			if(payBean.getOrderSubId() == null){
				return payBean;
			}
		}
		return null;
	}

	@Override
	public PayBean queryPayBeanByOrderSubId(Long orderId, Long orderSubId) {
		List<PayBean> payBeanList = PayBean.findAllByParams(PayBean.class, "orderId", orderId, "orderSubId", orderSubId);
		if(ListTools.isEmptyOrNull(payBeanList)){
			return null;
		}
		return payBeanList.get(0);
	}

	@Override
	public PayBean queryPayBeanByCode(String code) {
		List<PayBean> payBeanList = PayBean.findAllByParams(PayBean.class, "code", code);
		if(ListTools.isEmptyOrNull(payBeanList)){
			return null;
		}
		return payBeanList.get(0);
	}

	@Override
	public RefundBean queryRefundBeanByCode(String code) {
		List<RefundBean> refundBeanList = RefundBean.findAllByParams(RefundBean.class, "code", code);
		if(ListTools.isEmptyOrNull(refundBeanList)){
			return null;
		}
		return refundBeanList.get(0);
	}

}
