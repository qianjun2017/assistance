/**
 * 
 */
package com.cc.channel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.channel.form.ChannelSubjectItemQueryForm;
import com.cc.channel.result.ChannelSubjectItemResult;
import com.cc.common.orm.dao.CrudDao;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ChannelSubjectDao extends CrudDao {

	/**
	 * 查询主题内容列表
	 * @param form
	 * @return
	 */
	List<ChannelSubjectItemResult> queryChannelSubjectItemList(ChannelSubjectItemQueryForm form);
}
