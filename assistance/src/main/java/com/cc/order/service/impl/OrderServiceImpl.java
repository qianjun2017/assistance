package com.cc.order.service.impl;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.goods.bean.GoodsBean;
import com.cc.goods.bean.GoodsStockBean;
import com.cc.goods.enums.GoodsStatusEnum;
import com.cc.goods.service.GoodsService;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.event.Event;
import com.cc.integration.event.EventFactory;
import com.cc.integration.service.IntegrationEventService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.order.bean.AddressBean;
import com.cc.order.bean.OrderBean;
import com.cc.order.bean.OrderSubGoodsBean;
import com.cc.order.dao.OrderDao;
import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;
import com.cc.order.bean.OrderSubBean;
import com.cc.order.enums.OrderStatusEnum;
import com.cc.order.enums.PayMethodEnum;
import com.cc.order.enums.PayStatusEnum;
import com.cc.order.form.CartGoodsForm;
import com.cc.order.form.OrderForm;
import com.cc.order.form.OrderQueryForm;
import com.cc.order.form.SubOrderForm;
import com.cc.order.form.SubOrderGoodsForm;
import com.cc.order.result.OrderListResult;
import com.cc.order.result.OrderSubResult;
import com.cc.order.result.PreOrderResult;
import com.cc.order.service.PayService;
import com.cc.order.service.OrderPayService;
import com.cc.order.service.OrderService;
import com.cc.system.message.service.MessageService;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.enums.UserStatusEnum;
import com.cc.system.user.enums.UserTypeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	private static Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private IntegrationEventService integrationEventService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private OrderPayService orderPayService;

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public Long saveOrder(OrderForm orderForm) {
        AddressBean addressBean = AddressBean.get(AddressBean.class, orderForm.getAddressId());
        if(addressBean==null){
        	throw new LogicException("E001", "收货地址不存在");
        }
        OrderBean orderBean = new OrderBean();
        orderBean.setCode(orderForm.getCode());
        orderBean.setAddressId(addressBean.getId());
    	orderBean.setAddress(addressBean.getAddress());
    	orderBean.setCreateTime(DateTools.now());
    	orderBean.setLeaguerId(orderForm.getLeaguerId());
    	orderBean.setMobile(addressBean.getMobile());
    	orderBean.setReceiver(addressBean.getReceiver());
    	orderBean.setTotal(0L);
    	orderBean.setReceivable(0L);
    	orderBean.setRefund(0L);
    	orderBean.setDiscount(0L);
    	orderBean.setReceipts(0L);
    	PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(orderForm.getPayMethod());
    	if(payMethodEnum==null){
    		throw new LogicException("E002", "请选择支付方式");
    	}
    	OrderStatusEnum orderStatusEnum;
        if(PayMethodEnum.CODPAY.equals(payMethodEnum)){
        	orderStatusEnum = OrderStatusEnum.TOBESHIPPED;
    	}else{
    		orderStatusEnum = OrderStatusEnum.TOBEPAY;
    	}
        orderBean.setStatus(orderStatusEnum.getCode());
        orderBean.setPayMethod(orderForm.getPayMethod());
    	orderBean.save();
        List<SubOrderForm> orderList = orderForm.getOrderList();
        for(SubOrderForm subOrder: orderList){
        	OrderSubBean orderSubBean = new OrderSubBean();
        	orderSubBean.setStatus(orderStatusEnum.getCode());
        	orderSubBean.setOrderId(orderBean.getId());
        	orderSubBean.setRemark(subOrder.getRemark());
        	UserBean userBean = UserBean.get(UserBean.class, subOrder.getSellerId());
        	if(userBean==null){
                throw new LogicException("E004", "卖家【"+subOrder.getSellerName()+"】无效，请更换商品");
            }
        	UserStatusEnum userStatusEnum = UserStatusEnum.getUserStatusEnumByCode(userBean.getStatus());
        	if(UserStatusEnum.NORMAL.equals(userStatusEnum)){
                throw new LogicException("E005", "卖家【"+subOrder.getSellerName()+"】处于非正常状态，暂时无法购买卖家的商品");
            }
        	orderSubBean.setSellerId(userBean.getId());
        	orderSubBean.setSellerName(userBean.getNickName());
        	orderSubBean.setDiscount(subOrder.getDiscount());
        	orderSubBean.setTotal(0L);
        	orderSubBean.setReceivable(0L);
        	orderSubBean.setRefund(0L);
        	orderSubBean.setReceipts(0L);
        	orderSubBean.save();
        	List<SubOrderGoodsForm> goodsList = subOrder.getGoodsList();
        	for(SubOrderGoodsForm goods: goodsList){
        		GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, goods.getGoodsId());
        		if(goodsBean==null){
        			throw new LogicException("E006", "商品【"+goods.getGoodsName()+"】无效，请更换商品");
        		}
        		GoodsStatusEnum goodsStatusEnum = GoodsStatusEnum.getGoodsStatusEnumByCode(goodsBean.getStatus());
        		if(GoodsStatusEnum.ON.equals(goodsStatusEnum)){
        			throw new LogicException("E007", "商品【"+goods.getGoodsName()+"】处于非上架状态，暂时无法购买");
        		}
        		OrderSubGoodsBean orderSubGoodsBean = new OrderSubGoodsBean();
        		orderSubGoodsBean.setCode(goodsBean.getCode());
        		orderSubGoodsBean.setGoodsId(goodsBean.getId());
        		orderSubGoodsBean.setName(goodsBean.getName());
        		orderSubGoodsBean.setPrice(goodsBean.getPrice());
        		orderSubGoodsBean.setQuantity(goods.getQuantity());
        		orderSubGoodsBean.setOrderSubId(orderSubBean.getId());
        		orderSubGoodsBean.setTotal(goodsBean.getPrice()*goods.getQuantity());
        		synchronized (orderSubGoodsBean.getCode()) {
        			goodsService.minusGoodsStock(orderSubGoodsBean.getQuantity(), orderSubGoodsBean.getGoodsId());
				}
        		orderSubGoodsBean.save();
        		orderSubBean.setTotal(orderSubBean.getTotal()+orderSubGoodsBean.getTotal());
        	}
        	orderSubBean.setReceivable(orderSubBean.getTotal()-orderSubBean.getDiscount());
        	orderSubBean.update();
        	orderBean.setTotal(orderBean.getTotal()+orderSubBean.getTotal());
        	orderBean.setDiscount(orderBean.getDiscount()+orderSubBean.getDiscount());
        	orderBean.setReceivable(orderBean.getReceivable()+orderSubBean.getReceivable());
        	if(PayMethodEnum.CODPAY.equals(payMethodEnum)){
	        	PayBean payBean = new PayBean();
	        	payBean.setStatus(PayStatusEnum.PAY.getCode());
	        	payBean.setMethod(payMethodEnum.getCode());
	        	payBean.setReceivable(orderSubBean.getReceivable());
	        	payBean.setReceipts(0L);
	        	payBean.setCode(StringTools.getSeqNo());
	        	payBean.setOrderId(orderBean.getId());
	        	payBean.setOrderSubId(orderSubBean.getId());
	        	payBean.save();
        	}
        }
        orderBean.update();
        if(!PayMethodEnum.CODPAY.equals(payMethodEnum)){
        	PayBean payBean = new PayBean();
        	payBean.setStatus(PayStatusEnum.PAY.getCode());
        	payBean.setMethod(payMethodEnum.getCode());
        	payBean.setReceivable(orderBean.getReceivable());
        	payBean.setReceipts(0L);
        	payBean.setCode(StringTools.getSeqNo());
        	payBean.setOrderId(orderBean.getId());
        	payBean.save();
        }
        return orderBean.getId();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void cancelOrder(Long id) {
    	OrderBean orderBean = OrderBean.get(OrderBean.class, id);
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderBean.getStatus());
		if(!(OrderStatusEnum.TOBEPAY.equals(orderStatusEnum) || OrderStatusEnum.TOBESHIPPED.equals(orderStatusEnum))){
			throw new LogicException("E002", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能取消");
		}
    	List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", id);
    	for(OrderSubBean orderSubBean: orderSubBeanList){
    		OrderStatusEnum orderSubStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
    		if(orderSubStatusEnum == null){
    			throw new LogicException("E001", "订单状态错误");
    		}
    		if(OrderStatusEnum.CLOSED.equals(orderSubStatusEnum)){
    			continue;
    		}
    		if(!(OrderStatusEnum.TOBEPAY.equals(orderSubStatusEnum) || OrderStatusEnum.TOBESHIPPED.equals(orderSubStatusEnum))){
    			throw new LogicException("E002", "当前订单部分商品处于【"+orderSubStatusEnum.getName()+"】,暂时不能取消");
    		}
        	List<OrderSubGoodsBean> orderGoodsBeanList = OrderSubGoodsBean.findAllByParams(OrderSubGoodsBean.class, "orderSubId", orderSubBean.getId());
        	for(OrderSubGoodsBean orderGoodsBean: orderGoodsBeanList){
        		goodsService.minusGoodsStock(-orderGoodsBean.getQuantity(), orderGoodsBean.getGoodsId());
        	}
            int row = orderSubBean.update();
            if(row!=1){
                throw new LogicException("E003", "取消订单失败");
            }
    	}
		PayBean payBean = orderPayService.queryPayBeanByOrderId(orderBean.getId());
		if(payBean==null){
			throw new LogicException("E004", "没有查询到订单的支付数据");
		}
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getCode());
		switch (payStatusEnum) {
			case PAYING:
				payService.queryPay(payBean);
				payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getCode());
				switch (payStatusEnum) {
					case PAYING:
						payService.close(payBean);
					case NOPAY:
						break;
						
					case PAYED:
						RefundBean refundBean = new RefundBean();
						refundBean.setCode(StringTools.getSeqNo());
						refundBean.setRefundable(payBean.getReceipts());
						refundBean.setPayId(payBean.getId());
						refundBean.setStatus(PayStatusEnum.FUND.getCode());
						refundBean.setOrderId(orderBean.getId());
						refundBean.save();
						payService.refund(refundBean);
						break;
	
					default:
						logger.error("关闭支付【"+payBean.getCode()+"】错误：未处理");
						throw new LogicException("E005", "取消订单失败");
				}
				break;
				
			case PAYED:
				RefundBean refundBean = new RefundBean();
				refundBean.setCode(StringTools.getSeqNo());
				refundBean.setRefundable(payBean.getReceipts());
				refundBean.setPayId(payBean.getId());
				refundBean.setStatus(PayStatusEnum.FUND.getCode());
				refundBean.setOrderId(orderBean.getId());
				refundBean.save();
				payService.refund(refundBean);
				break;
				
			case NOPAY:
				break;
	
			default:
				throw new LogicException("E006", "取消订单失败");
		}
		UserBean userBean = SecurityContextUtil.getCurrentUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(userBean.getUserType());
        if(UserTypeEnum.USER.equals(userTypeEnum)){
        	LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, orderBean.getLeaguerId());
        	messageService.releaseSystemMessage(userBean.getId(),"尊敬的会员【"+leaguerBean.getLeaguerName()+"】，您的订单【"+orderBean.getCode()+"】已被我方取消，如有疑问请联系我们！");
        }
        List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.BUY.getCode());
		if (!ListTools.isEmptyOrNull(integrationEventBeanList)) {
			if (integrationEventBeanList.size()>1) {
				logger.error("购买事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(orderBean.getLeaguerId());
				event.setData(orderBean);
				event.happened();
			}
		}
    }

    @Override
    public PreOrderResult preOrder(List<CartGoodsForm> cartGoodsFormList) {
        if(ListTools.isEmptyOrNull(cartGoodsFormList)){
            throw new LogicException("E001", "订单商品列表不能为空");
        }
        Map<Long, List<CartGoodsForm>> listMap = cartGoodsFormList.stream().collect(Collectors.groupingBy(cartGoods -> cartGoods.getGoods().getSellerId()));
        List<OrderSubResult> orderSubList = new ArrayList<OrderSubResult>();
        OrderBean orderBean = new OrderBean();
        Iterator<Map.Entry<Long, List<CartGoodsForm>>> iterator = listMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Long, List<CartGoodsForm>> entry = iterator.next();
            List<CartGoodsForm> cartGoodsForms = entry.getValue();
            OrderSubBean orderSubBean = new OrderSubBean();
            UserBean userBean = UserBean.get(UserBean.class, entry.getKey());
            if(userBean==null){
                throw new LogicException("E002", "商品【"+cartGoodsForms.get(0).getGoods().getName()+"】的卖家无效，请更换商品");
            }
            UserStatusEnum userStatusEnum = UserStatusEnum.getUserStatusEnumByCode(userBean.getStatus());
            if(!UserStatusEnum.NORMAL.equals(userStatusEnum)){
                throw new LogicException("E003", "商家【"+userBean.getNickName()+"】目前非正常状态，不接受下单");
            }
            orderSubBean.setSellerId(userBean.getId());
            orderSubBean.setSellerName(userBean.getNickName());
            orderSubBean.setTotal(0L);
            List<OrderSubGoodsBean> orderSubGoodsList = new ArrayList<OrderSubGoodsBean>();
            for (CartGoodsForm cartGoodsForm: cartGoodsForms){
                OrderSubGoodsBean orderSubGoodsBean = new OrderSubGoodsBean();
                GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, cartGoodsForm.getGoods().getId());
                if(goodsBean==null){
                    throw new LogicException("E004", "商品【"+cartGoodsForm.getGoods().getName()+"】存在，请更换商品");
                }
                GoodsStatusEnum goodsStatusEnum = GoodsStatusEnum.getGoodsStatusEnumByCode(goodsBean.getStatus());
                if(!GoodsStatusEnum.ON.equals(goodsStatusEnum)){
                    throw new LogicException("E005", "商品【"+cartGoodsForm.getGoods().getName()+"】已下架，请更换商品");
                }
                List<GoodsStockBean> goodsStockBeanList = GoodsStockBean.findAllByParams(GoodsStockBean.class, "goodsId", goodsBean.getId());
                if(ListTools.isEmptyOrNull(goodsStockBeanList)){
                    throw new LogicException("E006", "商品【"+cartGoodsForm.getGoods().getName()+"】无库存，请更换商品");
                }
                GoodsStockBean goodsStockBean = goodsStockBeanList.get(0);
                if (goodsStockBean.getStock()-goodsStockBean.getLockStock() < cartGoodsForm.getQuantity()) {
                    throw new LogicException("E007", "商品【" + cartGoodsForm.getGoods().getName() + "】库存不足，当前最新库存为" + (goodsStockBean.getStock()-goodsStockBean.getLockStock()));
                }
                orderSubGoodsBean.setCode(goodsBean.getCode());
                orderSubGoodsBean.setGoodsId(goodsBean.getId());
                orderSubGoodsBean.setName(goodsBean.getName());
                orderSubGoodsBean.setPrice(goodsBean.getPrice());
                orderSubGoodsBean.setQuantity(cartGoodsForm.getQuantity());
                orderSubGoodsBean.setTotal(goodsBean.getPrice()*cartGoodsForm.getQuantity());
                orderSubBean.setTotal(orderSubBean.getTotal()+orderSubGoodsBean.getTotal());
                orderSubGoodsList.add(orderSubGoodsBean);
            }
            OrderSubResult orderSub = new OrderSubResult();
            orderSub.setOrderSub(orderSubBean);
            orderSub.setOrderSubGoodsList(orderSubGoodsList);
            orderBean.setTotal(orderBean.getTotal()+orderSubBean.getTotal());
            orderSubList.add(orderSub);
        }
        PreOrderResult preOrderResult = new PreOrderResult();
        orderBean.setCode(StringTools.getSeqNo());
        preOrderResult.setOrder(orderBean);
        preOrderResult.setOrderSubList(orderSubList);
        return preOrderResult;
    }

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deliverySubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		if(orderSubBean==null){
			throw new LogicException("E005", "没有查询到订单数据");
		}
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!OrderStatusEnum.OUTOFSTOCK.equals(orderStatusEnum)){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能发货");
		}
		orderSubBean.setStatus(OrderStatusEnum.DISTRIBUTION.getCode());
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单发货失败");
        }
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void receiveSubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		if(orderSubBean==null){
			throw new LogicException("E005", "没有查询到订单数据");
		}
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!OrderStatusEnum.TOBERECEIVED.equals(orderStatusEnum)){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能收货");
		}
		PayBean payBean = orderPayService.queryPayBeanByOrderSubId(orderSubBean.getOrderId(), orderSubBean.getId());
		if(payBean==null){
			throw new LogicException("E003", "未查询到订单的支付信息");
		}
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		if(PayStatusEnum.PAYED.equals(payStatusEnum)){
			orderSubBean.setStatus(OrderStatusEnum.RECEIVED.getCode());
		}else{
			orderSubBean.setStatus(OrderStatusEnum.TOBEPAID.getCode());
		}
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单收货失败");
        }
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void backSubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!(OrderStatusEnum.RECEIVED.equals(orderStatusEnum) || OrderStatusEnum.TOBEPAID.equals(orderStatusEnum))){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能退货");
		}
		List<OrderSubGoodsBean> orderGoodsBeanList = OrderSubGoodsBean.findAllByParams(OrderSubGoodsBean.class, "orderSubId", orderSubBean.getId());
    	for(OrderSubGoodsBean orderGoodsBean: orderGoodsBeanList){
    		goodsService.minusGoodsStock(-orderGoodsBean.getQuantity(), orderGoodsBean.getGoodsId());
    	}
		if(OrderStatusEnum.RECEIVED.equals(orderStatusEnum)){
			orderSubBean.setStatus(OrderStatusEnum.RETURN.getCode());
		}else{
        	PayBean payBean = orderPayService.queryPayBeanByOrderSubId(orderSubBean.getOrderId(), orderSubBean.getId());
    		if(payBean==null){
    			throw new LogicException("E003", "未查询到订单的支付信息");
    		}
			orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
			List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", orderSubBean.getOrderId());
    		IntegrationEventTypeEnum integrationEventTypeEnum = IntegrationEventTypeEnum.PARTBUY;
    		if(orderSubBeanList.size()==1){
            	integrationEventTypeEnum = IntegrationEventTypeEnum.BUY;
            }
    		List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(integrationEventTypeEnum.getCode());
    		if (!ListTools.isEmptyOrNull(integrationEventBeanList)) {
    			if (integrationEventBeanList.size()>1) {
    				logger.error("购买事件不唯一");
    			}
    			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
    			if (event!=null) {
    				OrderBean orderBean = OrderBean.get(OrderBean.class, orderSubBean.getOrderId());
    				event.setLeaguerId(orderBean.getLeaguerId());
    				if(orderSubBeanList.size()==1){
    					event.setData(orderBean);
    				}else{
    					event.setData(orderSubBean);
    				}
    				event.rollBack();
    			}
    		}
		}
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单退货失败");
        }
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void refundSubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!OrderStatusEnum.RETURN.equals(orderStatusEnum)){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能退款");
		}
		List<OrderSubGoodsBean> orderGoodsBeanList = OrderSubGoodsBean.findAllByParams(OrderSubGoodsBean.class, "orderSubId", orderSubBean.getId());
    	for(OrderSubGoodsBean orderGoodsBean: orderGoodsBeanList){
    		goodsService.minusGoodsStock(-orderGoodsBean.getQuantity(), orderGoodsBean.getGoodsId());
    	}
    	PayBean payBean = orderPayService.queryPayBeanByOrderSubId(orderSubBean.getOrderId(), orderSubBean.getId());
		if(payBean==null){
			throw new LogicException("E003", "未查询到订单的支付信息");
		}
		RefundBean refundBean = new RefundBean();
    	refundBean.setCode(StringTools.getSeqNo());
    	refundBean.setPayId(payBean.getId());
    	refundBean.setRefundable(payBean.getReceipts());
    	refundBean.setStatus(PayStatusEnum.FUND.getCode());
    	refundBean.setOrderId(orderSubBean.getOrderId());
    	refundBean.setOrderSubId(orderSubBean.getId());
    	refundBean.save();
    	payService.refund(refundBean);
		orderSubBean.setStatus(OrderStatusEnum.REFUND.getCode());
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单退款失败");
        }
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void readySubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!OrderStatusEnum.TOBESHIPPED.equals(orderStatusEnum)){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能备货");
		}
		orderSubBean.setStatus(OrderStatusEnum.OUTOFSTOCK.getCode());
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单备货失败");
        }
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void arriveSubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderStatusEnum == null){
			throw new LogicException("E006", "订单状态错误");
		}
		if(!OrderStatusEnum.DISTRIBUTION.equals(orderStatusEnum)){
			throw new LogicException("E007", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能进行送达操作");
		}
		orderSubBean.setStatus(OrderStatusEnum.TOBERECEIVED.getCode());
		int row = orderSubBean.update();
		if(row!=1){
            throw new LogicException("E008", "订单送达失败");
        }
	}

	@Override
	@Transactional
	public Object payOrder(Long id) {
		Object object = null;
		OrderBean orderBean = OrderBean.get(OrderBean.class, id);
		if(orderBean == null){
			throw new LogicException("E001", "未查询到订单信息");
		}
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderBean.getCode());
		if(!OrderStatusEnum.TOBEPAY.equals(orderStatusEnum)){
			throw new LogicException("E002", "当前订单状态为【"+orderStatusEnum.getName()+"】,不能付款");
		}
		PayBean payBean = orderPayService.queryPayBeanByOrderId(orderBean.getId());
		if(payBean==null){
			throw new LogicException("E003", "未查询到订单的支付信息");
		}
		object = payService.pay(payBean);
		payBean.update();
		orderPayService.notifyPay(payBean);
		return object;
	}

	@Override
	public void cancelSubOrder(Long orderSubId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, orderSubId);
		OrderStatusEnum orderSubStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(orderSubStatusEnum == null){
			throw new LogicException("E001", "订单状态错误");
		}
		if(!(OrderStatusEnum.TOBEPAY.equals(orderSubStatusEnum) || OrderStatusEnum.TOBESHIPPED.equals(orderSubStatusEnum) || OrderStatusEnum.OUTOFSTOCK.equals(orderSubStatusEnum))){
			throw new LogicException("E002", "当前订单状态为【"+orderSubStatusEnum.getName()+"】,不能取消");
		}
		OrderBean orderBean = OrderBean.get(OrderBean.class, orderSubBean.getOrderId());
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderBean.getStatus());
		if(OrderStatusEnum.OVER.equals(orderStatusEnum)){
			throw new LogicException("E002", "当前订单已结束,不能取消");
		}
    	List<OrderSubGoodsBean> orderGoodsBeanList = OrderSubGoodsBean.findAllByParams(OrderSubGoodsBean.class, "orderSubId", orderSubBean.getId());
    	for(OrderSubGoodsBean orderGoodsBean: orderGoodsBeanList){
    		goodsService.minusGoodsStock(-orderGoodsBean.getQuantity(), orderGoodsBean.getGoodsId());
    	}
    	orderSubBean.setStatus(OrderStatusEnum.CLOSED.getCode());
    	int row = orderSubBean.update();
        if(row!=1){
            throw new LogicException("E003", "取消订单失败");
        }
        PayBean payBean = orderPayService.queryPayBeanByOrderSubId(orderSubBean.getOrderId(), orderSubBean.getId());
		if(payBean==null){
			throw new LogicException("E003", "未查询到订单的支付信息");
		}
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
		switch (payStatusEnum) {
			case PAYING:
				payService.queryPay(payBean);
				payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(payBean.getStatus());
				switch (payStatusEnum) {
					case PAYING:
						throw new LogicException("E007", "当前订单正在支付中,等待卖家先完成支付");
						
					case NOPAY:
						break;
						
					case PAYED:
						RefundBean refundBean = new RefundBean();
				    	refundBean.setCode(StringTools.getSeqNo());
				    	refundBean.setPayId(payBean.getId());
				    	refundBean.setRefundable(payBean.getReceipts());
				    	refundBean.setStatus(PayStatusEnum.FUND.getCode());
				    	refundBean.setOrderId(orderSubBean.getOrderId());
				    	refundBean.setOrderSubId(orderSubBean.getId());
				    	refundBean.save();
				    	payService.refund(refundBean);
						break;
	
					default:
						logger.error("关闭支付子订单【"+orderSubBean.getId()+"】错误：未处理");
						throw new LogicException("E005", "取消订单失败");
				}
				break;
				
			case PAYED:
				RefundBean refundBean = new RefundBean();
		    	refundBean.setCode(StringTools.getSeqNo());
		    	refundBean.setPayId(payBean.getId());
		    	refundBean.setRefundable(payBean.getReceipts());
		    	refundBean.setStatus(PayStatusEnum.FUND.getCode());
		    	refundBean.setOrderId(orderSubBean.getOrderId());
		    	refundBean.setOrderSubId(orderSubBean.getId());
		    	refundBean.save();
		    	payService.refund(refundBean);
				break;
				
			case NOPAY:
				break;
	
			default:
				throw new LogicException("E006", "取消订单失败");
		}
		UserBean userBean = SecurityContextUtil.getCurrentUser();
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(userBean.getUserType());
        if(UserTypeEnum.SELLER.equals(userTypeEnum)){
        	LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, orderBean.getLeaguerId());
        	messageService.releaseSystemMessage(leaguerBean.getId(),"尊敬的客户【"+leaguerBean.getLeaguerName()+"】，您的订单【"+orderBean.getCode()+"】已被卖家【"+orderSubBean.getSellerName()+"】取消，如有疑问请联系我们！");
        }
        IntegrationEventTypeEnum integrationEventTypeEnum = IntegrationEventTypeEnum.PARTBUY;
        List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", orderSubBean.getOrderId());
		if(orderSubBeanList.size()==1){
        	integrationEventTypeEnum = IntegrationEventTypeEnum.BUY;
        }
        List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(integrationEventTypeEnum.getCode());
		if (!ListTools.isEmptyOrNull(integrationEventBeanList)) {
			if (integrationEventBeanList.size()>1) {
				logger.error("购买事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(orderBean.getLeaguerId());
				if(orderSubBeanList.size()==1){
					event.setData(orderBean);
				}else{
					event.setData(orderSubBean);
				}
				event.happened();
			}
		}
	}

	@Override
	public Page<OrderListResult> queryOrderPage(OrderQueryForm form) {
		Page<OrderListResult> page = new Page<OrderListResult>();
		PageHelper.orderBy(String.format("o.%s %s", form.getSort(), form.getOrder()));
        PageHelper.startPage(form.getPage(), form.getPageSize());
        List<OrderListResult> orderList = orderDao.queryOrderList(form);
        PageInfo<OrderListResult> pageInfo = new PageInfo<OrderListResult>(orderList);
        if (ListTools.isEmptyOrNull(orderList)) {
            page.setMessage("没有查询到相关订单数据");
            return page;
        }
        for(OrderListResult orderListResult: orderList){
        	orderListResult.setTotal(StringTools.toRmbYuan(orderListResult.getTotal()));
        	orderListResult.setPayMethodName(PayMethodEnum.getNameByCode(orderListResult.getPayMethod()));
        	orderListResult.setStatusName(OrderStatusEnum.getNameByCode(orderListResult.getStatus()));
        }
        page.setPage(pageInfo.getPageNum());
        page.setPages(pageInfo.getPages());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        page.setData(orderList);
        page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public void paySubOrder(Long subId) {
		OrderSubBean orderSubBean = OrderSubBean.get(OrderSubBean.class, subId);
		if(orderSubBean == null){
			throw new LogicException("E001", "未查询到订单信息");
		}
		OrderStatusEnum orderSubStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(orderSubBean.getStatus());
		if(!OrderStatusEnum.TOBEPAID.equals(orderSubStatusEnum)){
			throw new LogicException("E002", "当前订单状态为【"+orderSubStatusEnum.getName()+"】,不能收款");
		}
		PayBean payBean = orderPayService.queryPayBeanByOrderSubId(orderSubBean.getOrderId(), orderSubBean.getId());
		if(payBean==null){
			throw new LogicException("E003", "未查询到订单的支付信息");
		}
		payService.pay(payBean);
		IntegrationEventTypeEnum integrationEventTypeEnum = IntegrationEventTypeEnum.PARTBUY;
		List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByParams(OrderSubBean.class, "orderId", orderSubBean.getOrderId());
		if(orderSubBeanList.size()==1){
        	integrationEventTypeEnum = IntegrationEventTypeEnum.BUY;
        }
		List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(integrationEventTypeEnum.getCode());
		if (!ListTools.isEmptyOrNull(integrationEventBeanList)) {
			if (integrationEventBeanList.size()>1) {
				logger.error("购买事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				OrderBean orderBean = OrderBean.get(OrderBean.class, orderSubBean.getOrderId());
				event.setLeaguerId(orderBean.getLeaguerId());
				if(orderSubBeanList.size()==1){
					event.setData(orderBean);
				}else{
					event.setData(orderSubBean);
				}
				event.happened();
			}
		}
		orderPayService.notifyPay(payBean);
	}
}
