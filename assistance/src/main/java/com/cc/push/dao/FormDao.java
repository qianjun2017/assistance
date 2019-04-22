/**
 * 
 */
package com.cc.push.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.push.form.FormQueryForm;
import com.cc.push.result.FormResult;

/**
 * @author ws_yu
 *
 */
@Mapper
public interface FormDao extends CrudDao {

	/**
	 * 查询form列表，每个用户查询出最快到期的一条数据
	 * @param form
	 * @return
	 */
	List<FormResult> queryFormList(FormQueryForm form);
}
