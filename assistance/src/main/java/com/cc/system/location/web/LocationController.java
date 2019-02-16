/**
 * 
 */
package com.cc.system.location.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.common.web.Tree;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.location.service.LocationService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/location")
public class LocationController {
	
	private static Logger logger = LoggerFactory.getLogger(LocationController.class);
	
	@Autowired
	private LocationService locationService;

	/**
	 * 新增地区
	 * @param locationMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.location.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOCATIONMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增地区")
	public Response<String> addLocation(@RequestBody Map<String, String> locationMap){
		Response<String> response = new Response<String>();
		LocationBean locationBean = new LocationBean();
		String locationName = locationMap.get("locationName");
		if (StringTools.isNullOrNone(locationName)) {
			response.setMessage("请输入地区名称");
			return response;
		}
		locationBean.setLocationName(locationName);
		String locationCode = locationMap.get("locationCode");
		if (StringTools.isNullOrNone(locationCode)) {
			response.setMessage("请输入地区编码");
			return response;
		}
		locationBean.setLocationCode(locationCode);
		String parentId = locationMap.get("parentId");
		if (!StringTools.isNullOrNone(parentId)) {
			LocationBean parentLocationBean = LocationBean.get(LocationBean.class, Long.valueOf(parentId));
			if (parentLocationBean==null) {
				response.setMessage("父地区不存在,请重新选择");
				return response;
			}
			locationBean.setParentId(Long.valueOf(parentId));
			locationBean.setLevel(parentLocationBean.getLevel()+1);
			locationBean.setLocationNamePath(parentLocationBean.getLocationNamePath()+"/"+locationBean.getLocationName());
			locationBean.setLocationCodePath(parentLocationBean.getLocationCodePath()+"/"+locationBean.getLocationCode());
		}else {
			locationBean.setLevel(0);
			locationBean.setLocationNamePath(locationBean.getLocationName());
			locationBean.setLocationCodePath(locationBean.getLocationCode());
		}
		locationBean.setCreateTime(DateTools.now());
		try {
			locationService.saveLocation(locationBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	@ResponseBody
	@RequiresPermissions(value = { "system.location.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOCATIONMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改地区")
	public Response<String> updateLocation(@RequestBody Map<String, String> locationMap){
		Response<String> response = new Response<String>();
		String id = locationMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少地区主键");
			return response;
		}
		LocationBean locationBean = LocationBean.get(LocationBean.class, id);
		if (locationBean==null) {
			response.setMessage("地区不存在");
			return response;
		}
		String locationName = locationMap.get("locationName");
		if (StringTools.isNullOrNone(locationName)) {
			response.setMessage("请输入地区名称");
			return response;
		}
		locationBean.setLocationName(locationName);
		String locationCode = locationMap.get("locationCode");
		if (StringTools.isNullOrNone(locationCode)) {
			response.setMessage("请输入地区编码");
			return response;
		}
		locationBean.setLocationCode(locationCode);
		String parentId = locationMap.get("parentId");
		if (!StringTools.isNullOrNone(parentId)) {
			LocationBean parentLocationBean = LocationBean.get(LocationBean.class, Long.valueOf(parentId));
			if (parentLocationBean==null) {
				response.setMessage("父地区不存在,请重新选择");
				return response;
			}
			locationBean.setParentId(Long.valueOf(parentId));
			locationBean.setLevel(parentLocationBean.getLevel()+1);
			locationBean.setLocationNamePath(parentLocationBean.getLocationNamePath()+"/"+locationBean.getLocationName());
			locationBean.setLocationCodePath(parentLocationBean.getLocationCodePath()+"/"+locationBean.getLocationCode());
		}else {
			locationBean.setLevel(0);
			locationBean.setLocationNamePath(locationBean.getLocationName());
			locationBean.setLocationCodePath(locationBean.getLocationCode());
		}
		try {
			locationService.saveLocation(locationBean);
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
	 * 删除地区
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.location.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LOCATIONMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除地区", paramNames = {"id"})
	public Response<String> deleteLocation(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			locationService.deleteLocation(id);
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
	 * 查询地区信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryLocation(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		Map<String, Object> locationMap = new HashMap<String, Object>();
		try {
			LocationBean locationBean = LocationBean.get(LocationBean.class, id);
			if (locationBean==null) {
				response.setMessage("地区不存在");
				return response;
			}
			locationMap.put("id", locationBean.getId());
			locationMap.put("locationCode", locationBean.getLocationCode());
			locationMap.put("locationName", locationBean.getLocationName());
			locationMap.put("locationPath", locationBean.getLocationNamePath());
			locationMap.put("level", locationBean.getLevel());
			locationMap.put("createTime", DateTools.getFormatDate(locationBean.getCreateTime(), DateTools.DATEFORMAT));
			Long parentId = locationBean.getParentId();
			while (parentId!=null) {
				LocationBean parentLocationBean = LocationBean.get(LocationBean.class, Long.valueOf(parentId));
				if (parentLocationBean!=null) {
					locationMap.put("parent"+parentLocationBean.getLevel(), parentLocationBean.getId());
					parentId = parentLocationBean.getParentId();
				}else {
					logger.error("查询地区详情时，找不到父地区[id:"+parentId+"]");
					parentId = null;
				}
			}
			response.setData(locationMap);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
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
