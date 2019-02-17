/**
 * 
 */
package com.cc.interfaces.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.interfaces.enums.InterfaceEnum;
import com.cc.interfaces.form.InterfaceQueryForm;
import com.cc.interfaces.service.InterfaceService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/interface")
public class InterfaceController {
	
	@Autowired
	private InterfaceService interfaceService;

	/**
	 * 查询接口
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/code", method = RequestMethod.GET)
	public Response<Map<String, String>> queryEventType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> interfaceMap = InterfaceEnum.getInterfaceMap();
		if (interfaceMap==null || interfaceMap.isEmpty()) {
			response.setMessage("暂无接口数据");
			return response;
		}
		response.setData(interfaceMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询接口调用
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryInterfacePage(@ModelAttribute InterfaceQueryForm form){
		return interfaceService.queryInterfacePage(form);
	}
}
