/**
 * 
 */
package com.cc.order.service.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.RequestContextUtil;
import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;
import com.cc.order.enums.PayMethodEnum;
import com.cc.order.enums.PayStatusEnum;
import com.cc.order.service.OrderPayService;
import com.cc.order.service.PayService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.wx.enums.RefundStateEnum;
import com.cc.wx.enums.TradeStateEnum;
import com.cc.wx.http.request.CloseOrderRequest;
import com.cc.wx.http.request.QueryOrderRequest;
import com.cc.wx.http.request.QueryRefundRequest;
import com.cc.wx.http.request.RefundRequest;
import com.cc.wx.http.request.UnifiedOrderRequest;
import com.cc.wx.http.request.model.WeiXinPayInfo;
import com.cc.wx.http.response.CloseOrderResponse;
import com.cc.wx.http.response.QueryOrderResponse;
import com.cc.wx.http.response.QueryRefundResponse;
import com.cc.wx.http.response.RefundResponse;
import com.cc.wx.http.response.UnifiedOrderResponse;
import com.cc.wx.service.WeiXinService;

/**
 * @author ws_yu
 *
 */
@Service
public class PayServiceImpl implements PayService {
	
	private final static Logger logger = LoggerFactory.getLogger(PayService.class);
	
	@Autowired
	private WeiXinService weiXinService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private OrderPayService orderPayService;

