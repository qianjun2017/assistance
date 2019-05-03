/**
 * 
 */
package com.cc.lottery.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryLeaguerBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryLeaguerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryLeaguerListResult;
import com.cc.lottery.result.LotteryListResult;
import com.cc.lottery.service.LotteryService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

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
	 * 查询抽奖详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "lottery.detail" })
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
	 * 分页查询抽奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "lottery" })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<LotteryListResult> queryLotteryPage(@ModelAttribute LotteryQueryForm form){
		return lotteryService.queryLotteryPage(form);
	}
	
	/**
	 * 分页查询中奖
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "lottery.detail" })
	@RequestMapping(value = "/leaguer/page", method = RequestMethod.GET)
	public Page<LotteryLeaguerListResult> queryLotteryLeaguerPage(@ModelAttribute LotteryLeaguerQueryForm form){
		form.setPrize(Boolean.TRUE);
		return lotteryService.queryLotteryLeaguerPage(form);
	}
	
	/**
	 * 查询中奖详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "lottery.detail" })
	@RequestMapping(value = "/leaguer/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<LotteryLeaguerBean> queryLotteryLeaguer(@PathVariable Long id){
		Response<LotteryLeaguerBean> response = new Response<LotteryLeaguerBean>();
		LotteryLeaguerBean lotteryLeaguerBean = LotteryLeaguerBean.get(LotteryLeaguerBean.class, id);
		if (lotteryLeaguerBean==null) {
			response.setMessage("中奖不存在或已删除");
			return response;
		}
		response.setData(lotteryLeaguerBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 结束抽奖
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "lottery.over" })
	@RequestMapping(value = "/over/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOTTERYMANAGEMENT, operType = OperTypeEnum.OVER, title = "结束抽奖")
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
	
}
