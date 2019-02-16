/**
 * 
 */
package com.cc.system.location.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Tree;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.location.result.LocationResult;
import com.cc.system.location.service.LocationService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 *
 */
@Service
public class LocationServiceImpl implements LocationService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveLocation(LocationBean locationBean) {
		int row = locationBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存地区失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteLocation(Long id) {
		LocationBean locationBean = new LocationBean();
		locationBean.setId(id);
		int row = locationBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除地区失败");
		}
	}

	@Override
	public Tree<Map<String, Object>> queryLocationTree() {
		Tree<Map<String, Object>> tree = new Tree<Map<String,Object>>();
		List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class);
		if (ListTools.isEmptyOrNull(locationBeanList)) {
			tree.setMessage("没有地区数据");
			return tree;
		}
		List<LocationBean> topNodeList = locationBeanList.stream().filter(locationBean->(locationBean.getParentId()==null)).collect(Collectors.toList());
		if (ListTools.isEmptyOrNull(topNodeList)) {
			tree.setMessage("没有找到顶级地区数据");
			return tree;
		}
		List<Map<String, Object>> topLocationMapList = new ArrayList<Map<String,Object>>();
		for (LocationBean locationBean : topNodeList) {
			topLocationMapList.add(addSubLocationBean(locationBean, locationBeanList));
		}
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("id", -1);
		root.put("locationCode", "-1");
		root.put("locationName", "地区树");
		root.put("subLocationList", topLocationMapList);
		tree.setRoot(root);
		int level = -1;
		for (LocationBean locationBean : locationBeanList) {
			if (level<locationBean.getLevel()) {
				level = locationBean.getLevel();
			}
		}
		tree.setLevel(level);
		tree.setSuccess(Boolean.TRUE);
		return tree;
	}

	/**
	 * 添加子地区
	 * @param locationBean
	 * @param locationBeanList
	 * @return
	 */
	private Map<String, Object> addSubLocationBean(LocationBean locationBean, List<LocationBean> locationBeanList) {
		Map<String, Object> locationMap = new HashMap<String, Object>();
		locationMap.put("id", locationBean.getId());
		locationMap.put("locationCode", locationBean.getLocationCode());
		locationMap.put("locationName", locationBean.getLocationName());
		locationMap.put("level", locationBean.getLevel());
		if(!ListTools.isEmptyOrNull(locationBeanList)){
			List<LocationBean> subLocationBeanList = locationBeanList.stream().filter(subLocationBean->locationBean.getId().equals(subLocationBean.getParentId())).collect(Collectors.toList());
			if (!ListTools.isEmptyOrNull(subLocationBeanList)) {
				List<Map<String, Object>> subLocationMapList = new ArrayList<Map<String,Object>>();
				for (LocationBean subLocationBean : subLocationBeanList) {
					subLocationMapList.add(addSubLocationBean(subLocationBean, locationBeanList));
				}
				locationMap.put("subLocationList", subLocationMapList);
			}
		}
		return locationMap;
	}

	@Override
	public LocationResult queryLocation(String country, String province, String city) {
		LocationResult addressResult = new LocationResult();
		if(StringTools.isNullOrNone(country)){
			return addressResult;
		}
		List<LocationBean> countryLocationBeanList = LocationBean.findAllByParams(LocationBean.class, "level", 0, "locationCode", country);
		if(ListTools.isEmptyOrNull(countryLocationBeanList)){
			addressResult.setCountry(country);
			addressResult.setProvince(province);
			addressResult.setCity(city);
			return addressResult;
		}
		LocationBean countryLocationBean = countryLocationBeanList.get(0);
		addressResult.setCountry(countryLocationBean.getLocationName());
		if(StringTools.isNullOrNone(province)){
			return addressResult;
		}
		List<LocationBean> provinceLocationBeanList = LocationBean.findAllByParams(LocationBean.class, "parentId", countryLocationBean.getId(), "locationCode", province);
		if(ListTools.isEmptyOrNull(provinceLocationBeanList)){
			addressResult.setProvince(province);
			addressResult.setCity(city);
			return addressResult;
		}
		LocationBean provinceLocationBean = provinceLocationBeanList.get(0);
		addressResult.setProvince(provinceLocationBean.getLocationName());
		if(StringTools.isNullOrNone(city)){
			return addressResult;
		}
		List<LocationBean> cityLocationBeanList = LocationBean.findAllByParams(LocationBean.class, "parentId", provinceLocationBean.getId(), "locationCode", city);
		if(ListTools.isEmptyOrNull(cityLocationBeanList)){
			addressResult.setCity(city);
			return addressResult;
		}
		LocationBean cityLocationBean = cityLocationBeanList.get(0);
		addressResult.setCity(cityLocationBean.getLocationName());
		return addressResult;
	}

	@Override
	public LocationResult queryLocation(Long locationId) {
		LocationResult addressResult = new LocationResult();
		LocationBean locationBean = LocationBean.get(LocationBean.class, locationId);
		if(locationBean!=null){
			switch (locationBean.getLevel()) {
				
				default:
					int level = locationBean.getLevel();
					while(level>2){
						locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
						level = locationBean.getLevel();
					}
					
				case 2:
					addressResult.setCity(locationBean.getLocationName());
					locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
					
				case 1:
					addressResult.setProvince(locationBean.getLocationName());
					locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
					
				case 0:
					addressResult.setCountry(locationBean.getLocationName());
					break;
				
			}
		}
		return addressResult;
	}

	@Override
	public Boolean hasLocation(String country, String province, String city, String area) {
		List<String> locationList = new ArrayList<String>();
		if(!StringTools.isNullOrNone(country)){
			locationList.add(country);
			if(!StringTools.isNullOrNone(province)){
				locationList.add(province);
				if(!StringTools.isNullOrNone(city)){
					locationList.add(city);
					if(!StringTools.isNullOrNone(area)){
						locationList.add(area);
					}
				}
			}
		}
		if(ListTools.isEmptyOrNull(locationList)){
			return Boolean.FALSE;
		}
		StringBuffer buffer = new StringBuffer();
		for(String location: locationList){
			buffer.append("/").append(location);
		}
		String locationCodePath = buffer.substring(1);
		List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class, "locationCodePath", locationCodePath);
		if(ListTools.isEmptyOrNull(locationBeanList)){
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	public Boolean hasLocation(String country, String province, String city) {
		return hasLocation(country, province, city, null);
	}

	@Override
	public Boolean hasLocation(String country, String province) {
		return hasLocation(country, province, null);
	}

	@Override
	public Boolean hasLocation(String country) {
		return hasLocation(country, null);
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public Long saveLocation(String country, String province, String city, String area) {
		if(StringTools.isNullOrNone(area)){
			return saveLocation(country, province, city);
		}
		if(hasLocation(country, province, city, area)){
			List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class, "locationCodePath", country+"/"+province+"/"+city+"/"+area);
			return locationBeanList.get(0).getId();
		}else{
			Long parentId = saveLocation(country, province, city);
			LocationBean parentLocationBean = LocationBean.get(LocationBean.class, parentId);
			LocationBean locationBean = new LocationBean();
			locationBean.setLevel(parentLocationBean.getLevel()+1);
			locationBean.setLocationCode(area);
			locationBean.setLocationCodePath(parentLocationBean.getLocationCodePath()+"/"+area);
			locationBean.setLocationName(area);
			locationBean.setLocationNamePath(parentLocationBean.getLocationNamePath()+"/"+area);
			locationBean.setParentId(parentId);
			saveLocation(locationBean);
			return locationBean.getId();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public Long saveLocation(String country, String province, String city) {
		if(StringTools.isNullOrNone(city)){
			return saveLocation(country, province);
		}
		if(hasLocation(country, province, city)){
			List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class, "locationCodePath", country+"/"+province+"/"+city);
			return locationBeanList.get(0).getId();
		}else{
			Long parentId = saveLocation(country, province);
			LocationBean parentLocationBean = LocationBean.get(LocationBean.class, parentId);
			LocationBean locationBean = new LocationBean();
			locationBean.setLevel(parentLocationBean.getLevel()+1);
			locationBean.setLocationCode(city);
			locationBean.setLocationCodePath(parentLocationBean.getLocationCodePath()+"/"+city);
			locationBean.setLocationName(city);
			locationBean.setLocationNamePath(parentLocationBean.getLocationNamePath()+"/"+city);
			locationBean.setParentId(parentId);
			saveLocation(locationBean);
			return locationBean.getId();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public Long saveLocation(String country, String province) {
		if(StringTools.isNullOrNone(province)){
			return saveLocation(country);
		}
		if(hasLocation(country, province)){
			List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class, "locationCodePath", country+"/"+province);
			return locationBeanList.get(0).getId();
		}else{
			Long parentId = saveLocation(country);
			LocationBean parentLocationBean = LocationBean.get(LocationBean.class, parentId);
			LocationBean locationBean = new LocationBean();
			locationBean.setLevel(parentLocationBean.getLevel()+1);
			locationBean.setLocationCode(province);
			locationBean.setLocationCodePath(parentLocationBean.getLocationCodePath()+"/"+province);
			locationBean.setLocationName(province);
			locationBean.setLocationNamePath(parentLocationBean.getLocationNamePath()+"/"+province);
			locationBean.setParentId(parentId);
			saveLocation(locationBean);
			return locationBean.getId();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public Long saveLocation(String country) {
		if(StringTools.isNullOrNone(country)){
			return null;
		}
		if(hasLocation(country)){
			List<LocationBean> locationBeanList = LocationBean.findAllByParams(LocationBean.class, "locationCode", country, "level", 0);
			return locationBeanList.get(0).getId();
		}else{
			LocationBean locationBean = new LocationBean();
			locationBean.setLevel(0);
			locationBean.setLocationCode(country);
			locationBean.setLocationCodePath(country);
			locationBean.setLocationName(country);
			locationBean.setLocationNamePath(country);
			saveLocation(locationBean);
			return locationBean.getId();
		}
	}

}
