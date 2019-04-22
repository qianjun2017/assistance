/**
 * 
 */
package com.cc.integration.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventStatusEnum;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.enums.IntegrationTypeEnum;
import com.cc.integration.form.IntegrationEventQueryForm;
import com.cc.integration.service.IntegrationEventService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/integration/event")
public class IntegrationEventController {
	
	private static Logger logger = LoggerFactory.getLogger(IntegrationEventController.class);

	@Autowired
	private IntegrationEventService integrationEventService;
	
	/**
	 * 查询积分事件类型列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/eventType", method = RequestMethod.GET)
	public Response<Map<String, String>> queryEventType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> integrationEventTypeMap = IntegrationEventTypeEnum.getIntegrationEventTypeMap();
		if (integrationEventTypeMap==null || integrationEventTypeMap.isEmpty()) {
			response.setMessage("暂无积分事件类型数据");
			return response;
		}
		response.setData(integrationEventTypeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 新增积分事件
	 * @param eventMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.event.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONEVENTMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增积分事件")
	public Response<String> addEvent(@RequestBody Map<String, String> eventMap){
		Response<String> response = new Response<String>();
		IntegrationEventBean integrationEventBean = new IntegrationEventBean();
		String eventType = eventMap.get("eventType");
		if (StringTools.isNullOrNone(eventType)) {
			response.setMessage("请选择积分事件类型");
			return response;
		}
		IntegrationEventTypeEnum integrationEventTypeEnum = null;
		try {
			integrationEventTypeEnum = IntegrationEventTypeEnum.getIntegrationEventTypeEnumByCode(eventType);
			if (integrationEventTypeEnum==null) {
				response.setMessage("积分事件类型不合法");
				return response;
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		}
		integrationEventBean.setEventType(eventType);
		String integrationType = eventMap.get("integrationType");
		if (StringTools.isNullOrNone(integrationType)) {
			response.setMessage("请选择积分值类型");
			return response;
		}
		try {
			IntegrationTypeEnum integrationTypeEnum = IntegrationTypeEnum.getIntegrationTypeEnumByCode(integrationType);
			if (integrationTypeEnum==null) {
				response.setMessage("积分值类型不合法");
				return response;
			}
			if ((IntegrationEventTypeEnum.CHECKIN.equals(integrationEventTypeEnum) 
					|| IntegrationEventTypeEnum.REGISTER.equals(integrationEventTypeEnum)
					|| IntegrationEventTypeEnum.LOGIN.equals(integrationEventTypeEnum)) 
					&& !IntegrationTypeEnum.FIXED.equals(integrationTypeEnum)) {
				response.setMessage("积分事件类型为"+integrationEventTypeEnum.getName()+"时，积分值类型必须为"+IntegrationTypeEnum.FIXED.getName());
				return response;
			}
			integrationEventBean.setIntegrationType(integrationType);
			String integration = eventMap.get("integration");
			if (StringTools.isNullOrNone(integration)) {
				if(!integrationTypeEnum.equals(IntegrationTypeEnum.EXTERFIXED)){
					response.setMessage("请输入积分值参数");
					return response;
				}
			}else{
				Float parseFloat = Float.parseFloat(integration);
				integrationEventBean.setIntegration(parseFloat);
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		}
		integrationEventBean.setStatus(IntegrationEventStatusEnum.ON.getCode());
		try {
			integrationEventService.saveIntegrationEvent(integrationEventBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			String msg = "保存积分事件异常";
			logger.error(msg);
			response.setMessage(msg);
		}
		return response;
	}
	
	/**
	 * 修改积分事件
	 * @param eventMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.event.edit" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONEVENTMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改积分事件")
	public Response<String> updateEvent(@RequestBody Map<String, String> eventMap){
		Response<String> response = new Response<String>();
		String id = eventMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少事件主键");
			return response;
		}
		IntegrationEventBean integrationEventBean = IntegrationEventBean.get(IntegrationEventBean.class, Long.valueOf(id));
		String eventType = eventMap.get("eventType");
		if (StringTools.isNullOrNone(eventType)) {
			response.setMessage("请选择积分事件类型");
			return response;
		}
		IntegrationEventTypeEnum integrationEventTypeEnum = null;
		try {
			integrationEventTypeEnum = IntegrationEventTypeEnum.getIntegrationEventTypeEnumByCode(eventType);
			if (integrationEventTypeEnum==null) {
				response.setMessage("积分事件类型不合法");
				return response;
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		}
		integrationEventBean.setEventType(eventType);
		String integrationType = eventMap.get("integrationType");
		if (StringTools.isNullOrNone(integrationType)) {
			response.setMessage("请选择积分值类型");
			return response;
		}
		try {
			IntegrationTypeEnum integrationTypeEnum = IntegrationTypeEnum.getIntegrationTypeEnumByCode(integrationType);
			if (integrationTypeEnum==null) {
				response.setMessage("积分值类型不合法");
				return response;
			}
			if ((IntegrationEventTypeEnum.CHECKIN.equals(integrationEventTypeEnum) 
					|| IntegrationEventTypeEnum.REGISTER.equals(integrationEventTypeEnum)
					|| IntegrationEventTypeEnum.LOGIN.equals(integrationEventTypeEnum)) 
					&& !IntegrationTypeEnum.FIXED.equals(integrationTypeEnum)) {
				response.setMessage("积分事件类型为"+integrationEventTypeEnum.getName()+"时，积分值类型必须为"+IntegrationTypeEnum.FIXED.getName());
				return response;
			}
			integrationEventBean.setIntegrationType(integrationType);
			String integration = eventMap.get("integration");
			if (StringTools.isNullOrNone(integration)) {
				if(!integrationTypeEnum.equals(IntegrationTypeEnum.EXTERFIXED)){
					response.setMessage("请输入积分值参数");
					return response;
				}
			}else{
				Float parseFloat = Float.parseFloat(integration);
				integrationEventBean.setIntegration(parseFloat);
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		}
		try {
			integrationEventService.saveIntegrationEvent(integrationEventBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			String msg = "保存积分事件异常";
			logger.error(msg);
			response.setMessage(msg);
		}
		return response;
	}
	
	/**
	 * 删除积分事件
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.event.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONEVENTMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除积分事件", paramNames = {"id"})
	public Response<String> deleteEvent(@PathVariable Long id){
		Response<String> response = new Response<String>();
		IntegrationEventBean integrationEventBean = IntegrationEventBean.get(IntegrationEventBean.class, id);
		if (integrationEventBean == null) {
			response.setMessage("积分事件不存在");
			return response;
		}
		try {
			integrationEventService.deleteIntegrationEvent(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("删除积分事件异常");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询积分事件
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryIntegrationEventPage(@ModelAttribute IntegrationEventQueryForm form) {
		Page<Map<String, Object>> page = integrationEventService.queryIntegrationEventPage(form);
		return page;
	}
	
	/**
	 * 启用积分事件
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.event.on" })
	@RequestMapping(value = "/on/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONEVENTMANAGEMENT, operType = OperTypeEnum.ON, title = "启用积分事件", paramNames = {"id"})
	public Response<String> onIntegrationEvent(@PathVariable Long id){
		Response<String> response = new Response<String>();
		IntegrationEventBean integrationEventBean = IntegrationEventBean.get(IntegrationEventBean.class, id);
		if (integrationEventBean == null) {
			response.setMessage("积分事件不存在");
			return response;
		}
		try {
			integrationEventBean.setStatus(IntegrationEventStatusEnum.ON.getCode());
			integrationEventService.saveIntegrationEvent(integrationEventBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("启用积分事件异常");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 下线积分事件
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "integration.event.off" })
	@RequestMapping(value = "/off/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.INTEGRATIONEVENTMANAGEMENT, operType = OperTypeEnum.ON, title = "下线积分事件", paramNames = {"id"})
	public Response<String> offIntegrationEvent(@PathVariable Long id){
		Response<String> response = new Response<String>();
		IntegrationEventBean integrationEventBean = IntegrationEventBean.get(IntegrationEventBean.class, id);
		if (integrationEventBean == null) {
			response.setMessage("积分事件不存在");
			return response;
		}
		try {
			integrationEventBean.setStatus(IntegrationEventStatusEnum.OFF.getCode());
			integrationEventService.saveIntegrationEvent(integrationEventBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("下线积分事件异常");
			e.printStackTrace();
		}
		return response;
	}
	
}
