/**
 * 
 */
package com.cc.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.web.Tree;
import com.cc.system.location.service.LocationService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/location")
public class ApiLocationController {

	@Autowired
	private LocationService locationService;
	
	/**
	 * 查询地区树
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tree", method = RequestMethod.GET)
	public Tree<Map<String, Object>> queryLocationTree(){
		Tree<Map<String, Object>> tree = locationService.queryLocationTree();
		return tree;
	}
}
