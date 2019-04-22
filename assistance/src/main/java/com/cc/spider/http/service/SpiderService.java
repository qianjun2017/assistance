/**
 * 
 */
package com.cc.spider.http.service;

import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.http.response.DataPage;
import com.cc.spider.http.response.SpiderResponse;

/**
 * @author Administrator
 *
 */
public interface SpiderService {

	/**
	 * 查询爬虫内容
	 * @param request
	 * @return
	 */
	SpiderResponse<DataPage> querySpiderItems(SpiderRequest request);
	
	/**
	 * 管理爬虫地址
	 * @param request
	 * @return
	 */
	SpiderResponse<String> manageSpiderUrl(SpiderRequest request);
}
