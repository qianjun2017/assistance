/**
 * 
 */
package com.cc.exchange.web;

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
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.enums.ExchangeStatusEnum;
import com.cc.exchange.form.ExchangeQueryForm;
import com.cc.exchange.result.ExchangeResult;
import com.cc.exchange.service.ExchangeService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/exchange")
public class ExchangeController {
	
	@Autowired
	private ExchangeService exchangeService;

	/**
	 * 分页查询兑换记录
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<ExchangeResult> queryExchangePage(@ModelAttribute ExchangeQueryForm form){
		Page<ExchangeResult> page = new Page<ExchangeResult>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			page.setMessage("请先登录");
			return page;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				page.setMessage("查询兑换记录失败");
				return page;
			}
			form.setLeaguerId(leaguerBeanList.get(0).getId());
		}
		return exchangeService.queryExchangePage(form);
	}
	
	/**
	 * 取消兑换
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "exchange.cancel" })
	@RequestMapping(value = "/cancel/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.EXCHANGEMANAGEMENT, operType = OperTypeEnum.CANCEL, title = "取消交易", paramNames = {"id"})
	public Response<String> cancelExchange(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			exchangeService.cancelExchange(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("取消兑换异常");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 审批取消兑换申请
	 * @param auditMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "exchange.audit" })
	@RequestMapping(value = "/request/audit", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.EXCHANGEMANAGEMENT, operType = OperTypeEnum.AUDIT, title = "审批取消交易")
	public Response<String> rejectCancelRequestExchange(@RequestBody Map<String, String> auditMap){
		Response<String> response = new Response<String>();
		String id = auditMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少兑换主键");
			return response;
		}
		ExchangeBean exchangeBean = ExchangeBean.get(ExchangeBean.class, Long.valueOf(id));
		if(exchangeBean==null){
			response.setMessage("兑换信息不存在");
			return response;
		}
		String status = auditMap.get("status");
		if (StringTools.isNullOrNone(status)) {
			response.setMessage("请选择审核状态");
			return response;
		}
		exchangeBean.setStatus(status);
		try {
			ExchangeStatusEnum exchangeStatusEnum = ExchangeStatusEnum.getExchangeStatusEnumByCode(status);
			String remark = auditMap.get("remark");
			if (ExchangeStatusEnum.REJECT.equals(exchangeStatusEnum) && StringTools.isNullOrNone(remark)) {
				response.setMessage("请输入审批说明");
				return response;
			}
			exchangeBean.setRemark(remark);
			exchangeService.auditCancelExchange(exchangeBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("审批取消兑换异常");
			e.printStackTrace();
		}
		return response;
	}
}
