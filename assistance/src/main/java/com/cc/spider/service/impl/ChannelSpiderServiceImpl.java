/**
 * 
 */
package com.cc.spider.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.spider.bean.ChannelSpiderBean;
import com.cc.spider.service.ChannelSpiderService;

/**
 * @author Administrator
 *
 */
@Service
public class ChannelSpiderServiceImpl implements ChannelSpiderService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveChannelSpider(ChannelSpiderBean channelSpiderBean) {
		int row = channelSpiderBean.save();
		if (row!=1) {
			throw new LogicException("E001", "配置频道爬虫失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteChannelSpider(Long id) {
		ChannelSpiderBean channelSpiderBean = new ChannelSpiderBean();
		channelSpiderBean.setId(id);
		int row = channelSpiderBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除频道爬虫失败");
		}
	}

	@Override
	public List<ChannelSpiderBean> queryChannelSpiderBeanList(Long channelId) {
		List<ChannelSpiderBean> channelSpiderBeanList = ChannelSpiderBean.findAllByParams(ChannelSpiderBean.class, "channelId", channelId);
		return channelSpiderBeanList;
	}

}
