package com.cc.push.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.push.form.PushQueryForm;
import com.cc.push.result.PushListResult;

@Mapper
public interface PushDao extends CrudDao {

	/**
	 * 查询推送历史
	 * @param form
	 * @return
	 */
	List<PushListResult> queryPushList(PushQueryForm form);

}
