/**
 * 
 */
package com.cc.channel.service;

import java.util.List;

import com.cc.channel.bean.ChannelSubjectBean;
import com.cc.channel.bean.ChannelSubjectItemBean;
import com.cc.channel.enums.ChannelSubjectStatusEnum;
import com.cc.channel.form.ChannelSubjectItemQueryForm;
import com.cc.channel.form.ChannelSubjectQueryForm;
import com.cc.channel.result.ChannelSubjectItemResult;
import com.cc.channel.result.ChannelSubjectResult;
import com.cc.common.web.Page;

/**
 * @author Administrator
 *
 */
public interface ChannelSubjectService {

	/**
	 * 保存频道专题
	 * @param channelSubjectBean
	 */
	void saveChannelSubject(ChannelSubjectBean channelSubjectBean);
	
	/**
	 * 删除频道专题
	 * @param id
	 */
	void deleteChannelSubject(Long id);
	
	/**
	 * 保存专题内容
	 * @param channelSubjectItemBeanList
	 */
	void saveChannelSubjectItemList(List<ChannelSubjectItemBean> channelSubjectItemBeanList);
	
	/**
	 * 删除专题内容
	 * @param id
	 */
	void deleteChannelSubjectItem(Long id);
	
	/**
	 * 分页查询频道专题列表
	 * @param form
	 * @return
	 */
	Page<ChannelSubjectResult> queryChannelSubjectPage(ChannelSubjectQueryForm form);
	
	/**
	 * 查询专题内容
	 * @param form
	 * @return
	 */
	List<ChannelSubjectItemResult> queryChannelSubjectItemList(ChannelSubjectItemQueryForm form);
	
	/**
	 * 修改专题状态
	 * @param subjectId
	 * @param channelSubjectStatusEnum
	 */
	void changeChannelSubjectStatus(Long subjectId, ChannelSubjectStatusEnum channelSubjectStatusEnum);
	
	
}
