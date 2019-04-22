/**
 * 
 */
package com.cc.activity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.activity.bean.ActivityBean;
import com.cc.activity.form.ActivityParticipateQueryForm;
import com.cc.activity.form.ActivityParticipateStatisticsQueryForm;
import com.cc.activity.form.ActivityQueryForm;
import com.cc.activity.result.ActivityParticipateResult;
import com.cc.activity.result.ActivityParticipateStatisticsResult;
import com.cc.common.orm.dao.CrudDao;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ActivityDao extends CrudDao {

	/**
	 * 查询活动参与列表
	 * @param form
	 * @return
	 */
	List<ActivityParticipateResult> queryActivityParticipateList(ActivityParticipateQueryForm form);
	
	/**
	 * 活动参与统计
	 * @param form
	 * @return
	 */
	List<ActivityParticipateStatisticsResult> queryActivityParticipateStatisticsList(ActivityParticipateStatisticsQueryForm form);
	
	/**
	 * 查询活动列表
	 * @param form
	 * @return
	 */
	List<ActivityBean> queryActivityList(ActivityQueryForm form);
}
