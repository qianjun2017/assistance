/**
 * 
 */
package com.cc.activity.service;

import java.util.Map;

import com.cc.activity.bean.ActivityLeaguerBean;
import com.cc.activity.enums.ActivityStatusEnum;
import com.cc.activity.form.ActivityForm;
import com.cc.activity.form.ActivityParticipateForm;
import com.cc.activity.form.ActivityParticipateQueryForm;
import com.cc.activity.form.ActivityParticipateStatisticsQueryForm;
import com.cc.activity.form.ActivityQueryForm;
import com.cc.activity.result.ActivityParticipateResult;
import com.cc.activity.result.ActivityParticipateStatisticsResult;
import com.cc.common.web.Page;

/**
 * @author Administrator
 *
 */
public interface ActivityService {

	/**
	 * 保存活动
	 * @param activity
	 */
	void saveActivity(ActivityForm activity);

	/**
	 * 查询活动
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryActivityPage(ActivityQueryForm form);
	
	/**
	 * 参与活动
	 * @param form
	 */
	void participateActivity(ActivityParticipateForm form);

	/**
	 * 查询活动参与
	 * @param form
	 * @return
	 */
	Page<ActivityParticipateResult> queryActivityParticipatePage(ActivityParticipateQueryForm form);
	
	/**
	 * 审核活动参与
	 * @param activityLeaguerBean
	 */
	void auditActivityParticipate(ActivityLeaguerBean activityLeaguerBean);
	
	/**
	 * 统计活动参与情况
	 * @param form
	 * @return
	 */
	Page<ActivityParticipateStatisticsResult> queryActivityParticipateStatisticsList(ActivityParticipateStatisticsQueryForm form);
	
	/**
	 * 统计活动参与情况
	 * @param activityId
	 * @return
	 */
	ActivityParticipateStatisticsResult queryActivityParticipateStatistics(Long activityId);
	
	/**
	 * 改变活动状态
	 * @param id
	 * @param status
	 */
	void changeActivityStatus(Long id, ActivityStatusEnum status);
	
	/**
	 * 删除活动
	 * @param id
	 */
	void deleteActivity(Long id);
}
