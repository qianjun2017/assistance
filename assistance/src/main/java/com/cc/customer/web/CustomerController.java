/**
 * 
 */
package com.cc.customer.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.service.CustomerService;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.enums.CustomerStatusEnum;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

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
	 * 客户注册
	 * @param registerMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response<Object> register(@RequestBody Map<String, Object> registerMap){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = JsonTools.toObject(JsonTools.toJsonString(registerMap), CustomerBean.class);
		if(StringTools.isNullOrNone(customerBean.getOpenid())){
			response.setMessage("请输入客户微信openid");
			return response;
		}
		customerBean.setStatus(CustomerStatusEnum.NORMAL.getCode());
		customerBean.setCreateTime(DateTools.now());
		customerBean.setRetailer(Boolean.FALSE);
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
	
	/**
	 * 客户信息
	 * @param customerMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public Response<Object> updateCustomerInfo(@RequestBody Map<String, String> customerMap){
		Response<Object> response = new Response<Object>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, Long.valueOf(customerMap.get("customerId")));
		if (customerBean == null) {
			response.setMessage("您尚未注册");
			return response;
		}
		customerBean.setNickName(customerMap.get("nickName"));
		customerBean.setAvatarUrl(customerMap.get("avatarUrl"));
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
	
	/**
	 * 查询客户信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<CustomerBean> queryCustomer(@PathVariable Long id){
		Response<CustomerBean> response = new Response<CustomerBean>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("客户不存在");
			return response;
		}
		response.setData(customerBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询客户信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Response<Object> queryCustomerInfo(@ModelAttribute CustomerQueryForm form){
		Response<Object> response = new Response<Object>();
		List<CustomerBean> customerBeanList = CustomerBean.findAllByParams(CustomerBean.class, "openid", form.getOpenid());
		if (ListTools.isEmptyOrNull(customerBeanList)) {
			response.setMessage("客户不存在");
			response.setData(404);
			return response;
		}
		response.setData(customerBeanList.get(0));
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 锁定客户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.lock" })
	@RequestMapping(value = "/lock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.LOCK, title = "锁定客户", paramNames = {"id"})
	public Response<String> lockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("客户不存在");
			return response;
		}
		try {
			customerService.lockCustomer(id);
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
	 * 解锁客户
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "customer.unlock" })
	@RequestMapping(value = "/unlock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CUSTOMERMANAGEMENT, operType = OperTypeEnum.UNLOCK, title = "解锁客户", paramNames = {"id"})
	public Response<String> unlockCustomer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, id);
		if (customerBean == null) {
			response.setMessage("客户不存在");
			return response;
		}
		try {
			customerService.unLockCustomer(id);
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
	 * 分页查询客户信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<CustomerBean> queryCustomerPage(@ModelAttribute CustomerQueryForm form){
		Page<CustomerBean> page = customerService.queryCustomerPage(form);
		return page;
	}
	
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
