/**
 * 
 */
package com.cc.channel.schedule;

import com.cc.spider.http.request.SpiderRequest;

/**
 * @author Administrator
 *
 */
public abstract class ChannelSchedule {

	/**
	 * 更新数据
	 * @param request
	 * @return
	 */
	public abstract Long querySpiderItems(SpiderRequest request);
}
