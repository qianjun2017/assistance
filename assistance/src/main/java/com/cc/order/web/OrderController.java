package com.cc.order.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.order.bean.OrderBean;
import com.cc.order.bean.PayBean;
import com.cc.order.bean.RefundBean;
import com.cc.order.bean.OrderSubBean;
import com.cc.order.bean.OrderSubGoodsBean;
import com.cc.order.form.OrderQueryForm;
import com.cc.order.result.OrderListResult;
import com.cc.order.result.OrderResult;
import com.cc.order.result.OrderSubResult;
import com.cc.order.service.OrderService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.enums.UserTypeEnum;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 支付订单
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.goods" })
	@RequestMapping(value = "/pay/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.PAYORDER, title = "支付订单", paramNames = {"id"})
	public Response<Object> payOrder(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			Object data = orderService.payOrder(id);
			if(data == null){
				response.setMessage("支付失败，请联系系统管理员");
			}else{
				response.setData(data);
				response.setSuccess(Boolean.TRUE);
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 支付子订单
	 * @param subOrderId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "order.pay" })
	@RequestMapping(value = "/sub/pay/{subOrderId:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.PAYORDER, title = "商家订单收款", paramNames = {"subOrderId"})
	public Response<Object> paySubOrder(@PathVariable Long subOrderId){
		Response<Object> response = new Response<Object>();
		try {
			orderService.paySubOrder(subOrderId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 取消订单
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = "order.cancel")
	@RequestMapping(value = "/cancel/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.CANCELORDER, title = "取消订单", paramNames = {"id"})
	public Response<String> cancelOrder(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			orderService.cancelOrder(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 取消子订单
	 * @param orderSubId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "order.cancel" })
	@RequestMapping(value = "/sub/cancel/{orderSubId:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.CANCELORDER, title = "取消子订单", paramNames = {"orderSubId"})
	public Response<String> cancelSubOrder(@PathVariable Long orderSubId){
		Response<String> response = new Response<String>();
		try {
			orderService.cancelSubOrder(orderSubId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 子订单发货
	 * @param orderSubId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "order.delivery" })
	@RequestMapping(value = "/sub/delivery/{orderSubId:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.DELIVERYORDER, title = "订单发货", paramNames = {"orderSubId"})
	public Response<String> deliverySubOrder(@PathVariable Long orderSubId){
		Response<String> response = new Response<String>();
		try {
			orderService.deliverySubOrder(orderSubId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 子订单退货
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "order.back" })
	@RequestMapping(value = "/sub/back/{orderSubId:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ORDERMANAGEMENT, operType = OperTypeEnum.BACKORDER, title = "商家订单退货", paramNames = {"orderSubId"})
	public Response<String> backSubOrder(@PathVariable Long orderSubId){
		Response<String> response = new Response<String>();
		try {
			orderService.backSubOrder(orderSubId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询订单
	 * @param form
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<OrderListResult> queryOrderPage(@ModelAttribute OrderQueryForm form){
		UserBean userBean = SecurityContextUtil.getCurrentUser();
		if(userBean==null){
			Page<OrderListResult> page = new Page<OrderListResult>();
        	page.setMessage("没有查询到相关订单数据");
            return page;
		}
		form.setSellerId(userBean.getId());
        return orderService.queryOrderPage(form);
	}
	
	/**
	 * 查询订单详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<OrderResult> queryOrder(@PathVariable Long id){
		Response<OrderResult> response = new Response<OrderResult>();
		UserBean userBean = SecurityContextUtil.getCurrentUser();
		if(userBean==null){
			response.setMessage("请登录");
			return response;
		}
		OrderBean orderBean = OrderBean.get(OrderBean.class, id);
		if(orderBean==null){
			response.setMessage("订单不存在");
			return response;
		}
		OrderResult orderResult = new OrderResult();
		orderResult.setOrder(orderBean);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(userBean.getUserType());
        if(UserTypeEnum.SELLER.equals(userTypeEnum)){
        	paramMap.put("sellerId", userBean.getId());
        }
		paramMap.put("orderId", orderBean.getId());
		List<OrderSubBean> orderSubBeanList = OrderSubBean.findAllByMap(OrderSubBean.class, paramMap);
		List<OrderSubResult> orderSubResultList = new ArrayList<OrderSubResult>();
		if(!ListTools.isEmptyOrNull(orderSubBeanList)){
			for(OrderSubBean orderSubBean: orderSubBeanList){
				OrderSubResult orderSubResult = new OrderSubResult();
				orderSubResult.setOrderSub(orderSubBean);
				orderSubResult.setOrderSubGoodsList(OrderSubGoodsBean.findAllByParams(OrderSubGoodsBean.class, "orderSubId", orderSubBean.getId()));
				orderSubResultList.add(orderSubResult);
			}
		}
		orderResult.setOrderSubList(orderSubResultList);
		List<PayBean> orderPayBeanList = PayBean.findAllByParams(PayBean.class, "orderId", orderBean.getId());
		if(!ListTools.isEmptyOrNull(orderPayBeanList)){
			orderResult.setOrderPay(orderPayBeanList.get(0));
		}
		orderResult.setOrderRefundList(RefundBean.findAllByMap(RefundBean.class, paramMap));
		response.setData(orderResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
