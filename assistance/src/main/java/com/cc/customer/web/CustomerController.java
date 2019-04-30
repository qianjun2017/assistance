/**
 * 
 */
package com.cc.customer.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.customer.service.CustomerService;
import com.cc.customer.bean.CustomerBean;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 客户开启商家服务
	 * @param retailerMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/retailer", method = RequestMethod.POST)
	public Response<Object> retailer(@RequestBody Map<String, String> retailerMap){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, Long.valueOf(retailerMap.get("customerId")));
		if (customerBean == null) {
			response.setMessage("您尚未注册");
			return response;
		}
		String store = retailerMap.get("store");
		if(StringTools.isNullOrNone(store)){
			response.setMessage("请输入店铺名称");
			return response;
		}
		customerBean.setStore(store);
		String phone = retailerMap.get("phone");
		if(StringTools.isNullOrNone(phone)){
			response.setMessage("请输入联系电话");
			return response;
		}
		customerBean.setPhone(phone);
		String address = retailerMap.get("address");
		if(StringTools.isNullOrNone(address)){
			response.setMessage("请输入店铺地址");
			return response;
		}
		customerBean.setAddress(address);
		customerBean.setRetailer(Boolean.TRUE);
		try {
			customerService.saveCustomer(customerBean);
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
