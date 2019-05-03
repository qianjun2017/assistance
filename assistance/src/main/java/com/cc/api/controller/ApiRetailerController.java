package com.cc.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.RetailerForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryLeaguerBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.bean.LotteryRetailerBean;
import com.cc.lottery.enums.LotteryLeaguerStatusEnum;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryForm;
import com.cc.lottery.form.LotteryLeaguerQueryForm;
import com.cc.lottery.form.LotteryPrizeForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryLeaguerListResult;
import com.cc.lottery.result.LotteryListResult;
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
	@RequestMapping(value = "/info", method = RequestMethod.POST)
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
	
	
	/**
	 * 查询抽奖详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<LotteryBean> queryLottery(@PathVariable Long id){
		Response<LotteryBean> response = new Response<LotteryBean>();
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, id);
		if (lotteryBean==null) {
			response.setMessage("抽奖不存在或已删除");
			return response;
		}
		lotteryBean.setPrizeList(LotteryPrizeBean.findAllByParams(LotteryPrizeBean.class, "lotteryId", lotteryBean.getId()));
		response.setData(lotteryBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	

	/**
	 * 增加抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Response<Object> addLottery(@RequestBody LotteryForm form){
		Response<Object> response = new Response<Object>();
		if(form.getLeaguerId()==null){
			response.setMessage("匿名客户不能发起抽奖");
			return response;
		}
		List<LotteryRetailerBean> lotteryRetailerBeanList = LotteryRetailerBean.findAllByParams(LotteryRetailerBean.class, "leaguerId", form.getLeaguerId());
		if(ListTools.isEmptyOrNull(lotteryRetailerBeanList)){
			response.setMessage("您尚未开通商家服务");
			return response;
		}
		LotteryRetailerBean lotteryRetailerBean = lotteryRetailerBeanList.get(0);
		if(DateTools.now().after(lotteryRetailerBean.getRetailerTime())){
			response.setMessage("您的商家服务已到期，请续期后再发起抽奖");
			return response;
		}
		LotteryBean lotteryBean = new LotteryBean();
		lotteryBean.setLeaguerId(form.getLeaguerId());
		if(form.getCount()==null){
			response.setMessage("请输入每个客户最多抽奖次数");
			return response;
		}
		if(Integer.valueOf(0).equals(form.getCount())){
			response.setMessage("每个客户最多抽奖次数必须大于0");
			return response;
		}
		lotteryBean.setCount(form.getCount());
		if(form.getLastExchangeTime()==null){
			response.setMessage("请选择最后兑奖时间");
			return response;
		}
		lotteryBean.setLastExchangeTime(DateTools.getDate(form.getLastExchangeTime()+" 23:59:59"));
		lotteryBean.setShare(form.getShare());
		lotteryBean.setSame(form.getSame());
		List<LotteryBean> lotteryList = LotteryBean.findAllByParams(LotteryBean.class, "leaguerId", form.getLeaguerId(), "sort", "createTime", "order", "desc");
		if(ListTools.isEmptyOrNull(lotteryList)){
			lotteryBean.setNo(1l);
		}else{
			LotteryBean LastestLotteryBean = lotteryList.get(0);
			if(LotteryStatusEnum.NORMAL.equals(LotteryStatusEnum.getLotteryStatusEnumByCode(LastestLotteryBean.getStatus()))){
				response.setMessage("请先结束正在进行中的抽奖");
				return response;
			}
			lotteryBean.setNo(LastestLotteryBean.getNo()+1);
		}
		List<LotteryPrizeBean> lotteryPrizeBeanList = new ArrayList<LotteryPrizeBean>();
		Integer weight = 0;
		for(LotteryPrizeForm lotteryPrizeForm: form.getPrizeList()){
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
			lotteryPrizeBean.setStatus(LotteryStatusEnum.NORMAL.getCode());
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
	 * 修改抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response<Object> updateLottery(@RequestBody LotteryForm form){
		Response<Object> response = new Response<Object>();
		List<LotteryRetailerBean> lotteryRetailerBeanList = LotteryRetailerBean.findAllByParams(LotteryRetailerBean.class, "leaguerId", form.getLeaguerId());
		if(ListTools.isEmptyOrNull(lotteryRetailerBeanList)){
			response.setMessage("您尚未开通商家服务");
			return response;
		}
		LotteryRetailerBean lotteryRetailerBean = lotteryRetailerBeanList.get(0);
		if(DateTools.now().after(lotteryRetailerBean.getRetailerTime())){
			response.setMessage("您的商家服务已到期，请续期后再发起抽奖");
			return response;
		}
		LotteryBean lotteryBean = new LotteryBean();
		if(form.getId()==null){
			response.setMessage("请选择需要修改的抽奖");
			return response;
		}
		lotteryBean.setId(form.getId());
		if(form.getCount()==null){
			response.setMessage("请输入每个客户最多抽奖次数");
			return response;
		}
		if(Integer.valueOf(0).equals(form.getCount())){
			response.setMessage("每个客户最多抽奖次数必须大于0");
			return response;
		}
		lotteryBean.setCount(form.getCount());
		if(form.getLastExchangeTime()==null){
			response.setMessage("请选择最后兑奖时间");
			return response;
		}
		lotteryBean.setLastExchangeTime(DateTools.getDate(form.getLastExchangeTime()+" 23:59:59"));
		lotteryBean.setShare(form.getShare());
		lotteryBean.setSame(form.getSame());
		List<LotteryPrizeBean> lotteryPrizeBeanList = new ArrayList<LotteryPrizeBean>();
		Integer weight = 0;
		for(LotteryPrizeForm lotteryPrizeForm: form.getPrizeList()){
			LotteryPrizeBean lotteryPrizeBean = new LotteryPrizeBean();
			if(lotteryPrizeForm.getId()!=null){
				lotteryPrizeBean.setId(lotteryPrizeForm.getId());
			}
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
			lotteryPrizeBean.setStatus(LotteryStatusEnum.NORMAL.getCode());
			lotteryPrizeBean.setQuantity(lotteryPrizeForm.getQuantity());
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
		try {
			lotteryService.updateLottery(lotteryBean);
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
	 * 分页查询抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<LotteryListResult> queryLotteryPage(@ModelAttribute LotteryQueryForm form){
		Page<LotteryListResult> page = lotteryService.queryLotteryPage(form);
		for(LotteryListResult lotteryListResult: page.getData()){
			lotteryListResult.setPrizeList(LotteryPrizeBean.findAllByParams(LotteryPrizeBean.class, "lotteryId", lotteryListResult.getId()));
		}
		return page;
	}
	
	/**
	 * 结束抽奖
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/over/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> overLottery(@PathVariable Long id){
		Response<String> response = new Response<String>();
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, id);
		if(lotteryBean==null){
			response.setMessage("抽奖不存在或已删除");
			return response;
		}
		lotteryBean.setStatus(LotteryStatusEnum.OVER.getCode());
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
	 * 客户兑奖
	 * @param lotteryLeaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
	public Response<String> leaguerLotteryExchange(@RequestParam Long lotteryLeaguerId){
		Response<String> response = new Response<String>();
		if(lotteryLeaguerId==null){
			response.setMessage("请扫描兑奖码");
			return response;
		}
		LotteryLeaguerBean lotteryLeaguerBean = LotteryLeaguerBean.get(LotteryLeaguerBean.class, lotteryLeaguerId);
		if(lotteryLeaguerBean==null){
			response.setMessage("请扫描有效兑奖码");
			return response;
		}
		if(!lotteryLeaguerBean.getPrize()){
			response.setMessage("对不起，您没有中奖");
			return response;
		}
		LotteryLeaguerStatusEnum lotteryLeaguerStatusEnum = LotteryLeaguerStatusEnum.getLotteryLeaguerStatusEnumByCode(lotteryLeaguerBean.getStatus());
		if(!LotteryLeaguerStatusEnum.TOBEEXCHANGE.equals(lotteryLeaguerStatusEnum)){
			if(LotteryLeaguerStatusEnum.EXCHANGED.equals(lotteryLeaguerStatusEnum)){
				response.setMessage("奖品已被领取");
			}else if(LotteryLeaguerStatusEnum.EXPIRED.equals(lotteryLeaguerStatusEnum)){
				response.setMessage("对不起，奖品已过期");
			}
			return response;
		}
		LotteryPrizeBean lotteryPrizeBean = LotteryPrizeBean.get(LotteryPrizeBean.class, lotteryLeaguerBean.getLotteryPrizeId());
		if(lotteryPrizeBean==null){
			response.setMessage("对不起，奖品不存在或已删除");
			return response;
		}
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, lotteryPrizeBean.getLotteryId());
		if(lotteryBean==null){
			response.setMessage("对不起，抽奖不存在或已删除");
			return response;
		}
		if(new Date().after(lotteryBean.getLastExchangeTime())){
			response.setMessage("对不起，奖品已过期");
			return response;
		}
		if(lotteryBean.getShare() && !lotteryLeaguerBean.getShare()){
			response.setMessage("奖品尚未分享认证，无法领取");
			return response;
		}
		lotteryLeaguerBean.setStatus(LotteryLeaguerStatusEnum.EXCHANGED.getCode());
		lotteryLeaguerBean.setExchangeTime(new Date());
		try {
			lotteryService.saveLotteryLeaguer(lotteryLeaguerBean);
			response.setData(lotteryPrizeBean.getName());
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
	 * 分页查询中奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/page", method = RequestMethod.GET)
	public Page<LotteryLeaguerListResult> queryLotteryLeaguerPage(@ModelAttribute LotteryLeaguerQueryForm form){
		return lotteryService.queryLotteryLeaguerPage(form);
	}
}
