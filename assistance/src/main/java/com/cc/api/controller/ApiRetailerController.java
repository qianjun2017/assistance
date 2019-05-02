package com.cc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.RetailerForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.lottery.bean.LotteryRetailerBean;
import com.cc.lottery.service.LotteryService;

@Controller
@RequestMapping("/api/retailer")
public class ApiRetailerController {
	
	@Autowired
	private LotteryService lotteryService;

	/**
	 * 会员申请为商家
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response<String> addRetailerInfo(@RequestBody RetailerForm form){
		Response<String> response = new Response<String>();
		LotteryRetailerBean lotteryRetailerBean = new LotteryRetailerBean();
		if(StringTools.isNullOrNone(form.getStore())){
			response.setMessage("请输入店铺名称");
			return response;
		}
		if(StringTools.isNullOrNone(form.getAddress())){
			response.setMessage("请输入店铺地址");
			return response;
		}
		lotteryRetailerBean.setAddress(form.getAddress());
		lotteryRetailerBean.setStore(form.getStore());
		lotteryRetailerBean.setLeaguerId(form.getLeaguerId());
		try {
			lotteryService.saveLotteryRetailer(lotteryRetailerBean);
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
