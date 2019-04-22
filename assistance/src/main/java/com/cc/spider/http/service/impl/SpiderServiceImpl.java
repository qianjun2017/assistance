/**
 * 
 */
package com.cc.spider.http.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.StringTools;
import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.http.response.Data;
import com.cc.spider.http.response.DataPage;
import com.cc.spider.http.response.SpiderResponse;
import com.cc.spider.http.service.SpiderService;

/**
 * @author Administrator
 *
 */
@Service
public class SpiderServiceImpl implements SpiderService {
	
	@Autowired
	private HttpService httpService;

	@Override
	public SpiderResponse<DataPage> querySpiderItems(SpiderRequest request) {
		SpiderResponse<DataPage> response = new SpiderResponse<DataPage>();
		if(StringTools.isNullOrNone(request.getUserName())){
			response.setMessage("请输入爬虫用户名");
			return response;
		}
		if(StringTools.isNullOrNone(request.getPwd())){
			response.setMessage("请输入爬虫用户密码");
			return response;
		}
		if(StringTools.isNullOrNone(request.getSpiderNo())){
			response.setMessage("请输入爬虫编码");
			return response;
		}
		if(StringTools.isNullOrNone(request.getUrl())){
			response.setMessage("请输入爬虫地址");
			return response;
		}
		Map<String, Object> paramMap = request.getParamMap();
		paramMap.put("userName", request.getUserName());
		paramMap.put("pwd", request.getPwd());
		paramMap.put("spiderNo", request.getSpiderNo());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			DataPage dataPage = JsonTools.toObject(httpResponse, DataPage.class);
			response.setData(dataPage);
			response.setSuccess(Boolean.TRUE);
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}

	@Override
	public SpiderResponse<String> manageSpiderUrl(SpiderRequest request) {
		SpiderResponse<String> response = new SpiderResponse<String>();
		if(StringTools.isNullOrNone(request.getUserName())){
			response.setMessage("请输入爬虫用户名");
			return response;
		}
		if(StringTools.isNullOrNone(request.getPwd())){
			response.setMessage("请输入爬虫用户密码");
			return response;
		}
		if(StringTools.isNullOrNone(request.getSpiderNo())){
			response.setMessage("请输入爬虫编码");
			return response;
		}
		if(StringTools.isNullOrNone(request.getUrl())){
			response.setMessage("请输入爬虫地址");
			return response;
		}
		Map<String, Object> paramMap = request.getParamMap();
		paramMap.put("userName", request.getUserName());
		paramMap.put("pwd", request.getPwd());
		paramMap.put("spiderNo", request.getSpiderNo());
		String httpResponse = httpService.post(request.getUrl(), paramMap, "UTF-8");
		if (!StringTools.isNullOrNone(httpResponse)) {
			Data data = JsonTools.toObject(httpResponse, Data.class);
			if(data.isSuccess()){
				response.setSuccess(Boolean.TRUE);
			}else{
				response.setMessage(data.getMessage());
			}
		}else {
			response.setMessage("http返回值为空");
		}
		return response;
	}
}
