/**
 * 
 */
package com.cc.integration.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Response;
import com.cc.integration.bean.IntegrationBean;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.enums.IntegrationTypeEnum;
import com.cc.integration.event.Event;
import com.cc.integration.event.EventFactory;
import com.cc.integration.service.IntegrationEventService;
import com.cc.integration.service.IntegrationService;
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
@RequestMapping("/integration")
public class IntegrationController {

	@Autowired
	private IntegrationService integrationService;
	
	@Autowired
	private IntegrationEventService integrationEventService;
	
	/**
	 * 查询积分值类型列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public Response<Map<String, String>> queryEventType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> integrationTypeMap = IntegrationTypeEnum.getIntegrationTypeMap();
		if (integrationTypeMap==null || integrationTypeMap.isEmpty()) {
			response.setMessage("暂无积分值类型数据");
			return response;
		}
		response.setData(integrationTypeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 为所有会员创建积分账户
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.create" })
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONMANAGEMENT, operType = OperTypeEnum.ADD, title = "初始化积分账户")
	public Response<String> createIntegrationBeans(){
		Response<String> response = new Response<String>();
		try {
			integrationService.createIntegrations();
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("创建积分账户异常");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 为会员创建积分账户
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.create" })
	@RequestMapping(value = "/create/{leaguerId}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONMANAGEMENT, operType = OperTypeEnum.ADD, title = "初始化积分账户", paramNames = {"leaguerId"})
	public Response<String> createIntegrationBean(@PathVariable Long leaguerId){
		Response<String> response = new Response<String>();
		try {
			integrationService.createIntegration(leaguerId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("创建积分账户异常");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 获取会员积分
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{leaguerId}", method = RequestMethod.GET)
	public Response<IntegrationBean> queryIntegration(@PathVariable Long leaguerId){
		Response<IntegrationBean> response = new Response<IntegrationBean>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				response.setMessage("您还未注册");
				return response;
			}
			leaguerId = leaguerBeanList.get(0).getId();
		}
		List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByParams(IntegrationBean.class, "leaguerId", leaguerId);
		if (ListTools.isEmptyOrNull(integrationBeanList)) {
			response.setMessage("尚未创建积分账户");
			return response;
		}
		response.setData(integrationBeanList.get(0));
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 积分充值
	 * @param leaguerId
	 * @param integration
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.deposit" })
	@RequestMapping(value = "/deposit/{leaguerId}/{integration}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONMANAGEMENT, operType = OperTypeEnum.DEPOSIT, title = "积分充值", paramNames = {"leaguerId", "deposit"})
	public Response<IntegrationBean> deposit(@PathVariable Long leaguerId, @PathVariable Long integration){
		Response<IntegrationBean> response = new Response<IntegrationBean>();
		try {
			List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.DEPOSIT.getCode());
			if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
				response.setMessage("系统拒绝充值");
				return response;
			}
			if (integrationEventBeanList.size()>1) {
				response.setMessage("充值事件不唯一");
				return response;
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event==null) {
				response.setMessage("系统拒绝充值");
				return response;
			}
			event.setLeaguerId(leaguerId);
			event.setData(integration);
			event.happened();
			List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByParams(IntegrationBean.class, "leaguerId", leaguerId);
			response.setData(integrationBeanList.get(0));
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("充值积分异常");
			e.printStackTrace();
		}
		return response;
	}
}
