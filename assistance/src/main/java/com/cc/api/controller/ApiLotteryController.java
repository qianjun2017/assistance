package com.cc.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.LeaguerLotteryForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryLeaguerBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.enums.LotteryLeaguerStatusEnum;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryLeaguerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryLeaguerListResult;
import com.cc.lottery.service.LotteryService;

@Controller
@RequestMapping("/api/lottery")
public class ApiLotteryController {

	@Autowired
	private LotteryService lotteryService;
	
	/**
	 * 获取抽奖信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Response<LotteryBean> queryLottery(@ModelAttribute LotteryQueryForm form){
		Response<LotteryBean> response = new Response<LotteryBean>();
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, form.getLotteryId());
		if (lotteryBean==null) {
			response.setMessage("抽奖不存在或已删除");
			return response;
		}
		lotteryBean.setPrizeList(LotteryPrizeBean.findAllByParams(LotteryPrizeBean.class, "lotteryId", lotteryBean.getId(), "status", LotteryStatusEnum.NORMAL.getCode()));
		Integer count = lotteryBean.getCount();
		if(form.getLeaguerId()!=null){
			count -= lotteryService.queryLotteryLeaguerCount(form.getLeaguerId(), lotteryBean.getId());
		}
		lotteryBean.setCount(count);
		response.setData(lotteryBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 客户抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/leaguer", method = RequestMethod.POST)
	public Response<Object> leaguerLottery(@RequestBody LeaguerLotteryForm form){
		Response<Object> response = new Response<Object>();
		if(form.getLotteryId()==null){
			response.setMessage("请选择抽奖");
			response.setData(404);
			return response;
		}
		LotteryBean lotteryBean = LotteryBean.get(LotteryBean.class, form.getLotteryId());
		if(lotteryBean==null){
			response.setMessage("抽奖不存在或已删除");
			response.setData(404);
			return response;
		}
		if(LotteryStatusEnum.OVER.equals(LotteryStatusEnum.getLotteryStatusEnumByCode(lotteryBean.getStatus()))){
			response.setMessage("抽奖已结束");
			response.setData(404);
			return response;
		}
		synchronized (this) {
			int lotteryLeaguerCount = lotteryService.queryLotteryLeaguerCount(form.getLeaguerId(), form.getLotteryId());
			if(lotteryBean.getShare() && lotteryLeaguerCount==0){
				if(form.getShareId()!=null){
					LotteryLeaguerBean lotteryLeaguerBean = LotteryLeaguerBean.get(LotteryLeaguerBean.class, form.getShareId());
					lotteryLeaguerBean.setShare(Boolean.TRUE);
					try {
						lotteryService.saveLotteryLeaguer(lotteryLeaguerBean);
					} catch (LogicException e) {
						response.setMessage(e.getErrContent());
						return response;
					} catch (Exception e) {
						response.setMessage("系统内部错误");
						e.printStackTrace();
						return response;
					}
				}
			}
			if(lotteryLeaguerCount>=lotteryBean.getCount()){
				response.setMessage("您的抽奖次数已用完");
				response.setData(405);
				return response;
			}
			List<LotteryPrizeBean> lotteryPrizeBeanList = LotteryPrizeBean.findAllByParams(LotteryPrizeBean.class, "lotteryId", form.getLotteryId(), "status", LotteryStatusEnum.NORMAL.getCode());
			if(ListTools.isEmptyOrNull(lotteryPrizeBeanList)){
				response.setMessage("奖品已抽完");
				response.setData(406);
				return response;
			}
			List<Long> prizeList = new ArrayList<Long>();
			for(LotteryPrizeBean lotteryPrizeBean: lotteryPrizeBeanList){
				if(lotteryPrizeBean.getTotal()>lotteryPrizeBean.getQuantity()){
					Integer weight = 0;
					while(weight<lotteryPrizeBean.getWeight()){
						prizeList.add(lotteryPrizeBean.getId());
						weight ++;
					}
				}
			}
			Random random = new Random();
			int index = random.nextInt(10000);
			LotteryLeaguerBean lotteryLeaguerBean = new LotteryLeaguerBean();
			lotteryLeaguerBean.setLotteryId(form.getLotteryId());
			if(index>prizeList.size()){
				response.setMessage("很遗憾，您未中奖");
				lotteryLeaguerBean.setPrize(Boolean.FALSE);
				response.setData(407);
			}else{
				Long lotteryPrizeId = prizeList.get(index);
				if(!lotteryBean.getSame() && !ListTools.isEmptyOrNull(LotteryLeaguerBean.findAllByParams(LotteryLeaguerBean.class, "leaguerId", form.getLeaguerId(), "lotteryId", form.getLotteryId(), "lotteryPrizeId", lotteryPrizeId))){
					response.setMessage("很遗憾，您未中奖");
					lotteryLeaguerBean.setPrize(Boolean.FALSE);
					response.setData(407);
				}else{
					lotteryLeaguerBean.setPrize(Boolean.TRUE);
					lotteryLeaguerBean.setLotteryPrizeId(lotteryPrizeId);
					lotteryLeaguerBean.setShare(Boolean.FALSE);
					lotteryLeaguerBean.setStatus(LotteryLeaguerStatusEnum.TOBEEXCHANGE.getCode());
					LotteryPrizeBean lotteryPrizeBean = LotteryPrizeBean.get(LotteryPrizeBean.class, lotteryPrizeId);
					lotteryPrizeBean.setQuantity(lotteryPrizeBean.getQuantity()+1);
					lotteryPrizeBean.save();
					response.setData(lotteryPrizeBean);
				}
			}
			lotteryLeaguerBean.setLeaguerId(form.getLeaguerId());
			lotteryLeaguerBean.setCreateTime(DateTools.now());
			try {
				lotteryService.saveLotteryLeaguer(lotteryLeaguerBean);
				response.setSuccess(Boolean.TRUE);
			} catch (LogicException e) {
				response.setMessage(e.getErrContent());
			} catch (Exception e) {
				response.setMessage("系统内部错误");
				e.printStackTrace();
			}
		}
		return response;
	}
	
	/**
	 * 分页查询中奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<LotteryLeaguerListResult> queryLotteryLeaguerPage(@ModelAttribute LotteryLeaguerQueryForm form){
		form.setPrize(Boolean.TRUE);
		return lotteryService.queryLotteryLeaguerPage(form);
	}
}
