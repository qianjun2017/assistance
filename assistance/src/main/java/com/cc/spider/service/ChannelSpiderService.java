/**
 * 
 */
package com.cc.spider.service;

import java.util.List;

import com.cc.spider.bean.ChannelSpiderBean;

/**
 * @author Administrator
 *
 */
public interface ChannelSpiderService {

	/**
	 * 保存频道爬虫信息
	 * @param channelSpiderBean
	 */
	void saveChannelSpider(ChannelSpiderBean channelSpiderBean);
	
	/**
	 * 删除频道爬虫
	 * @param id
	 */
	void deleteChannelSpider(Long id);
	
	/**
	 * 查询频道的所有爬虫
	 * @param channelId
	 * @return
	 */
	List<ChannelSpiderBean> queryChannelSpiderBeanList(Long channelId);
}
