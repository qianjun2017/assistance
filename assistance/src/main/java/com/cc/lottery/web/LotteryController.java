/**
 * 
 */
package com.cc.lottery.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.carousel.bean.CarouselBean;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DESTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.customer.bean.CustomerBean;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryForm;
import com.cc.lottery.form.LotteryPrizeForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryListResult;
import com.cc.lottery.service.LotteryService;
import com.cc.system.shiro.SecurityContextUtil;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/lottery")
public class LotteryController {
	
	@Autowired
	private LotteryService lotteryService;

	/**
	 * 增加抽奖
	 * @param lotteryMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response<Object> addLottery(@RequestBody Map<String, Object> lotteryMap){
		Response<Object> response = new Response<Object>();
		LotteryForm lotteryForm = JsonTools.toObject(JsonTools.toJsonString(lotteryMap), LotteryForm.class);
		if(lotteryForm.getCustomerId()==null){
			response.setMessage("匿名客户不能发起抽奖");
			return response;
		}
		CustomerBean customerBean = CustomerBean.get(CustomerBean.class, lotteryForm.getCustomerId());
		if(customerBean==null){
			response.setMessage("您尚未注册");
			return response;
		}
		if(!customerBean.getRetailer()){
			response.setMessage("您尚未开通商家服务");
			return response;
		}
		String openid = DESTools.decrypt(lotteryForm.getOpenid(), SecurityContextUtil.getDESKey());
		if(!customerBean.getOpenid().equals(openid)){
			response.setMessage("您无权为他人发起抽奖");
			return response;
		}
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setCustomerId(customerBean.getId());
		if(lotteryForm.getMCount()==null){
			response.setMessage("请输入每个客户最多抽奖次数");
			return response;
		}
		if(Integer.valueOf(0).equals(lotteryForm.getMCount())){
			response.setMessage("每个客户最多抽奖次数必须大于0");
			return response;
		}
		lotteryBean.setMCount(lotteryForm.getMCount());
		List<LotteryBean> lotteryList = LotteryBean.findAllByParams(LotteryBean.class, "customerId", customerBean.getId(), "createTime desc");
		if(ListTools.isEmptyOrNull(lotteryList)){
			lotteryBean.setNo(1l);
		}else{
			lotteryBean.setNo(lotteryList.get(0).getNo()+1);
		}
		List<LotteryPrizeBean> lotteryPrizeBeanList = new ArrayList<LotteryPrizeBean>();
		Integer weight = 0;
		for(LotteryPrizeForm lotteryPrizeForm: lotteryForm.getPrizeList()){
			LotteryPrizeBean lotteryPrizeBean = new LotteryPrizeBean();
			if(StringTools.isNullOrNone(lotteryPrizeForm.getName())){
				response.setMessage("奖项名称不能为空");
				return response;
			}
			lotteryPrizeBean.setName(lotteryPrizeForm.getName());
			if(lotteryPrizeForm.getTotal()==null){
				response.setMessage("请输入奖项["+lotteryPrizeBean.getName()+"]总数");
				return response;
			}
			if(Integer.valueOf(0).equals(lotteryPrizeForm.getTotal())){
				response.setMessage("奖项["+lotteryPrizeBean.getName()+"]总数必须大于0");
				return response;
			}
			lotteryPrizeBean.setTotal(lotteryPrizeForm.getTotal());
			lotteryPrizeBean.setQuantity(0);
			if(lotteryPrizeForm.getWeight()==null){
				response.setMessage("请输入奖项["+lotteryPrizeBean.getName()+"]万次抽奖中奖次数，用来计算中奖概率，必须填写");
				return response;
			}
			if(Integer.valueOf(0).equals(lotteryPrizeForm.getWeight())){
				response.setMessage("奖项["+lotteryPrizeBean.getName()+"]万次抽奖中奖次数必须大于0");
				return response;
			}
			lotteryPrizeBean.setWeight(lotteryPrizeForm.getWeight());
			weight += lotteryPrizeBean.getWeight();
			lotteryPrizeBeanList.add(lotteryPrizeBean);
		}
		if(ListTools.isEmptyOrNull(lotteryPrizeBeanList)){
			response.setMessage("至少设置一个奖项");
			return response;
		}
		if(weight>10000){
			response.setMessage("所有奖项万次抽奖中奖总次数不能超过10000");
			return response;
		}
		lotteryBean.setPrizeList(lotteryPrizeBeanList);
		lotteryBean.setCreateTime(DateTools.now());
		lotteryBean.setStatus(LotteryStatusEnum.NORMAL.getCode());
		try {
			lotteryService.saveLottery(lotteryBean);
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
	 * 查询抽奖详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<LotteryBean> queryCarousel(@PathVariable Long id){
		Response<LotteryBean> response = new Response<LotteryBean>();
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, id);
		if (lotteryBean==null) {
			response.setMessage("抽奖不存在");
			return response;
		}
		lotteryBean.setPrizeList(LotteryPrizeBean.findAllByParams(LotteryPrizeBean.class, "lotteryId", lotteryBean.getId()));
		response.setData(lotteryBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<LotteryListResult> queryCarouselPage(@ModelAttribute LotteryQueryForm form){
		return lotteryService.queryLotteryPage(form);
	}
}
