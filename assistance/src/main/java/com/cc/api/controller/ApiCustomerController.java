/**
 * 
 */
package com.cc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.CustomerForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.customer.bean.CardLevelBean;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.result.CustomerResult;
import com.cc.customer.service.CardService;
import com.cc.customer.service.CustomerService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/customer")
public class ApiCustomerController {
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * 查询登录用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Response<Object> queryCustomerInfo(@RequestBody CustomerForm form){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, form.getCustomerId());
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		CustomerResult customerResult = JsonTools.toObject(JsonTools.toJsonString(customerBean), CustomerResult.class);
		CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
		if(cardLevelBean!=null){
			customerResult.setCardLevel(cardLevelBean.getName());
			customerResult.setCardImage(cardLevelBean.getImageUrl());
			customerResult.setCardColor(cardLevelBean.getColor());
		}
		response.setData(customerResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 会员更新个人信息  姓名和手机号
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response<Object> updateCustomerInfo(@RequestBody CustomerForm form){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, form.getCustomerId());
		if (customerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		if(StringTools.isNullOrNone(form.getName())){
			response.setMessage("姓名不能为空");
			return response;
		}
		if(StringTools.isNullOrNone(form.getPhone())){
			response.setMessage("手机号码不能为空");
			return response;
		}
		if(!StringTools.matches(form.getPhone(), "^1[34578]\\d{9}$")){
			response.setMessage("请输入11位有效手机号码");
            return response;
		}
		customerBean.setName(form.getName());
		customerBean.setPhone(form.getPhone());
		try {
			customerService.saveCustomer(customerBean);
			CustomerResult customerResult = JsonTools.toObject(JsonTools.toJsonString(customerBean), CustomerResult.class);
			CardLevelBean cardLevelBean = cardService.queryCardLevelByPoints(customerBean.getPoints());
			if(cardLevelBean!=null){
				customerResult.setCardLevel(cardLevelBean.getName());
				customerResult.setCardImage(cardLevelBean.getImageUrl());
			}
			response.setData(customerResult);
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
