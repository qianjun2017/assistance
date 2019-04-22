/**
 * 
 */
package com.cc.activity.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.activity.bean.ActivityBean;
import com.cc.activity.bean.ActivityChannelBean;
import com.cc.activity.bean.ActivityPlotBean;
import com.cc.activity.bean.ActivityImageBean;
import com.cc.activity.bean.ActivityImageSampleBean;
import com.cc.activity.bean.ActivityLeaguerBean;
import com.cc.activity.bean.ActivityLeaguerImageBean;
import com.cc.activity.bean.ActivityLocationBean;
import com.cc.activity.enums.ActivityParticipateStatusEnum;
import com.cc.activity.enums.ActivityStatusEnum;
import com.cc.activity.enums.ActivityTypeEnum;
import com.cc.activity.form.ActivityForm;
import com.cc.activity.form.ActivityImageSampleForm;
import com.cc.activity.form.ActivityParticipateForm;
import com.cc.activity.form.ActivityParticipateQueryForm;
import com.cc.activity.form.ActivityParticipateStatisticsQueryForm;
import com.cc.activity.form.ActivityQueryForm;
import com.cc.activity.result.ActivityParticipateResult;
import com.cc.activity.result.ActivityParticipateStatisticsResult;
import com.cc.activity.result.ActivityResult;
import com.cc.activity.service.ActivityService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/activity")
public class ActivityController {

