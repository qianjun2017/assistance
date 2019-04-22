/**
 * 
 */
package com.cc.activity.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.activity.bean.ActivityBean;
import com.cc.activity.bean.ActivityChannelBean;
import com.cc.activity.bean.ActivityPlotBean;
import com.cc.activity.bean.ActivityImageBean;
import com.cc.activity.bean.ActivityImageSampleBean;
import com.cc.activity.bean.ActivityLeaguerBean;
import com.cc.activity.bean.ActivityLeaguerImageBean;
import com.cc.activity.bean.ActivityLocationBean;
import com.cc.activity.dao.ActivityDao;
import com.cc.activity.enums.ActivityParticipateStatusEnum;
import com.cc.activity.enums.ActivityStatusEnum;
import com.cc.activity.enums.ActivityTypeEnum;
import com.cc.activity.form.ActivityForm;
import com.cc.activity.form.ActivityImageSampleForm;
import com.cc.activity.form.ActivityParticipateForm;
import com.cc.activity.form.ActivityParticipateImageForm;
import com.cc.activity.form.ActivityParticipateQueryForm;
import com.cc.activity.form.ActivityParticipateStatisticsQueryForm;
import com.cc.activity.form.ActivityQueryForm;
import com.cc.activity.result.ActivityParticipateResult;
import com.cc.activity.result.ActivityParticipateStatisticsResult;
import com.cc.activity.service.ActivityService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.event.Event;
import com.cc.integration.event.EventFactory;
import com.cc.integration.service.IntegrationEventService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.service.LeaguerService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.location.result.LocationResult;
import com.cc.system.location.service.LocationService;
import com.cc.system.message.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService {
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private LeaguerService leaguerService;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private IntegrationEventService integrationEventService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveActivity(ActivityForm activity) {
		ActivityBean activityBean = null;
		if (activity.getId()!=null) {
			activityBean = ActivityBean.get(ActivityBean.class, activity.getId());
			if (activityBean==null) {
				throw new LogicException("E001", "活动不存在");
			}
		}else {
			activityBean = new ActivityBean();
			activityBean.setCreateTime(DateTools.now());
			activityBean.setStatus(ActivityStatusEnum.DRAFT.getCode());
		}
		activityBean.setName(activity.getName());
		activityBean.setType(activity.getType());
		activityBean.setIntegration(activity.getIntegration());
		activityBean.setStartTime(activity.getStartTime());
		activityBean.setEndTime(activity.getEndTime());
		activityBean.setAutoAudit(activity.getAutoAudit());
		activityBean.setParticipateType(activity.getParticipateType());
		int row = activityBean.save();
		if (row!=1) {
			throw new LogicException("E002", "保存活动失败");
		}
		List<ActivityPlotBean> activityPlotBeanList = ActivityPlotBean.findAllByParams(ActivityPlotBean.class, "activityId", activityBean.getId());
		ActivityPlotBean activityPlotBean = null;
		if (ListTools.isEmptyOrNull(activityPlotBeanList)) {
			activityPlotBean = new ActivityPlotBean();
		}else {
			activityPlotBean = activityPlotBeanList.get(0);
		}
		activityPlotBean.setActivityId(activityBean.getId());
		activityPlotBean.setDescription(StringTools.isNullOrNone(activity.getDescription())?null:activity.getDescription().getBytes());
		activityPlotBean.setPlot(StringTools.isNullOrNone(activity.getPlot())?null:activity.getPlot().getBytes());
		row = activityPlotBean.save();
		if (row!=1) {
			throw new LogicException("E003", "保存活动失败");
		}
		List<ActivityImageBean> activityImageBeanList = ActivityImageBean.findAllByParams(ActivityImageBean.class, "activityId", activityBean.getId());
		if (!ListTools.isEmptyOrNull(activityImageBeanList)) {
			for (ActivityImageBean activityImageBean : activityImageBeanList) {
				activityImageBean.delete();
			}
		}
		if (!ListTools.isEmptyOrNull(activity.getImageUrlList())) {
			for (String imageUrl : activity.getImageUrlList()) {
				ActivityImageBean activityImageBean = new ActivityImageBean();
				activityImageBean.setActivityId(activityBean.getId());
				activityImageBean.setImageNo(activity.getImageUrlList().indexOf(imageUrl)+1);
				activityImageBean.setImageUrl(imageUrl);
				row = activityImageBean.insert();
				if (row!=1) {
					throw new LogicException("E004", "保存活动失败");
				}
			}
			activityBean.setImgUrl(activity.getImageUrlList().get(0));
		}
		List<ActivityImageSampleBean> activityImageSampleBeanList = ActivityImageSampleBean.findAllByParams(ActivityImageSampleBean.class, "activityId", activityBean.getId());
		if (!ListTools.isEmptyOrNull(activityImageSampleBeanList)) {
			for (ActivityImageSampleBean activityImageSampleBean : activityImageSampleBeanList) {
				activityImageSampleBean.delete();
			}
		}
		List<ActivityImageSampleForm> sampleImageList = activity.getSampleImageList();
		if (!ListTools.isEmptyOrNull(sampleImageList)) {
			for (ActivityImageSampleForm activityImageSampleForm : sampleImageList) {
				ActivityImageSampleBean activityImageSampleBean = new ActivityImageSampleBean();
				activityImageSampleBean.setActivityId(activityBean.getId());
				activityImageSampleBean.setImageNo(sampleImageList.indexOf(activityImageSampleForm)+1);
				activityImageSampleBean.setImageUrl(activityImageSampleForm.getImageUrl());
				activityImageSampleBean.setName(activityImageSampleForm.getName());
				activityImageSampleBean.setRemark(activityImageSampleForm.getRemark());
				row = activityImageSampleBean.insert();
				if (row!=1) {
					throw new LogicException("E005", "保存活动失败");
				}
			}
		}
		List<Long> channelIdList = new ArrayList<Long>();
		for (Integer channel : activity.getChannelList()) {
			channelIdList.add(new Long(channel));
		}
		Example example = new Example(ActivityChannelBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("activityId", activityBean.getId());
		ActivityChannelBean.deleteByExample(ActivityChannelBean.class, example);
		for (Long channelId : channelIdList) {
			ActivityChannelBean activityChannelBean = new ActivityChannelBean();
			activityChannelBean.setActivityId(activityBean.getId());
			activityChannelBean.setChannelId(channelId);
			row = activityChannelBean.insert();
			if (row!=1) {
				throw new LogicException("E006", "保存活动失败");
			}
		}
		List<ActivityLocationBean> activityLocationBeanList = ActivityLocationBean.findAllByParams(ActivityLocationBean.class, "activtyId", activity.getId());
        if(!ListTools.isEmptyOrNull(activityLocationBeanList)){
        	ActivityLocationBean activityLocationBean = activityLocationBeanList.get(0);
        	activityLocationBean.setLocationId(activity.getLocationId());
        	activityLocationBean.update();
        }else{
        	ActivityLocationBean newActivityLocationBean = new ActivityLocationBean();
        	newActivityLocationBean.setLocationId(activity.getLocationId());
        	newActivityLocationBean.setActivityId(activityBean.getId());
        	newActivityLocationBean.insert();
        }
	}

	@Override
	public Page<Map<String, Object>> queryActivityPage(ActivityQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		PageHelper.orderBy(String.format("a.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ActivityBean> activityBeanList = activityDao.queryActivityList(form);
		PageInfo<ActivityBean> pageInfo = new PageInfo<ActivityBean>(activityBeanList);
		if (ListTools.isEmptyOrNull(activityBeanList)) {
			page.setMessage("没有查询到相关活动数据");
			return page;
		}
		List<Map<String, Object>> activityList = new ArrayList<Map<String,Object>>();
		for (ActivityBean activityBean : activityBeanList) {
			Map<String, Object> activity = new HashMap<String, Object>();
			activity.put("id", activityBean.getId());
			activity.put("status", activityBean.getStatus());
			activity.put("statusName", ActivityStatusEnum.getNameByCode(activityBean.getStatus()));
			activity.put("typeName", ActivityTypeEnum.getNameByCode(activityBean.getType()));
			activity.put("name", activityBean.getName());
			activity.put("integration", activityBean.getIntegration());
			LocationResult location = null;
			List<ActivityLocationBean> activityLocationBeanList = ActivityLocationBean.findAllByParams(ActivityLocationBean.class, "activityId", activityBean.getId());
			if(!ListTools.isEmptyOrNull(activityLocationBeanList)){
				LocationBean locationBean = LocationBean.get(LocationBean.class, activityLocationBeanList.get(0));
				if(locationBean!=null){
					
				}
				location = locationService.queryLocation(activityLocationBeanList.get(0).getLocationId());
			}
			if(location!=null){
				activity.put("country", location.getCountry());
				activity.put("province", location.getProvince());
				activity.put("city", location.getCity());
			}
			activity.put("autoAudit", activityBean.getAutoAudit());
			activity.put("createTime", DateTools.getFormatDate(activityBean.getCreateTime(), DateTools.DATEFORMAT3));
			activity.put("startTime", activityBean.getStartTime() == null?"":DateTools.getFormatDate(activityBean.getStartTime(), DateTools.DATEFORMAT));
			activity.put("endTime", activityBean.getEndTime() == null?"":DateTools.getFormatDate(activityBean.getEndTime(), DateTools.DATEFORMAT));
			List<ActivityImageBean> activityImageBeanList = ActivityImageBean.findAllByParams(ActivityImageBean.class, "activityId", activityBean.getId(), "imageNo", "1");
			if (!ListTools.isEmptyOrNull(activityImageBeanList)) {
				activity.put("listImgUrl", activityImageBeanList.get(0).getImageUrl());
			}
			activityList.add(activity);
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(activityList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void participateActivity(ActivityParticipateForm form) {
		ActivityLeaguerBean activityLeaguerBean = new ActivityLeaguerBean();
		activityLeaguerBean.setActivityId(form.getActivityId());
		activityLeaguerBean.setLeaguerId(form.getLeaguerId());
		if(form.getAutoAudit()){
			activityLeaguerBean.setStatus(ActivityParticipateStatusEnum.SUCCESS.getCode());
			activityLeaguerBean.setRemark("系统自动审核");
		}else{
			activityLeaguerBean.setStatus(ActivityParticipateStatusEnum.WAIT.getCode());
		}
		List<ActivityLeaguerImageBean> activityLeaguerImageBeanList = new ArrayList<ActivityLeaguerImageBean>();
		List<ActivityImageSampleBean> activityImageSampleBeanList = ActivityImageSampleBean.findAllByParams(ActivityImageSampleBean.class, "activityId", activityLeaguerBean.getActivityId());
		if (!ListTools.isEmptyOrNull(activityImageSampleBeanList)) {
			if (ListTools.isEmptyOrNull(form.getImageList()) || form.getImageList().size()!=activityImageSampleBeanList.size()) {
				throw new LogicException("E001", "图片张数不对");
			}
			for (ActivityImageSampleBean activityImageSampleBean : activityImageSampleBeanList) {
				int imageNo = activityImageSampleBean.getImageNo();
				Boolean find = Boolean.FALSE;
				for (ActivityParticipateImageForm activityParticipateImage : form.getImageList()) {
					if (activityParticipateImage.getImageNo() == imageNo) {
						find = Boolean.TRUE;
						if(form.getAutoAudit()){
							//对每张图片进行审核,不符合则设置为待定
						}
						ActivityLeaguerImageBean activityLeaguerImageBean = new ActivityLeaguerImageBean();
						activityLeaguerImageBean.setImageNo(imageNo);
						activityLeaguerImageBean.setImageUrl(activityParticipateImage.getImageUrl());
						activityLeaguerImageBeanList.add(activityLeaguerImageBean);
						break;
					}
				}
				if (!find) {
					throw new LogicException("E002", "请上传"+activityImageSampleBean.getName());
				}
			}
		}
		activityLeaguerBean.setCreateTime(DateTools.now());
		int row = activityLeaguerBean.save();
		if (row!=1) {
			throw new LogicException("E003", "参与活动失败");
		}
		if (!ListTools.isEmptyOrNull(activityLeaguerImageBeanList)) {
			for (ActivityLeaguerImageBean activityLeaguerImageBean : activityLeaguerImageBeanList) {
				activityLeaguerImageBean.setParticipateId(activityLeaguerBean.getId());
				row = activityLeaguerImageBean.save();
				if (row!=1) {
					throw new LogicException("E004", "参与活动失败");
				}
			}
		}
		if(ActivityParticipateStatusEnum.SUCCESS.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))){
			SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("activity.credit");
			if (systemConfigBean!=null) {
				try {
					leaguerService.addLeaguerCredit(activityLeaguerBean.getLeaguerId(), Integer.parseInt(systemConfigBean.getPropertyValue()));
				} catch (LogicException e) {
					logger.warn("会员["+activityLeaguerBean.getLeaguerId()+"]提升信用["+systemConfigBean.getPropertyValue()+"],"+e.getErrContent());
				} catch (Exception e) {
					logger.warn("会员["+activityLeaguerBean.getLeaguerId()+"]提升信用["+systemConfigBean.getPropertyValue()+"]失败");
					e.printStackTrace();
				}
			}
			List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.ACTIVITY.getCode());
			if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
				return;
			}
			if (integrationEventBeanList.size()>1) {
				logger.error("参与活动事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(activityLeaguerBean.getLeaguerId());
				event.setData(ActivityBean.get(ActivityBean.class, activityLeaguerBean.getActivityId()));
				event.happened();
			}
		}
	}

	@Override
	public Page<ActivityParticipateResult> queryActivityParticipatePage(ActivityParticipateQueryForm form) {
		Page<ActivityParticipateResult> page = new Page<ActivityParticipateResult>();
		PageHelper.orderBy(String.format("al.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ActivityParticipateResult> activityParticipateList = activityDao.queryActivityParticipateList(form);
		PageInfo<ActivityParticipateResult> pageInfo = new PageInfo<ActivityParticipateResult>(activityParticipateList);
		if (ListTools.isEmptyOrNull(activityParticipateList)) {
			page.setMessage("没有查询到相关活动参与数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(activityParticipateList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void auditActivityParticipate(ActivityLeaguerBean activityLeaguerBean) {
		ActivityLeaguerBean activityParticipateBean = ActivityLeaguerBean.get(ActivityLeaguerBean.class, activityLeaguerBean.getId());
		if (activityParticipateBean==null) {
			throw new LogicException("E001", "活动参与不存在");
		}
		if (ActivityParticipateStatusEnum.FAKE.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))) {
			leaguerService.downHalfLeaguerCredit(activityParticipateBean.getLeaguerId());
			ActivityBean activityBean = ActivityBean.get(ActivityBean.class, activityLeaguerBean.getActivityId());
			LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, activityLeaguerBean.getLeaguerId());
			if(activityBean!=null && leaguerBean!=null){
				messageService.releaseSystemMessage(leaguerBean.getId(), "系统检测到您在参与活动【"+activityBean.getName()+"】时，存在欺诈参与行为【"+activityLeaguerBean.getRemark()+"】，现扣除您参与活动所得积分，并对您的信誉值进行折半处理，关闭参与活动自动审批功能。");
			}
		}else if (ActivityParticipateStatusEnum.SUCCESS.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))) {
			SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("activity.credit");
			if (systemConfigBean!=null) {
				try {
					leaguerService.addLeaguerCredit(activityParticipateBean.getLeaguerId(), Integer.parseInt(systemConfigBean.getPropertyValue()));
				} catch (LogicException e) {
					logger.warn("会员["+activityParticipateBean.getLeaguerId()+"]提升信用["+systemConfigBean.getPropertyValue()+"],"+e.getErrContent());
				} catch (Exception e) {
					logger.warn("会员["+activityParticipateBean.getLeaguerId()+"]提升信用["+systemConfigBean.getPropertyValue()+"]失败");
					e.printStackTrace();
				}
			}
		}
		if (ActivityParticipateStatusEnum.SUCCESS.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))
				|| ActivityParticipateStatusEnum.FAKE.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))){
			List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.ACTIVITY.getCode());
			if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
				return;
			}
			if (integrationEventBeanList.size()>1) {
				logger.error("参与活动事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(activityParticipateBean.getLeaguerId());
				event.setData(ActivityBean.get(ActivityBean.class, activityParticipateBean.getActivityId()));
				if(ActivityParticipateStatusEnum.FAKE.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))
						&& ActivityParticipateStatusEnum.SUCCESS.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityParticipateBean.getStatus()))){
					event.rollBack();
				}else if(ActivityParticipateStatusEnum.SUCCESS.equals(ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(activityLeaguerBean.getStatus()))){
					event.happened();
				}
			}
		}
		int row = activityLeaguerBean.update();
		if (row!=1) {
			throw new LogicException("E002", "活动参与审核失败");
		}
	}

	@Override
	public Page<ActivityParticipateStatisticsResult> queryActivityParticipateStatisticsList(ActivityParticipateStatisticsQueryForm form) {
		Page<ActivityParticipateStatisticsResult> page = new Page<ActivityParticipateStatisticsResult>();
		PageHelper.orderBy(String.format("a.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ActivityParticipateStatisticsResult> activityParticipateStatisticsResultList = activityDao.queryActivityParticipateStatisticsList(form);
		PageInfo<ActivityParticipateStatisticsResult> pageInfo = new PageInfo<ActivityParticipateStatisticsResult>(activityParticipateStatisticsResultList);
		if (ListTools.isEmptyOrNull(activityParticipateStatisticsResultList)) {
			page.setMessage("没有查询到相关活动参与统计数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(activityParticipateStatisticsResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public ActivityParticipateStatisticsResult queryActivityParticipateStatistics(Long activityId) {
		ActivityParticipateStatisticsQueryForm form = new ActivityParticipateStatisticsQueryForm();
		form.setActivityId(activityId);
		List<ActivityParticipateStatisticsResult> activityParticipateStatisticsResultList = activityDao.queryActivityParticipateStatisticsList(form);
		if (ListTools.isEmptyOrNull(activityParticipateStatisticsResultList)) {
			return null;
		}
		return activityParticipateStatisticsResultList.get(0);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void changeActivityStatus(Long id, ActivityStatusEnum status) {
		ActivityBean activityBean = new ActivityBean();
		activityBean.setId(id);
		activityBean.setStatus(status.getCode());
		int row = activityBean.update();
		if (row!=1) {
			throw new LogicException("E001", "变更活动状态失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteActivity(Long id) {
		Example example = new Example(ActivityPlotBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("activityId", id);
		ActivityPlotBean.deleteByExample(ActivityPlotBean.class, example);
		Example example2 = new Example(ActivityImageSampleBean.class);
		Criteria criteria2 = example2.createCriteria();
		criteria2.andEqualTo("activityId", id);
		ActivityImageSampleBean.deleteByExample(ActivityImageSampleBean.class, example2);
		ActivityBean activityBean = new ActivityBean();
		activityBean.setId(id);
		int row = activityBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除活动失败");
		}
	}

}
