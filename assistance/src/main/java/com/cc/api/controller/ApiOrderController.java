package com.cc.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.order.form.CartGoodsForm;
import com.cc.order.form.OrderForm;
import com.cc.order.form.SubOrderForm;
import com.cc.order.result.PreOrderResult;
import com.cc.order.service.OrderService;

@Controller
@RequestMapping("/api/order")
public class ApiOrderController {
	
	@Autowired
	private OrderService orderService;

	/**
	 * 预下单
	 * @param cartMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pre", method = RequestMethod.POST)
	public Response<PreOrderResult> preOrder(@RequestBody Map<String, Object> cartMap){
		Response<PreOrderResult> response = new Response<PreOrderResult>();
		Object object = cartMap.get("cartGoodsList");
		if(object == null || !(object instanceof List<?>)){
			response.setMessage("请选择商品");
			return response;
		}
		List<Object> cartGoodsList = (List<Object>) object;
		if(ListTools.isEmptyOrNull(cartGoodsList)){
			response.setMessage("请选择商品");
			return response;
		}
		List<CartGoodsForm> cartGoodsFormList = new ArrayList<CartGoodsForm>();
		for(Object cartGoods: cartGoodsList){
			try {
				CartGoodsForm cartGoodsForm = JsonTools.toObject(JsonTools.toJsonString(cartGoods), CartGoodsForm.class);
				if(cartGoodsForm==null || cartGoodsForm.getGoods()==null){
					response.setMessage("商品参数格式错误");
					return response;
				}
				cartGoodsFormList.add(cartGoodsForm);
			} catch (Exception e) {
				response.setMessage("参数格式错误");
				return response;
			}
		}
		try {
			PreOrderResult preOrder = orderService.preOrder(cartGoodsFormList);
			response.setSuccess(Boolean.TRUE);
			response.setData(preOrder);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 下单
	 * @param orderMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Response<Object> saveOrder(@RequestBody Map<String, Object> orderMap){
		Response<Object> response = new Response<Object>();
		try {
			OrderForm orderForm = JsonTools.toObject(JsonTools.toJsonString(orderMap), OrderForm.class);
			if(orderForm==null){
				response.setMessage("参数格式错误");
				return response;
			}
			if(orderForm.getAddressId()==null) {
				response.setMessage("请选择收货地址");
				return response;
			}
			if(StringTools.isNullOrNone(orderForm.getPayMethod())){
				response.setMessage("请选择支付方式");
				return response;
			}
			if(ListTools.isEmptyOrNull(orderForm.getOrderList())){
				response.setMessage("请选择商家");
				return response;
			}
			for(SubOrderForm subOrderForm: orderForm.getOrderList()){
				if(ListTools.isEmptyOrNull(subOrderForm.getGoodsList())){
					response.setMessage("请选择商家【"+subOrderForm.getSellerName()+"】的商品");
					return response;
				}
			}
            orderForm.setLeaguerId(Long.valueOf(StringTools.toString(orderMap.get("leaguerId"))));
			Long orderId = orderService.saveOrder(orderForm);
			response.setSuccess(Boolean.TRUE);
			response.setData(orderId);
		} catch (Exception e) {
			response.setMessage("参数格式错误");
			return response;
		}
		return response;
	}
	
	/**
	 * 取消子订单
	 * @param orderSubId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sub/cancel/{orderSubId:\\d+}", method = RequestMethod.POST)
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
	 * 订单收货
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sub/receive/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> receiveOrder(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			orderService.receiveSubOrder(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