	@Autowired 
	private ActivityService activityService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 查活动类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public Response<Map<String, String>> queryActivityType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> activityTypeMap = ActivityTypeEnum.getActivityTypeMap();
		if (activityTypeMap==null || activityTypeMap.isEmpty()) {
			response.setMessage("暂无活动类型数据");
			return response;
		}
		response.setData(activityTypeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 新增活动
	 * @param activityMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.ADD, title="新增活动")
	public Response<String> addActivity(@RequestBody Map<String, Object> activityMap){
		Response<String> response = new Response<String>();
		ActivityForm activity = JsonTools.toObject(JsonTools.toJsonString(activityMap), ActivityForm.class);
		if (StringTools.isNullOrNone(activity.getName())) {
			response.setMessage("请输入活动名称");
			return response;
		}
		if (StringTools.isNullOrNone(activity.getPlot())) {
			response.setMessage("请输入活动介绍");
			return response;
		}
		if (StringTools.isNullOrNone(activity.getType())) {
			response.setMessage("请选择活动类型");
			return response;
		}
		if (activity.getIntegration()==null) {
			response.setMessage("请输入活动积分");
			return response;
		}
		if (ListTools.isEmptyOrNull(activity.getChannelList())) {
			response.setMessage("至少选择一个频道");
			return response;
		}
		if (ListTools.isEmptyOrNull(activity.getImageUrlList())) {
			response.setMessage("至少一张活动图片");
			return response;
		}
		List<ActivityImageSampleForm> sampleImageList = activity.getSampleImageList();
		if (!ListTools.isEmptyOrNull(sampleImageList)) {
			for (ActivityImageSampleForm activityImageSampleForm : sampleImageList) {
				if(StringTools.isNullOrNone(activityImageSampleForm.getImageUrl())){
					response.setMessage("请选择活动样例图片");
					return response;
				}
			}
		}else {
			response.setMessage("至少一张活动样例图片");
			return response;
		}
		try {
			activityService.saveActivity(activity);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 修改活动
	 * @param activityMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.edit" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.UPDATE, title="修改活动")
	public Response<String> updateActivity(@RequestBody Map<String, Object> activityMap){
		Response<String> response = new Response<String>();
		ActivityForm activity = JsonTools.toObject(JsonTools.toJsonString(activityMap), ActivityForm.class);
		if (activity.getId()==null) {
			response.setMessage("缺少活动主键");
			return response;
		}
		if (StringTools.isNullOrNone(activity.getName())) {
			response.setMessage("请输入活动名称");
			return response;
		}
		if (StringTools.isNullOrNone(activity.getPlot())) {
			response.setMessage("请输入活动介绍");
			return response;
		}
		if (StringTools.isNullOrNone(activity.getType())) {
			response.setMessage("请选择活动类型");
			return response;
		}
		if (activity.getIntegration()==null) {
			response.setMessage("请输入活动积分");
			return response;
		}
		if (ListTools.isEmptyOrNull(activity.getChannelList())) {
			response.setMessage("至少选择一个频道");
			return response;
		}
		if (ListTools.isEmptyOrNull(activity.getImageUrlList())) {
			response.setMessage("至少一张活动图片");
			return response;
		}
		List<ActivityImageSampleForm> sampleImageList = activity.getSampleImageList();
		if (!ListTools.isEmptyOrNull(sampleImageList)) {
			for (ActivityImageSampleForm activityImageSampleForm : sampleImageList) {
				if(StringTools.isNullOrNone(activityImageSampleForm.getImageUrl())){
					response.setMessage("请选择活动样例图片"+(StringTools.isNullOrNone(activityImageSampleForm.getName())?"":("["+activityImageSampleForm.getName()+"]")));
					return response;
				}
			}
		}
		try {
			activityService.saveActivity(activity);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询活动详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ActivityResult> queryActivity(@PathVariable Long id){
		Response<ActivityResult> response = new Response<ActivityResult>();
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, id);
		if (activityBean==null) {
			response.setMessage("活动不存在");
			return response;
		}
		ActivityResult activityResult = new ActivityResult();
		activityResult.setActivity(activityBean);
		List<ActivityLocationBean> activityLocationBeanList = ActivityLocationBean.findAllByParams(ActivityLocationBean.class, "activityId", activityBean.getId());
        if(!ListTools.isEmptyOrNull(activityLocationBeanList)){
        	ActivityLocationBean activityLocationBean = activityLocationBeanList.get(0);
            if(activityLocationBean!=null){
            	LocationBean locationBean = LocationBean.get(LocationBean.class, activityLocationBean.getLocationId());
				if(locationBean!=null){
					activityResult.setLocationBean(locationBean);
					Long[] locationArray = new Long[locationBean.getLevel()+1];
					while(locationBean.getParentId()!=null){
						locationArray[locationBean.getLevel()] = locationBean.getId();
						locationBean = LocationBean.get(LocationBean.class, locationBean.getParentId());
					}
					locationArray[0] = locationBean.getId();
					activityResult.setLocationArray(locationArray);
				}
            	
            }
        }
		List<ActivityPlotBean> activityPlotBeanList = ActivityPlotBean.findAllByParams(ActivityPlotBean.class, "activityId", activityBean.getId());
		if (!ListTools.isEmptyOrNull(activityPlotBeanList)) {
			ActivityPlotBean activityPlotBean = activityPlotBeanList.get(0);
			if(activityPlotBean.getDescription()!=null){
				activityResult.setDescription(new String(activityPlotBean.getDescription()));
			}
			if(activityPlotBean.getPlot()!=null){
				activityResult.setPlot(new String(activityPlotBean.getPlot()));
			}
		}
		List<ActivityChannelBean> activityChannelList = ActivityChannelBean.findAllByParams(ActivityChannelBean.class, "activityId", activityBean.getId());
		if(!ListTools.isEmptyOrNull(activityChannelList)){
			activityResult.setChannelList(activityChannelList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
		}
		activityResult.setActivityImageList(ActivityImageBean.findAllByParams(ActivityImageBean.class, "activityId", activityBean.getId()));
		activityResult.setActivityImageSampleList(ActivityImageSampleBean.findAllByParams(ActivityImageSampleBean.class, "activityId", activityBean.getId()));
		response.setData(activityResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询活动
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryActivityPage(@ModelAttribute ActivityQueryForm form){
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			page.setMessage("请先登录");
			return page;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				page.setMessage("查询活动失败");
				return page;
			}
			form.setStatus(ActivityStatusEnum.ON.getCode());
			LeaguerBean leaguerBean = leaguerBeanList.get(0);
			form.setLeaguerId(leaguerBean.getId());
			Long locationId = leaguerBean.getLocationId();
			List<Long> locationIdList = new ArrayList<Long>();
			while(locationId!=null){
				LocationBean locationBean = LocationBean.get(LocationBean.class, locationId);
				if(locationBean!=null){
					locationIdList.add(locationBean.getId());
					locationId = locationBean.getParentId();
				}else{
					break;
				}
			}
			if(ListTools.isEmptyOrNull(locationIdList)){
				page.setMessage("没有查询到符合条件的活动数据");
				return page;
			}
			form.setLocationIdList(locationIdList);
		}
		page = activityService.queryActivityPage(form);
		return page;
	}
	
	/**
	 * 参与活动
	 * @param participateMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.PARTICIPATE, title="参与活动")
	public Response<String> participateActivity(@RequestBody Map<String, Object> participateMap){
		Response<String> response = new Response<String>();
		ActivityParticipateForm activityParticipate = JsonTools.toObject(JsonTools.toJsonString(participateMap), ActivityParticipateForm.class);
		if (activityParticipate.getActivityId()==null) {
			response.setMessage("请选择活动");
			return response;
		}
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, activityParticipate.getActivityId());
		if (activityBean==null) {
			response.setMessage("活动不存在");
			return response;
		}
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				response.setMessage("参与活动失败");
				return response;
			}
			LeaguerBean leaguerBean = leaguerBeanList.get(0);
			activityParticipate.setLeaguerId(leaguerBean.getId());
			Float credit = 0f;
			SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("credit.autoAudit");
			if (systemConfigBean!=null) {
				credit = Float.parseFloat(systemConfigBean.getPropertyValue());
			}
			if (activityBean.getAutoAudit() && credit.compareTo(leaguerBean.getCredit())<=0) {
				activityParticipate.setAutoAudit(Boolean.TRUE);
			}else {
				activityParticipate.setAutoAudit(Boolean.FALSE);
			}
		}else {
			response.setMessage("参与活动失败");
			return response;
		}
		try {
			activityService.participateActivity(activityParticipate);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询活动参与情况
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate/page", method = RequestMethod.GET)
	public Page<ActivityParticipateResult> queryActivityParticipatePage(@ModelAttribute ActivityParticipateQueryForm form){
		Page<ActivityParticipateResult> page = activityService.queryActivityParticipatePage(form);
		return page;
	}
	
	/**
	 * 查询活动参与详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryActivityParticipate(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String,Object>>();
		ActivityLeaguerBean activityLeaguerBean = ActivityLeaguerBean.get(ActivityLeaguerBean.class, id);
		if (activityLeaguerBean==null) {
			response.setMessage("活动参与不存在");
			return response;
		}
		Map<String, Object> activityParticipate = new HashMap<String, Object>();
		activityParticipate.put("id", activityLeaguerBean.getId());
		activityParticipate.put("status", activityLeaguerBean.getStatus());
		activityParticipate.put("statusName", ActivityParticipateStatusEnum.getNameByCode(activityLeaguerBean.getStatus()));
		activityParticipate.put("remark", activityLeaguerBean.getRemark());
		activityParticipate.put("participateTime", DateTools.getFormatDate(activityLeaguerBean.getCreateTime(), DateTools.DATEFORMAT));
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, activityLeaguerBean.getActivityId());
		if (activityBean!=null) {
			ActivityResult activityResult = new ActivityResult();
			activityResult.setActivity(activityBean);
			List<ActivityLocationBean> activityLocationBeanList = ActivityLocationBean.findAllByParams(ActivityLocationBean.class, "activityId", activityBean.getId());
	        if(!ListTools.isEmptyOrNull(activityLocationBeanList)){
	        	ActivityLocationBean activityLocationBean = activityLocationBeanList.get(0);
	            if(activityLocationBean!=null){
	            	activityResult.setLocationBean(LocationBean.get(LocationBean.class, activityLocationBean.getLocationId()));
	            }
	        }
			List<ActivityPlotBean> activityPlotBeanList = ActivityPlotBean.findAllByParams(ActivityPlotBean.class, "activityId", activityBean.getId());
			if (!ListTools.isEmptyOrNull(activityPlotBeanList)) {
				ActivityPlotBean activityPlotBean = activityPlotBeanList.get(0);
				activityResult.setDescription(new String(activityPlotBean.getDescription()));
				activityResult.setPlot(new String(activityPlotBean.getPlot()));
			}
			activityResult.setActivityImageList(
					ActivityImageBean.findAllByParams(ActivityImageBean.class, "activityId", activityBean.getId()));
			activityResult.setActivityImageSampleList(ActivityImageSampleBean
					.findAllByParams(ActivityImageSampleBean.class, "activityId", activityBean.getId()));
			activityParticipate.put("activity", activityResult);
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, activityLeaguerBean.getLeaguerId());
		if (leaguerBean!=null) {
			Map<String, Object> leaguer = new HashMap<String, Object>();
			leaguer.put("name", leaguerBean.getLeaguerName());
			leaguer.put("avatarUrl", leaguerBean.getAvatarUrl());
			LocationBean locationBean = LocationBean.get(LocationBean.class, leaguerBean.getLocationId());
			if(locationBean!=null){
				leaguer.put("location", locationBean.getLocationNamePath());
			}
			leaguer.put("credit", leaguerBean.getCredit());
			activityParticipate.put("leaguer", leaguer);
		}
		List<ActivityLeaguerImageBean> activityLeaguerImageBeanList = ActivityLeaguerImageBean.findAllByParams(ActivityLeaguerImageBean.class, "participateId", id);
		activityParticipate.put("activityLeaguerImageBeanList", activityLeaguerImageBeanList);
		response.setData(activityParticipate);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 审核活动参与
	 * @param auditMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.participate.audit" })
	@RequestMapping(value = "/participate/audit", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.AUDIT, title = "审核活动参与")
	public Response<String> auditActivityParticipate(@RequestBody Map<String, String> auditMap){
		Response<String> response = new Response<String>();
		String id = auditMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少活动参与主键");
			return response;
		}
		ActivityLeaguerBean activityLeaguerBean = new ActivityLeaguerBean();
		activityLeaguerBean.setId(Long.valueOf(id));
		String status = auditMap.get("status");
		if (StringTools.isNullOrNone(status)) {
			response.setMessage("请选择审核状态");
			return response;
		}
		try {
			ActivityParticipateStatusEnum activityParticipateStatusEnum = ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(status);
			if (activityParticipateStatusEnum==null) {
				response.setMessage("审核状态不合法");
				return response;
			}
			activityLeaguerBean.setStatus(status);
			String remark = auditMap.get("remark");
			if ((ActivityParticipateStatusEnum.REJECT.equals(activityParticipateStatusEnum) 
					|| ActivityParticipateStatusEnum.FAKE.equals(activityParticipateStatusEnum))
					&& StringTools.isNullOrNone(remark)) {
				response.setMessage("请输入审批说明");
				return response;
			}
			activityLeaguerBean.setRemark(remark);
			activityService.auditActivityParticipate(activityLeaguerBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询活动参与统计情况
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate/statistics/page", method = RequestMethod.GET)
	public Page<ActivityParticipateStatisticsResult> queryActivityParticipateStatisticsPage(@ModelAttribute ActivityParticipateStatisticsQueryForm form){
		Page<ActivityParticipateStatisticsResult> page = activityService.queryActivityParticipateStatisticsList(form);
		return page;
	}
	
	/**
	 * 查询活动参与统计情况
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate/statistics/{id:\\d+}", method = RequestMethod.GET)
	public Response<ActivityParticipateStatisticsResult> queryActivityParticipateStatistics(@PathVariable Long id){
		Response<ActivityParticipateStatisticsResult> response = new Response<ActivityParticipateStatisticsResult>();
		ActivityParticipateStatisticsResult activityParticipateStatisticsResult = activityService.queryActivityParticipateStatistics(id);
		if(activityParticipateStatisticsResult==null){
			response.setMessage("无活动参与统计情况");
			return response;
		}
		response.setData(activityParticipateStatisticsResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 上架活动
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.up" })
	@RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.UP, title = "上架活动", paramNames = {"id"})
	public Response<String> upActivity(@PathVariable Long id){
		Response<String> response = new Response<String>();
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, id);
		if(activityBean==null){
			response.setMessage("活动不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("上架活动["+activityBean.getName()+"]");
			activityService.changeActivityStatus(id, ActivityStatusEnum.ON);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 下架活动
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.down" })
	@RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架活动", paramNames = {"id"})
	public Response<String> downActivity(@PathVariable Long id){
		Response<String> response = new Response<String>();
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, id);
		if(activityBean==null){
			response.setMessage("活动不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("下架活动["+activityBean.getName()+"]");
			activityService.changeActivityStatus(id, ActivityStatusEnum.DOWN);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 删除活动
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "activity.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.ACTIVITYMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除活动", paramNames = {"id"})
	public Response<String> deleteActivity(@PathVariable Long id){
		Response<String> response = new Response<String>();
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, id);
		if(activityBean==null){
			response.setMessage("活动不存在");
			return response;
		}
		if (ActivityStatusEnum.ON.getCode().equals(activityBean.getStatus())) {
			response.setMessage("活动上架中，不能删除");
			return response;
		}
		try {
			LogContextUtil.setOperContent("删除活动["+activityBean.getName()+"]");
			activityService.deleteActivity(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