	@Override
	@Transactional
	public Object pay(PayBean payBean) {
		Object object = null;
		payBean.setCreateTime(DateTools.now());
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		if(!(PayStatusEnum.PAY.equals(payStatusEnum) || PayStatusEnum.PAYING.equals(payStatusEnum))){
			throw new LogicException("E001", "当前支付状态为【"+payStatusEnum.getName()+"】,不能支付");
		}
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(payBean.getMethod());
		switch (payMethodEnum) {
			case WECHAT:
				SystemConfigBean keySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.key");
				SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.appid");
				SystemConfigBean mchIdSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.mchId");
				if(PayStatusEnum.PAYING.equals(payStatusEnum)){
					queryPay(payBean);
					payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
					switch (payStatusEnum) {
						case PAYING:
							WeiXinPayInfo payInfo = new WeiXinPayInfo();
							payInfo.setNonceStr(StringTools.getRandomCode(20));
							payInfo.setPkg("prepay_id="+payBean.getSerial());
							payInfo.setSignType("MD5");
							payInfo.setTimeStamp(String.valueOf(DateTools.now().getTime()));
							payInfo.setAppId(appidSystemConfigBean.getPropertyValue());
							payInfo.setPaySign(weiXinService.sign(payInfo.getSignType(), keySystemConfigBean.getPropertyValue(), JsonTools.toObject(JsonTools.toJsonString(payInfo), HashMap.class)));
							object = payInfo;
							break;
							
						default:
							break;
					}
				}else{
					UnifiedOrderRequest request = new UnifiedOrderRequest();
					request.setOutTradeNo(payBean.getCode());
					request.setTotalFee(payBean.getReceivable().intValue());
					request.setKey(keySystemConfigBean.getPropertyValue());
					request.setAppid(appidSystemConfigBean.getPropertyValue());
					request.setMchId(mchIdSystemConfigBean.getPropertyValue());
					request.setTradeType("JSAPI");
					request.setOpenid("");
					SystemConfigBean notifyUrlSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.unifiedOrder.notifyUrl");
					request.setNotifyUrl(notifyUrlSystemConfigBean.getPropertyValue());
					request.setSpbillCreateIp(RequestContextUtil.getIpAddr(RequestContextUtil.httpServletRequest()));
					SystemConfigBean bodySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.body");
					request.setBody(bodySystemConfigBean.getPropertyValue());
					UnifiedOrderResponse response = weiXinService.unifiedOrder(request);
					if(response.isSuccess()){
						payBean.setStatus(PayStatusEnum.PAYING.getCode());
						payBean.setSerial(response.getPrepayId());
						WeiXinPayInfo payInfo = new WeiXinPayInfo();
						payInfo.setNonceStr(StringTools.getRandomCode(20));
						payInfo.setPkg("prepay_id="+payBean.getSerial());
						payInfo.setSignType("MD5");
						payInfo.setTimeStamp(String.valueOf(DateTools.now().getTime()));
						payInfo.setAppId(request.getAppid());
						payInfo.setPaySign(weiXinService.sign(payInfo.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(payInfo), HashMap.class)));
						object = payInfo;
					}else{
						logger.error("支付【"+payBean.getCode()+"】错误："+response.getMessage());
						throw new LogicException("E003", "微信支付错误");
					}
				}
				break;
				
			case CODPAY:
				payBean.setPayTime(DateTools.now());
				payBean.setStatus(PayStatusEnum.PAYED.getCode());
				payBean.setReceipts(payBean.getReceivable());
				object = new Object();
				break;
	
			default:
				throw new LogicException("E002", "暂不支持支付方式【"+payMethodEnum.getName()+"】,请选择其他支付方式");
		}
		payBean.update();
		orderPayService.notifyPay(payBean);
		return object;
	}

	@Override
	@Transactional
	public void refund(RefundBean refundBean) {
		PayStatusEnum refundStatusEnum = PayStatusEnum.getPayStatusEnumByCode(refundBean.getStatus());
		if(!(PayStatusEnum.FUND.equals(refundStatusEnum))){
			throw new LogicException("E001", "当前退款状态为【"+refundStatusEnum.getName()+"】,不能退款");
		}
		PayBean payBean = PayBean.get(PayBean.class, refundBean.getPayId());
		refundBean.setCreateTime(DateTools.now());
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(payBean.getMethod());
		switch (payMethodEnum) {
			case WECHAT:
				SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.appid");
				SystemConfigBean mchIdSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.mchId");
				SystemConfigBean keySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.key");
				RefundRequest request = new RefundRequest();
				request.setAppid(appidSystemConfigBean.getPropertyValue());
				request.setMchId(mchIdSystemConfigBean.getPropertyValue());
				request.setKey(keySystemConfigBean.getPropertyValue());
				request.setOutTradeNo(payBean.getCode());
				request.setTransactionId(payBean.getSerial());
				request.setOutRefundNo(refundBean.getCode());
				request.setTotalFee(payBean.getReceivable().intValue());
				request.setRefundFee(refundBean.getRefundable().intValue());
				request.setRefundDesc("取消订单");
				SystemConfigBean notifyUrlSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.refund.notifyUrl");
				request.setNotifyUrl(notifyUrlSystemConfigBean.getPropertyValue());
				RefundResponse response = weiXinService.refund(request);
				if(response.isSuccess()){
					refundBean.setStatus(PayStatusEnum.FUNDING.getCode());
					refundBean.setSerial(response.getRefundId());
				}else{
					logger.error("退款【"+refundBean.getCode()+"】错误："+response.getMessage());
					throw new LogicException("E004", "微信退款错误");
				}
				break;
				
			case CODPAY:
				refundBean.setRefund(refundBean.getRefundable());
				refundBean.setStatus(PayStatusEnum.FUNDED.getCode());
				refundBean.setRefundTime(DateTools.now());
				payBean.setRefund(payBean.getRefund()+refundBean.getRefund());
				payBean.setReceipts(payBean.getReceipts()-refundBean.getRefund());
				payBean.update();
				break;
				
			default:
				throw new LogicException("E003", "暂不支持支付方式【"+payMethodEnum.getName()+"】退款,请联系卖家");
		}
		refundBean.update();
		orderPayService.notifyRefund(refundBean);
	}

	@Override
	@Transactional
	public void close(PayBean payBean) {
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		if(!PayStatusEnum.PAYING.equals(payStatusEnum)){
			throw new LogicException("E001", "当前订单支付状态为【"+payStatusEnum.getName()+"】,不能取消支付");
		}
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(payBean.getMethod());
		switch (payMethodEnum) {
			case WECHAT:
				SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.appid");
				SystemConfigBean mchIdSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.mchId");
				SystemConfigBean keySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.key");
				CloseOrderRequest request = new CloseOrderRequest();
				request.setAppid(appidSystemConfigBean.getPropertyValue());
				request.setMchId(mchIdSystemConfigBean.getPropertyValue());
				request.setKey(keySystemConfigBean.getPropertyValue());
				request.setOutTradeNo(payBean.getCode());
				CloseOrderResponse response = weiXinService.closeOrder(request);
				if(response.isSuccess()){
					payBean.setStatus(PayStatusEnum.NOPAY.getCode());
				}else{
					logger.error("关闭【"+payBean.getCode()+"】错误："+response.getMessage());
					throw new LogicException("E003", "取消微信订单错误");
				}
				break;
				
			default:
				throw new LogicException("E002", "暂不支持支付方式【"+payMethodEnum.getName()+"】,请选择其他支付方式");
		}
		payBean.update();
		orderPayService.notifyPay(payBean);
	}

	@Override
	@Transactional
	public void queryPay(PayBean payBean) {
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		if(!PayStatusEnum.PAYING.equals(payStatusEnum)){
			throw new LogicException("E001", "当前支付状态为【"+payStatusEnum.getName()+"】,不能进行查询操作");
		}
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(payBean.getMethod());
		switch (payMethodEnum) {
			case WECHAT:
				SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.appid");
				SystemConfigBean mchIdSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.mchId");
				SystemConfigBean keySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.key");
				QueryOrderRequest request = new QueryOrderRequest();
				request.setAppid(appidSystemConfigBean.getPropertyValue());
				request.setMchId(mchIdSystemConfigBean.getPropertyValue());
				request.setTransactionId(payBean.getSerial());
				request.setOutTradeNo(payBean.getCode());
				request.setKey(keySystemConfigBean.getPropertyValue());
				QueryOrderResponse response = weiXinService.queryOrder(request);
				if(response.isSuccess()){
					TradeStateEnum tradeStateEnum = TradeStateEnum.getTradeStateEnumByCode(response.getTradeState());
					if(tradeStateEnum==null){
						logger.error("查询【"+payBean.getCode()+"】错误：未知交易状态码【"+response.getTradeState()+"】,描述【"+response.getTradeStateDesc()+"】");
						throw new LogicException("E004", "查询微信订单错误");
					}
					switch (tradeStateEnum) {
						case SUCCESS:
							payBean.setStatus(PayStatusEnum.PAYED.getCode());
							payBean.setReceipts(response.getCashFee().longValue());
							payBean.setPayTime(DateTools.now());
							break;
							
						case CLOSED:
						case REVOKED:
						case PAYERROR:
							payBean.setStatus(PayStatusEnum.NOPAY.getCode());
							payBean.setPayTime(DateTools.now());
							break;
							
						case REFUND:
							logger.warn("查询【"+payBean.getCode()+"】警告：交易状态码【"+response.getTradeState()+"】,交易状态名称【"+tradeStateEnum.getName()+"】,描述【"+response.getTradeStateDesc()+"】");
							break;
							
						case NOTPAY:
						case USERPAYING:
							break;
	
						default:
							logger.error("查询【"+payBean.getCode()+"】错误：交易状态码【"+response.getTradeState()+"】,交易状态名称【"+tradeStateEnum.getName()+"】,描述【"+response.getTradeStateDesc()+"】未处理");
							throw new LogicException("E005", "查询微信订单错误");
					}
				}else{
					logger.error("查询【"+payBean.getCode()+"】错误："+response.getMessage());
					throw new LogicException("E003", "查询微信订单错误");
				}
				break;
				
			default:
				throw new LogicException("E002", "暂不支持支付方式【"+payMethodEnum.getName()+"】,不能进行查询操作");
		}
		payBean.update();
		orderPayService.notifyPay(payBean);
	}

	@Override
	@Transactional
	public void queryRefund(RefundBean refundBean) {
		PayStatusEnum refundStatusEnum = PayStatusEnum.getPayStatusEnumByCode(refundBean.getStatus());
		if(!PayStatusEnum.FUNDING.equals(refundStatusEnum)){
			throw new LogicException("E001", "当前退款状态为【"+refundStatusEnum.getName()+"】,不能进行查询操作");
		}
		PayBean payBean = PayBean.get(PayBean.class, refundBean.getPayId());
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(payBean.getMethod());
		switch (payMethodEnum) {
			case WECHAT:
				SystemConfigBean appidSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.appid");
				SystemConfigBean mchIdSystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.mchId");
				SystemConfigBean keySystemConfigBean = systemConfigService.querySystemConfigBean("wx.pay.key");
				QueryRefundRequest request = new QueryRefundRequest();
				request.setAppid(appidSystemConfigBean.getPropertyValue());
				request.setMchId(mchIdSystemConfigBean.getPropertyValue());
				request.setTransactionId(payBean.getSerial());
				request.setOutTradeNo(payBean.getCode());
				request.setKey(keySystemConfigBean.getPropertyValue());
				request.setOutRefundNo(refundBean.getCode());
				request.setRefundId(refundBean.getSerial());
				QueryRefundResponse response = weiXinService.queryRefund(request);
				if(response.isSuccess()){
					RefundStateEnum refundStateEnum = RefundStateEnum.getRefundStateEnumByCode(response.getRefundStatus0());
					if(refundStateEnum==null){
						logger.error("查询退款【"+refundBean.getCode()+"】错误：未知退款状态码【"+response.getRefundStatus0()+"】,请联系卖家");
						throw new LogicException("E004", "查询微信退款错误");
					}
					switch (refundStateEnum) {
						case SUCCESS:
							refundBean.setStatus(PayStatusEnum.FUNDED.getCode());
							refundBean.setRefundTime(DateTools.getDate(response.getRefundSuccessTime0()));
							refundBean.setFromAccount(response.getRefundAccount0());
							refundBean.setToAccount(response.getRefundRecvAccout0());
							refundBean.setRefund(response.getRefundFee0().longValue());
							payBean.setRefund(payBean.getRefund()+refundBean.getRefund());
							payBean.setReceipts(payBean.getReceipts()-refundBean.getRefund());
							break;
						
						case REFUNDCLOSE:
						case CHANGE:
							refundBean.setStatus(PayStatusEnum.NOFUND.getCode());
							refundBean.setRefundTime(DateTools.now());
							break;
							
						case PROCESSING:
							break;
	
						default:
							logger.error("查询退款【"+payBean.getCode()+"】警告：退款状态码【"+response.getRefundStatus0()+"】,退款状态名称【"+refundStateEnum.getName()+"】未处理");
							throw new LogicException("E005", "查询微信退款错误");
					}
				}else{
					logger.error("查询退款【"+refundBean.getCode()+"】错误："+response.getMessage());
					throw new LogicException("E003", "查询微信退款错误");
				}
				break;
				
			default:
				throw new LogicException("E002", "暂不支持支付方式【"+payMethodEnum.getName()+"】,不能进行查询操作");
		}
		payBean.update();
		refundBean.update();
		orderPayService.notifyRefund(refundBean);
	}
	
}
