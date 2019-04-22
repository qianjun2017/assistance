/**
 * 
 */
package com.cc.channel.service;

import java.util.List;
import java.util.Map;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.form.ChannelItemQueryForm;
import com.cc.channel.form.ChannelQueryForm;
import com.cc.channel.result.ChannelItemResult;
import com.cc.common.web.Page;

/**
 * @author Administrator
 *
 */
public interface ChannelService {

	/**
	 * 保存频道
	 * @param channelBean
	 */
	void saveChannel(ChannelBean channelBean);
	
	/**
	 * 删除频道
	 * @param id
	 */
	void deleteChannel(Long id);
	
	/**
	 * 关闭频道
	 * @param id
	 */
	void closeChannel(Long id);
	
	/**
	 * 开启频道
	 * @param id
	 */
	void openChannel(Long id);
	
	/**
	 * 分页查询频道
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryChannelPage(ChannelQueryForm form);
	
	/**
	 * 根据编码查询频列表
	 * @param channelCode
	 * @return
	 */
	List<ChannelBean> queryChannelBeanList(String channelCode);
	
	/**
	 * 查询频道
	 * @param form
	 * @return
	 */
	List<ChannelBean> queryChannelBeanList(ChannelQueryForm form);
	
	/**
	 * 查询频道
	 * @param channelIdList
	 * @return
	 */
	List<ChannelBean> queryChannelBeanList(List<Long> channelIdList);
	
	/**
	 * 分页查询频道内容
	 * @param form
	 * @return
	 */
	Page<ChannelItemResult> queryChannelItemPage(ChannelItemQueryForm form);
}
