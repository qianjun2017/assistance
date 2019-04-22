/**
 * 
 */
package com.cc.channel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.channel.form.ChannelItemQueryForm;
import com.cc.channel.result.ChannelItemResult;
import com.cc.common.orm.dao.CrudDao;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ChannelDao extends CrudDao {
	
	/**
	 * 查询频道内容
	 * @param form
	 * @return
	 */
	List<ChannelItemResult> queryChannelItemList(ChannelItemQueryForm form);
}
