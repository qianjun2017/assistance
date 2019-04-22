/**
 * 
 */
package com.cc.leaguer.service;

import java.util.List;

import com.cc.leaguer.bean.LeaguerChannelBean;

/**
 * @author Administrator
 *
 */
public interface LeaguerChannelService {

	/**
	 * 修改会员频道
	 * @param leaguerId
	 * @param channelIdList
	 */
	void updateLeaguerChannel(Long leaguerId, List<Long> channelIdList);
	
	/**
	 * 获取会员的频道列表
	 * @param leaguerId
	 * @return
	 */
	List<LeaguerChannelBean> queryLeaguerChannelBeanList(Long leaguerId);
}
