/**
 * 
 */
package com.cc.map.service;

import java.util.Map;

import com.cc.map.http.request.GeocoderRequest;
import com.cc.map.http.request.IpLocationRequest;
import com.cc.map.http.request.SearchRequest;
import com.cc.map.http.request.SuggestionRequest;
import com.cc.map.http.response.GeocoderResponse;
import com.cc.map.http.response.IpLocationResponse;
import com.cc.map.http.response.SearchResponse;
import com.cc.map.http.response.SuggestionResponse;

/**
 * @author ws_yu
 *
 */
public interface MapService {

	/**
	 * 参数签名
	 * @param interfaceUrl
	 * @param sort
	 * @param sk
	 * @param param
	 * @return
	 */
	String sign(String interfaceUrl, Boolean sort, String sk, Map<String, Object> param);
	
	/**
	 * 查询IP地理位置
	 * @param request
	 * @return
	 */
	IpLocationResponse queryIpLocation(IpLocationRequest request);
	
	/**
	 * 地点检索
	 * @param request
	 * @return
	 */
	SearchResponse search(SearchRequest request);
	
	/**
	 * 地点输入提示
	 * @param request
	 * @return
	 */
	SuggestionResponse suggestion(SuggestionRequest request);
	
	/**
	 * 逆地理编码
	 * @param request
	 * @return
	 */
	GeocoderResponse geocoder(GeocoderRequest request);
}
