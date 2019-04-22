/**
 * 
 */
package com.cc.integration.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Page;
import com.cc.integration.form.IntegrationOperationQueryForm;
import com.cc.integration.service.IntegrationOperationService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/integration/operation")
public class IntegrationOperationController {

	@Autowired
	private IntegrationOperationService integrationOperationService;
	
	/**
	 * 分页查询积分日志
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryIntegrationOperationPage(@ModelAttribute IntegrationOperationQueryForm form){
		return integrationOperationService.queryIntegrationOperationPage(form);
	}
}
