package com.cc.api.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.cc.activity.bean.ActivityImageBean;
import com.cc.activity.bean.ActivityImageSampleBean;
import com.cc.activity.bean.ActivityLocationBean;
import com.cc.activity.bean.ActivityPlotBean;
import com.cc.activity.form.ActivityParticipateForm;
import com.cc.activity.form.ActivityQueryForm;
import com.cc.activity.result.ActivityResult;
import com.cc.activity.service.ActivityService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.location.bean.LocationBean;

@Controller
@RequestMapping("/api/activity")
public class ApiActivityController {
	
	@Autowired 
	private ActivityService activityService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
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
			response.setMessage("活动不存在或已删除");
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
		return activityService.queryActivityPage(form);
	}

	/**
	 * 参与活动
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/participate", method = RequestMethod.POST)
	public Response<String> participateActivity(@RequestBody ActivityParticipateForm form){
		Response<String> response = new Response<String>();
		if (form.getActivityId()==null) {
			response.setMessage("请选择活动");
			return response;
		}
		ActivityBean activityBean = ActivityBean.get(ActivityBean.class, form.getActivityId());
		if (activityBean==null) {
			response.setMessage("活动不存在或已删除");
			return response;
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, form.getLeaguerId());
		Float credit = 0f;
		SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("credit.autoAudit");
		if (systemConfigBean!=null) {
			credit = Float.parseFloat(systemConfigBean.getPropertyValue());
		}
		if (activityBean.getAutoAudit() && credit.compareTo(leaguerBean.getCredit())<=0) {
			form.setAutoAudit(Boolean.TRUE);
		}else {
			form.setAutoAudit(Boolean.FALSE);
		}
		try {
			activityService.participateActivity(form);
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
