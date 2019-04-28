package com.cc.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.bean.ChannelSubjectBean;
import com.cc.channel.enums.ChannelTypeEnum;
import com.cc.channel.form.ChannelSubjectItemQueryForm;
import com.cc.channel.form.ChannelSubjectQueryForm;
import com.cc.channel.result.ChannelSubjectItemResult;
import com.cc.channel.service.ChannelSubjectService;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.service.LeaguerChannelService;
import com.cc.system.location.bean.LocationBean;

@Controller
@RequestMapping("/api/channel/subject")
public class ApiChannelSubjectController {
	
	@Autowired
	private LeaguerChannelService leaguerChannelService;
	
	@Autowired
	private ChannelSubjectService channelSubjectService;

	/**
	 * 获取频道专题
	 * @param id
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ChannelSubjectBean> queryChannelSubject(@PathVariable Long id, @RequestParam(required = true) Long leaguerId){
		Response<ChannelSubjectBean> response = new Response<ChannelSubjectBean>();
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, id);
		if (channelSubjectBean==null) {
			response.setMessage("专题不存在或已删除");
			return response;
		}
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelSubjectBean.getChannelId());
		if (channelBean==null) {
			response.setMessage("专题不存在或已删除");
			return response;
		}
		List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerId);
		if(ListTools.isEmptyOrNull(leaguerChannelBeanList)){
			response.setMessage("专题不存在或已删除");
			return response;
		}
		for(LeaguerChannelBean leaguerChannelBean: leaguerChannelBeanList){
			if(leaguerChannelBean.getChannelId().equals(channelBean.getId())){
				response.setData(channelSubjectBean);
				response.setSuccess(Boolean.TRUE);
				return response;
			}
		}
		response.setMessage("专题不存在或已删除");
		return response;
	}
	
	/**
	 * 查询频道专题列表
	 * @param channelId
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Response<Object> queryChannelSubjectList(@RequestParam(required = true) Long channelId, @RequestParam(required = true) Long leaguerId){
		Response<Object> response = new Response<Object>();
		ChannelSubjectQueryForm form = new ChannelSubjectQueryForm();
		form.setChannelId(channelId);
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, form.getChannelId());
		if (channelBean==null) {
			response.setMessage("没有查询到相关专题数据");
			return response;
		}
		List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerId);
		if(ListTools.isEmptyOrNull(leaguerChannelBeanList)){
			response.setMessage("没有查询到相关专题数据");
			return response;
		}
		List<ChannelSubjectBean> channelSubjectList = channelSubjectService.queryChannelSubjectList(channelId);
		if(ListTools.isEmptyOrNull(channelSubjectList)){
			response.setMessage("没有查询到相关专题数据");
			return response;
		}
		for(LeaguerChannelBean leaguerChannelBean: leaguerChannelBeanList){
			if(leaguerChannelBean.getChannelId().equals(channelBean.getId())){
				response.setData(channelSubjectList);
				response.setSuccess(Boolean.TRUE);
				return response;
			}
		}
		response.setMessage("没有查询到相关专题数据");
		return response;
	}
	
	/**
	 * 查询频道专题内容列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/item/list", method = RequestMethod.GET)
	public Response<Object> queryChannelSubjectItemList(@RequestParam(required = true) Long subjectId, @RequestParam(required = true) Long leaguerId){
		Response<Object> response = new Response<Object>();
		ChannelSubjectItemQueryForm form = new ChannelSubjectItemQueryForm();
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, subjectId);
		if(channelSubjectBean==null){
			response.setMessage("没有查询到符合条件的专题数据");
			return response;
		}
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelSubjectBean.getChannelId());
		if (channelBean==null) {
			response.setMessage("没有查询到符合条件的专题数据");
			return response;
		}
		form.setChannelCode(channelBean.getChannelCode());
		ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getChannelTypeEnumByCode(channelBean.getChannelType());
		if(ChannelTypeEnum.COMPREHENSIVE.equals(channelTypeEnum)){
			form.setOrdinary(Boolean.TRUE);
			List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerId);
			if(ListTools.isEmptyOrNull(leaguerChannelBeanList)){
				response.setMessage("没有查询到符合条件的专题数据");
				return response;
			}
			form.setChannelIdList(leaguerChannelBeanList.stream().map(leaguerChannelBean->leaguerChannelBean.getChannelId()).collect(Collectors.toList()));
			form.setLocation(Boolean.TRUE);
			LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, leaguerId);
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
				response.setMessage("没有查询到符合条件的专题数据");
				return response;
			}
			form.setLocationIdList(locationIdList);
		}
		List<ChannelSubjectItemResult> channelSubjectItemList = channelSubjectService.queryChannelSubjectItemList(form);
		if(ListTools.isEmptyOrNull(channelSubjectItemList)) {
			response.setMessage("没有查询到符合条件的专题数据");
			return response;
		}
		response.setData(channelSubjectItemList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
