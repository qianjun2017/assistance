package com.cc.map.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.exception.LogicException;
import com.cc.common.http.service.HttpService;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.MD5Tools;
import com.cc.common.tools.StringTools;
import com.cc.common.tools.XmlTools;
import com.cc.interfaces.enums.InterfaceEnum;
import com.cc.interfaces.service.InterfaceService;
import com.cc.map.enums.ErrCodeEnum;
import com.cc.map.enums.OutputEnum;
import com.cc.map.enums.SearchTypeEnum;
import com.cc.map.http.request.GeocoderRequest;
import com.cc.map.http.request.IpLocationRequest;
import com.cc.map.http.request.SearchRequest;
import com.cc.map.http.request.SuggestionRequest;
import com.cc.map.http.response.GeocoderResponse;
import com.cc.map.http.response.IpLocationResponse;
import com.cc.map.http.response.SearchResponse;
import com.cc.map.http.response.SuggestionResponse;
import com.cc.map.service.MapService;

@Service
public class MapServiceImpl implements MapService {
	
	@Autowired
	private HttpService httpService;
	
	@Autowired
    private InterfaceService interfaceService;

	@Override
	public String sign(String interfaceUrl, Boolean sort, String sk, Map<String, Object> param) {
		List<String> paramList = new ArrayList<String>();
		Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> entry = iterator.next();
			if(!StringTools.isNullOrNone(StringTools.toString(entry.getValue()))){
				paramList.add(entry.getKey());
			}
		}
		if(sort){
			Collections.sort(paramList);
		}
		StringBuffer buffer = new StringBuffer();
		for(String p: paramList){
			try {
				buffer.append("&"+p+"="+URLEncoder.encode(StringTools.toString(param.get(p)), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new LogicException("E001", "请求参数签名错误");
			}
		}
		buffer.append(sk);
		try {
			return MD5Tools.encrypt(URLEncoder.encode(interfaceUrl+"?"+buffer.substring(1), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new LogicException("E002", "请求参数签名错误");
		}
	}

	@Override
	public IpLocationResponse queryIpLocation(IpLocationRequest request) {
		IpLocationResponse response = new IpLocationResponse();
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if(StringTools.isNullOrNone(request.getAk())) {
			response.setMessage("请输入开发者密钥");
			return response;
		}
		paramMap.put("ak", request.getAk());
		if(StringTools.isNullOrNone(request.getIp())) {
			response.setMessage("请输入定位ip");
			return response;
		}
		paramMap.put("ip", request.getIp());
		if(!StringTools.isNullOrNone(request.getCoor())) {
			paramMap.put("coor", request.getCoor());
		}
		if(!StringTools.isNullOrNone(request.getSk())){
			request.setSn(sign(request.getInterfaceUrl(), false, request.getSk(), paramMap));
			paramMap.put("sn", request.getSn());
		}
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			response = JsonTools.toObject(httpResponse, IpLocationResponse.class);
			if(response == null){
				response = new IpLocationResponse();
				response.setMessage("http返回值为空");
			}else if(!"0".equals(response.getStatus())){
				response.setSuccess(Boolean.FALSE);
				ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(response.getStatus());
				if(errCodeEnum!=null){
					response.setMessage(errCodeEnum.getName());
				}else{
					response.setMessage("未知错误");
				}
				interfaceService.saveInterface(InterfaceEnum.BDIPLOCATION, JsonTools.toJsonString(request), JsonTools.toJsonString(response));
			}else{
				response.setSuccess(Boolean.TRUE);
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

	@Override
	public SearchResponse search(SearchRequest request) {
		SearchResponse response = new SearchResponse();
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if(StringTools.isNullOrNone(request.getAk())) {
			response.setMessage("请输入开发者密钥");
			return response;
		}
		paramMap.put("ak", request.getAk());
		if(StringTools.isNullOrNone(request.getType())) {
			response.setMessage("请选择检索类型");
			return response;
		}
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.getSearchTypeEnumByCode(request.getType());
		if(searchTypeEnum==null){
			response.setMessage("未知检索类型，请重新选择");
			return response;
		}
		switch (searchTypeEnum) {
			case REGION:
			case RADIUS:
			case BOUNDS:
				if(StringTools.isNullOrNone(request.getQuery())) {
					response.setMessage("请输入检索关键字");
					return response;
				}
				paramMap.put("query", request.getQuery());
				if(!StringTools.isNullOrNone(request.getFilter())){
					paramMap.put("filter", request.getFilter());
				}
				if(!StringTools.isNullOrNone(request.getTag())){
					paramMap.put("tag", request.getTag());
				}
				if(request.getCoordType()!=null){
					paramMap.put("coord_type", request.getCoordType());
				}
				if(!StringTools.isNullOrNone(request.getRetCoordtype())){
					paramMap.put("ret_coordtype", request.getRetCoordtype());
				}
				if(request.getPageSize()!=null){
					paramMap.put("page_size", request.getPageSize());
				}
				if(request.getPageNum()!=null){
					paramMap.put("page_num", request.getPageNum());
				}
				break;
				
			case DETAIL:
				if(!StringTools.isNullOrNone(request.getUids())){
					paramMap.put("uids", request.getUids());
				}
				break;
	
			default:
				throw new LogicException("E001", "暂不支持检索类型: "+request.getType());
		}
		switch (searchTypeEnum) {
			case REGION:
				if(StringTools.isNullOrNone(request.getRegion())) {
					response.setMessage("请输入检索行政区划区域");
					return response;
				}
				paramMap.put("region", request.getRegion());
				if(request.getCityLimit()){
					paramMap.put("city_limit", String.valueOf(request.getCityLimit()));
				}
				break;
				
			case RADIUS:
				if(StringTools.isNullOrNone(request.getLocation())) {
					response.setMessage("请输入圆形区域检索中心点");
					return response;
				}
				paramMap.put("location", request.getLocation());
				if(!StringTools.isNullOrNone(request.getRadius())){
					paramMap.put("radius", request.getRadius());
				}
				if(request.getRadiusLimit()){
					paramMap.put("radius_limit", String.valueOf(request.getRadiusLimit()));
				}
				break;
				
			case BOUNDS:
				if(StringTools.isNullOrNone(request.getBounds())) {
					response.setMessage("请输入检索矩形区域");
					return response;
				}
				paramMap.put("bounds", request.getBounds());
				break;
				
			case DETAIL:
				if(StringTools.isNullOrNone(request.getUid())) {
					response.setMessage("请输入poi的唯一标示uid");
					return response;
				}
				paramMap.put("uid", request.getUid());
				break;
	
			default:
				throw new LogicException("E001", "暂不支持检索类型: "+request.getType());
		}
		if(!StringTools.isNullOrNone(request.getOutput())){
			paramMap.put("output", request.getOutput());
		}
		if(!StringTools.isNullOrNone(request.getScope())){
			paramMap.put("scope", request.getScope());
		}
		if(!StringTools.isNullOrNone(request.getSk())){
			request.setTimestamp(System.currentTimeMillis());
			paramMap.put("timestamp", request.getTimestamp());
			request.setSn(sign(request.getInterfaceUrl(), false, request.getSk(), paramMap));
			paramMap.put("sn", request.getSn());
		}
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			OutputEnum outputEnum = OutputEnum.getOutputEnumByCode(request.getOutput());
			if(OutputEnum.JSON.equals(outputEnum)){
				response = JsonTools.toObject(httpResponse, SearchResponse.class);
			}else{
				response = XmlTools.toObject(httpResponse, SearchResponse.class);
			}
			if(response == null){
				response = new SearchResponse();
				response.setMessage("http返回值为空");
			}else if(!Integer.valueOf(0).equals(response.getStatus())){
				response.setSuccess(Boolean.FALSE);
				ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(StringTools.toString(response.getStatus()));
				if(errCodeEnum!=null){
					response.setMessage(errCodeEnum.getName());
				}else{
					response.setMessage("未知错误");
				}
				interfaceService.saveInterface(InterfaceEnum.BDSEARCH, JsonTools.toJsonString(request), JsonTools.toJsonString(response));
			}else{
				response.setSuccess(Boolean.TRUE);
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

	@Override
	public SuggestionResponse suggestion(SuggestionRequest request) {
		SuggestionResponse response = new SuggestionResponse();
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if(StringTools.isNullOrNone(request.getAk())) {
			response.setMessage("请输入开发者密钥");
			return response;
		}
		paramMap.put("ak", request.getAk());
		if(StringTools.isNullOrNone(request.getQuery())) {
			response.setMessage("请输入建议关键字");
			return response;
		}
		paramMap.put("query", request.getQuery());
		if(StringTools.isNullOrNone(request.getRegion())) {
			response.setMessage("请指定行政区划区域");
			return response;
		}
		paramMap.put("region", request.getRegion());
		if(request.getCityLimit()){
			paramMap.put("city_limit", String.valueOf(request.getCityLimit()));
		}
		if(!StringTools.isNullOrNone(request.getLocation())){
			paramMap.put("location", request.getLocation());
		}
		if(request.getCoordType()!=null){
			paramMap.put("coord_type", request.getCoordType());
		}
		if(!StringTools.isNullOrNone(request.getRetCoordtype())){
			paramMap.put("ret_coordtype", request.getRetCoordtype());
		}
		if(!StringTools.isNullOrNone(request.getOutput())){
			paramMap.put("output", request.getOutput());
		}
		if(!StringTools.isNullOrNone(request.getSk())){
			request.setTimestamp(System.currentTimeMillis());
			paramMap.put("timestamp", request.getTimestamp());
			request.setSn(sign(request.getInterfaceUrl(), false, request.getSk(), paramMap));
			paramMap.put("sn", request.getSn());
		}
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			OutputEnum outputEnum = OutputEnum.getOutputEnumByCode(request.getOutput());
			if(OutputEnum.JSON.equals(outputEnum)){
				response = JsonTools.toObject(httpResponse, SuggestionResponse.class);
			}else{
				response = XmlTools.toObject(httpResponse, SuggestionResponse.class);
			}
			if(response == null){
				response = new SuggestionResponse();
				response.setMessage("http返回值为空");
			}else if(!Integer.valueOf(0).equals(response.getStatus())){
				response.setSuccess(Boolean.FALSE);
				ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(StringTools.toString(response.getStatus()));
				if(errCodeEnum!=null){
					response.setMessage(errCodeEnum.getName());
				}else{
					response.setMessage("未知错误");
				}
				interfaceService.saveInterface(InterfaceEnum.BDSUGGESTION, JsonTools.toJsonString(request), JsonTools.toJsonString(response));
			}else{
				response.setSuccess(Boolean.TRUE);
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

	@Override
	public GeocoderResponse geocoder(GeocoderRequest request) {
		GeocoderResponse response = new GeocoderResponse();
		Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
		if(StringTools.isNullOrNone(request.getAk())) {
			response.setMessage("请输入开发者密钥");
			return response;
		}
		paramMap.put("ak", request.getAk());
		if(StringTools.isNullOrNone(request.getLocation())) {
			response.setMessage("请输入经纬度坐标");
			return response;
		}
		paramMap.put("location", request.getLocation());
		if(request.getCoordType()!=null){
			paramMap.put("coord_type", request.getCoordType());
		}
		if(!StringTools.isNullOrNone(request.getRetCoordtype())){
			paramMap.put("ret_coordtype", request.getRetCoordtype());
		}
		if(!StringTools.isNullOrNone(request.getOutput())){
			paramMap.put("output", request.getOutput());
		}
		if(request.getRadius()!=null){
			paramMap.put("radius", request.getRadius());
		}
		if(request.getPois()!=null){
			paramMap.put("pois", request.getPois());
			if(Integer.valueOf(0).equals(request.getPois())){
				request.setExtensionsPoi("null");
			}
		}
		if(!StringTools.isNullOrNone(request.getCallback())){
			paramMap.put("callback", request.getCallback());
		}
		if(!StringTools.isNullOrNone(request.getLanguage())){
			paramMap.put("language", request.getLanguage());
		}
		if(request.getLanguageAuto()!=null){
			paramMap.put("language_auto", request.getLanguageAuto());
		}
		if(!StringTools.isNullOrNone(request.getExtensionsPoi())){
			paramMap.put("extensions_poi", request.getExtensionsPoi());
		}
		paramMap.put("extensions_road", String.valueOf(request.getExtensionsRoad()));
		paramMap.put("extensions_town", String.valueOf(request.getExtensionsTown()));
		if(request.getLatestAdmin()!=null){
			paramMap.put("latest_admin", request.getLatestAdmin());
		}
		if(!StringTools.isNullOrNone(request.getSk())){
			request.setSn(sign(request.getInterfaceUrl(), false, request.getSk(), paramMap));
			paramMap.put("sn", request.getSn());
		}
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			OutputEnum outputEnum = OutputEnum.getOutputEnumByCode(request.getOutput());
			if(OutputEnum.JSON.equals(outputEnum)){
				response = JsonTools.toObject(httpResponse, GeocoderResponse.class);
			}else{
				response = XmlTools.toObject(httpResponse, GeocoderResponse.class);
			}
			if(response == null){
				response = new GeocoderResponse();
				response.setMessage("http返回值为空");
			}else if(!Integer.valueOf(0).equals(response.getStatus())){
				response.setSuccess(Boolean.FALSE);
				ErrCodeEnum errCodeEnum = ErrCodeEnum.getErrCodeEnumByCode(StringTools.toString(response.getStatus()));
				if(errCodeEnum!=null){
					response.setMessage(errCodeEnum.getName());
				}else{
					response.setMessage("未知错误");
				}
				interfaceService.saveInterface(InterfaceEnum.BDGEOCODER, JsonTools.toJsonString(request), JsonTools.toJsonString(response));
			}else{
				response.setSuccess(Boolean.TRUE);
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

}
