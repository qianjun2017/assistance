/**
 * 
 */
package com.cc.leaguer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.dao.LeaguerChannelDao;
import com.cc.leaguer.service.LeaguerChannelService;
import com.cc.system.log.utils.LogContextUtil;

/**
 * @author Administrator
 *
 */
@Service
public class LeaguerChannelServiceImpl implements LeaguerChannelService {
	
	@Autowired
	private LeaguerChannelDao leaguerChannelDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateLeaguerChannel(Long leaguerId, List<Long> channelIdList) {
		StringBuffer buffer = new StringBuffer();
		List<LeaguerChannelBean> leaguerChannelBeanList = LeaguerChannelBean.findAllByParams(LeaguerChannelBean.class, "leaguerId", leaguerId);
		if (!ListTools.isEmptyOrNull(leaguerChannelBeanList)) {
			List<Long> deleteList = new ArrayList<Long>();
			for (LeaguerChannelBean leaguerChannelBean : leaguerChannelBeanList) {
				Long channelId = leaguerChannelBean.getChannelId();
				if (channelIdList.contains(channelId)) {
					channelIdList.remove(channelId);
				} else {
					int row = leaguerChannelBean.delete();
					if (row != 1) {
						throw new LogicException("E001", "设置频道时,删除频道失败");
					}
					deleteList.add(channelId);
				}
			}
			buffer.append("删除频道").append(ListTools.toString(deleteList));
		}
		if (!ListTools.isEmptyOrNull(channelIdList)) {
			for (Long channelId : channelIdList) {
				LeaguerChannelBean leaguerChannelBean = new LeaguerChannelBean();
				leaguerChannelBean.setChannelId(channelId);
				leaguerChannelBean.setLeaguerId(leaguerId);
				int row = leaguerChannelBean.insert();
				if (row!=1) {
					throw new LogicException("E001","设置频道时,新增频道失败");
				}
			}
			buffer.append((buffer.length()>0?",":"")+"新增频道").append(ListTools.toString(channelIdList));
		}
		LogContextUtil.setOperContent(buffer.substring(0));
	}

	@Override
	public List<LeaguerChannelBean> queryLeaguerChannelBeanList(Long leaguerId) {
		return leaguerChannelDao.queryLeaguerChannelBeanList(leaguerId);
	}

}
