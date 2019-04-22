/**
 * 
 */
package com.cc.system.log.web;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.system.log.bean.OperationLogBean;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.form.LogQueryForm;
import com.cc.system.log.service.LogService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/log")
public class LogController {
	
	@Autowired
	private LogService logService;

	/**
	 * 查询操作日志模块
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.log" })
	@RequestMapping(value = "/module", method = RequestMethod.GET)
	public Response<Map<String, String>> queryOperationLogModule(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> moduleMap = ModuleEnum.getModuleMap();
		if (moduleMap==null || moduleMap.isEmpty()) {
			response.setMessage("暂无操作日志模块数据");
			return response;
		}
		response.setData(moduleMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询操作日志类型
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.log" })
	@RequestMapping(value = "/operType", method = RequestMethod.GET)
	public Response<Map<String, String>> queryOperationLogOperType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> operTypeMap = OperTypeEnum.getOperTypeMap();
		if (operTypeMap==null || operTypeMap.isEmpty()) {
			response.setMessage("暂无操作日志类型数据");
			return response;
		}
		response.setData(operTypeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询操作日志详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.log.detail" })
	@RequestMapping(value = "/get/{id:\\d+}")
	public Response<OperationLogBean> queryOperationLog(@PathVariable Long id){
		Response<OperationLogBean> response = new Response<OperationLogBean>();
		OperationLogBean operationLogBean = OperationLogBean.get(OperationLogBean.class, id);
		if (operationLogBean!=null) {
			response.setData(operationLogBean);
			response.setSuccess(Boolean.TRUE);
		}else {
			response.setMessage("操作日志不存在");
		}
		return response;
	}
	
	/**
	 * 分页查询操作日志
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.log" })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryOperationLogPage(@ModelAttribute LogQueryForm form){
		Page<Map<String, Object>> page = logService.queryOperationLogPage(form);
		return page;
	}

}
