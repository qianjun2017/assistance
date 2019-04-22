/**
 * 
 */
package com.cc.channel.web;

import java.util.ArrayList;
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

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.bean.ChannelSubjectBean;
import com.cc.channel.bean.ChannelSubjectItemBean;
import com.cc.channel.enums.ChannelStatusEnum;
import com.cc.channel.enums.ChannelSubjectStatusEnum;
import com.cc.channel.form.ChannelSubjectItemQueryForm;
import com.cc.channel.form.ChannelSubjectQueryForm;
import com.cc.channel.result.ChannelSubjectItemResult;
import com.cc.channel.result.ChannelSubjectResult;
import com.cc.channel.service.ChannelSubjectService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.service.LeaguerChannelService;
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
@RequestMapping("/channel/subject")
public class ChannelSubjectController {

	@Autowired
	private ChannelSubjectService channelSubjectService;
	
	@Autowired
	private LeaguerChannelService leaguerChannelService;
	
	/**
	 * 新增频道专题
	 * @param subjectMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增专题")
	public Response<Long> addChannelSubject(@RequestBody Map<String, Object> subjectMap){
		Response<Long> response = new Response<Long>();
		ChannelSubjectBean channelSubjectBean = new ChannelSubjectBean();
		Object subjectName = subjectMap.get("subjectName");
		if (subjectName == null || StringTools.isNullOrNone(StringTools.toString(subjectName))) {
			response.setMessage("请输入专题名称");
			return response;
		}
		channelSubjectBean.setSubjectName(StringTools.toString(subjectName));
		Object channel = subjectMap.get("channelId");
		if (channel == null || StringTools.isNullOrNone(StringTools.toString(channel))) {
			response.setMessage("请选择频道");
			return response;
		}
		Long channelId = Long.valueOf(StringTools.toString(channel));
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelId);
		if(channelBean==null || !ChannelStatusEnum.NORMAL.equals(ChannelStatusEnum.getChannelStatusEnumByCode(channelBean.getStatus()))){
			response.setMessage("频道不存在或已关闭,请重新选择");
			return response;
		}
		channelSubjectBean.setChannelId(channelId);
		Object cover = subjectMap.get("cover");
		if (cover != null) {
			channelSubjectBean.setCover(StringTools.toString(cover));
			Object height = subjectMap.get("height");
			if (height == null || StringTools.isNullOrNone(StringTools.toString(height))) {
				response.setMessage("请输入封面高度");
				return response;
			}
			channelSubjectBean.setHeight(Long.valueOf(StringTools.toString(height)));
		}
		Object text = subjectMap.get("text");
		if(text==null){
			response.setMessage("请选择是否展示文本");
			return response;
		}
		channelSubjectBean.setText(Boolean.valueOf(StringTools.toString(text)));
		Object single = subjectMap.get("single");
		if(single==null){
			response.setMessage("请选择是否单行展示");
			return response;
		}
		channelSubjectBean.setSingle(Boolean.valueOf(StringTools.toString(single)));
		if(!channelSubjectBean.getSingle()){
			Object number = subjectMap.get("number");
			if (number == null || StringTools.isNullOrNone(StringTools.toString(number))) {
				response.setMessage("请输入每行展示数量");
				return response;
			}
			channelSubjectBean.setNumber(Integer.valueOf(StringTools.toString(number)));
		}
		Object scale = subjectMap.get("scale");
		if (scale == null || StringTools.isNullOrNone(StringTools.toString(scale))) {
			response.setMessage("请输入展示规格");
			return response;
		}
		channelSubjectBean.setScale(StringTools.toString(scale));
		try {
			channelSubjectBean.setStatus(ChannelSubjectStatusEnum.DRAFT.getCode());
			channelSubjectBean.setCreateTime(DateTools.now());
			channelSubjectService.saveChannelSubject(channelSubjectBean);
			response.setData(channelSubjectBean.getId());
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
	 * 修改频道专题
	 * @param subjectMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改专题")
	public Response<Long> updateChannelSubject(@RequestBody Map<String, Object> subjectMap){
		Response<Long> response = new Response<Long>();
		Object id = subjectMap.get("id");
		if(id == null || StringTools.isNullOrNone(StringTools.toString(id))){
			response.setMessage("缺少专题主键");
			return response;
		}
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, Long.valueOf(StringTools.toString(id)));
		if (channelSubjectBean==null) {
			response.setMessage("专题不存在");
			return response;
		}
		Object subjectName = subjectMap.get("subjectName");
		if (subjectName == null || StringTools.isNullOrNone(StringTools.toString(subjectName))) {
			response.setMessage("请输入专题名称");
			return response;
		}
		channelSubjectBean.setSubjectName(StringTools.toString(subjectName));
		Object channel = subjectMap.get("channelId");
		if (channel == null || StringTools.isNullOrNone(StringTools.toString(channel))) {
			response.setMessage("请选择频道");
			return response;
		}
		Long channelId = Long.valueOf(StringTools.toString(channel));
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelId);
		if(channelBean==null || !ChannelStatusEnum.NORMAL.equals(ChannelStatusEnum.getChannelStatusEnumByCode(channelBean.getStatus()))){
			response.setMessage("频道不存在或已关闭,请重新选择");
			return response;
		}
		channelSubjectBean.setChannelId(channelId);
		Object cover = subjectMap.get("cover");
		if (cover != null) {
			channelSubjectBean.setCover(StringTools.toString(cover));
			Object height = subjectMap.get("height");
			if (height == null || StringTools.isNullOrNone(StringTools.toString(height))) {
				response.setMessage("请输入封面高度");
				return response;
			}
			channelSubjectBean.setHeight(Long.valueOf(StringTools.toString(height)));
		}else{
			channelSubjectBean.setCover(null);
			channelSubjectBean.setHeight(null);
		}
		Object text = subjectMap.get("text");
		if(text==null){
			response.setMessage("请选择是否展示文本");
			return response;
		}
		channelSubjectBean.setText(Boolean.valueOf(StringTools.toString(text)));
		Object single = subjectMap.get("single");
		if(single==null){
			response.setMessage("请选择是否单行展示");
			return response;
		}
		channelSubjectBean.setSingle(Boolean.valueOf(StringTools.toString(single)));
		if(!channelSubjectBean.getSingle()){
			Object number = subjectMap.get("number");
			if (number == null || StringTools.isNullOrNone(StringTools.toString(number))) {
				response.setMessage("请输入每行展示数量");
				return response;
			}
			channelSubjectBean.setNumber(Integer.valueOf(StringTools.toString(number)));
		}else{
			channelSubjectBean.setNumber(null);
		}
		Object scale = subjectMap.get("scale");
		if (scale == null || StringTools.isNullOrNone(StringTools.toString(scale))) {
			response.setMessage("请输入展示规格");
			return response;
		}
		channelSubjectBean.setScale(StringTools.toString(scale));
		try {
			channelSubjectService.saveChannelSubject(channelSubjectBean);
			response.setData(channelSubjectBean.getId());
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
	 * 删除频道专题
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除专题", paramNames = {"id"})
	public Response<String> deleteChannelSubject(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelSubjectService.deleteChannelSubject(id);
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
	 * 获取频道专题
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ChannelSubjectBean> queryChannelSubject(@PathVariable Long id){
		Response<ChannelSubjectBean> response = new Response<ChannelSubjectBean>();
		try {
			ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, id);
			if (channelSubjectBean==null) {
				response.setMessage("专题不存在");
				return response;
			}
			response.setData(channelSubjectBean);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 分页查询频道专题列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<ChannelSubjectResult> queryChannelSubjectPage(@ModelAttribute ChannelSubjectQueryForm form){
		if(!StringTools.isNullOrNone(form.getChannelCode())){
			List<ChannelBean> channelList = ChannelBean.findAllByParams(ChannelBean.class, "channelCode", form.getChannelCode());
			if(!ListTools.isEmptyOrNull(channelList)){
				form.setChannelId(channelList.get(0).getId());
			}
		}
		return channelSubjectService.queryChannelSubjectPage(form);
	}
	
	/**
	 * 新增频道专题内容
	 * @param subjectItemMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.item.add" })
	@RequestMapping(value = "/item/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增专题内容")
	public Response<String> addChannelSubjectItem(@RequestBody Map<String, Object> subjectItemMap){
		Response<String> response = new Response<String>();
		List<ChannelSubjectItemBean> channelSubjectItemBeanList = new ArrayList<ChannelSubjectItemBean>();
		Object subject = subjectItemMap.get("subjectId");
		if (subject == null) {
			response.setMessage("请选择专题");
			return response;
		}
		Long subjectId = Long.valueOf(StringTools.toString(subject));
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, subjectId);
		if(channelSubjectBean==null){
			response.setMessage("专题不存在");
			return response;
		}
		Object object = subjectItemMap.get("subjectItemList");
		if (object==null) {
			response.setMessage("请选择专题内容");
			return response;
		}
		List<Integer> subjectItemList = (List<Integer>) object;
		if (ListTools.isEmptyOrNull(subjectItemList)) {
			response.setMessage("请选择专题内容");
			return response;
		}
		for (Integer subjectItem : subjectItemList) {
			ChannelSubjectItemBean channelSubjectItemBean = new ChannelSubjectItemBean();
			channelSubjectItemBean.setSubjectId(subjectId);
			channelSubjectItemBean.setItemId(Long.valueOf(StringTools.toString(subjectItem)));
			channelSubjectItemBeanList.add(channelSubjectItemBean);
		}
		try {
			channelSubjectService.saveChannelSubjectItemList(channelSubjectItemBeanList);
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
	 * 删除频道专题内容
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.item.delete" })
	@RequestMapping(value = "/item/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除专题内容", paramNames = {"id"})
	public Response<String> deleteChannelSubjectItem(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelSubjectService.deleteChannelSubjectItem(id);
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
	 * 查询频道专题内容列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/item/list", method = RequestMethod.GET)
	public Response<Object> queryChannelSubjectItemList(@ModelAttribute ChannelSubjectItemQueryForm form){
		Response<Object> response = new Response<Object>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				response.setMessage("查询活动失败");
				return response;
			}
			LeaguerBean leaguerBean = leaguerBeanList.get(0);
			if(form.getLocation()){
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
					response.setMessage("没有查询到符合条件的活动数据");
					return response;
				}
				form.setLocationIdList(locationIdList);
			}
			if(form.getOrdinary()){
				List<LeaguerChannelBean> leaguerChannelList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerBean.getId());
				if(!ListTools.isEmptyOrNull(leaguerChannelList)){
					form.setChannelIdList(leaguerChannelList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
				}
			}
		}else{
			if(form.getLocation()){
				Long locationId = form.getLocationId();
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
					response.setMessage("没有查询到符合条件的活动数据");
					return response;
				}
				form.setLocationIdList(locationIdList);
			}
		}
		List<ChannelSubjectItemResult> channelSubjectItemList = channelSubjectService.queryChannelSubjectItemList(form);
		if(ListTools.isEmptyOrNull(channelSubjectItemList)) {
			response.setMessage("没有查询到专题内容");
			return response;
		}
		response.setData(channelSubjectItemList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 上架专题
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.up" })
	@RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.UP, title = "上架专题", paramNames = {"id"})
	public Response<String> upChannelSubject(@PathVariable Long id){
		Response<String> response = new Response<String>();
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, id);
		if (channelSubjectBean==null) {
			response.setMessage("专题不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("上架专题["+channelSubjectBean.getSubjectName()+"]");
			channelSubjectService.changeChannelSubjectStatus(id, ChannelSubjectStatusEnum.ON);
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
	 * 下架专题
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.subject.down" })
	@RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELSUBJECTMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架专题", paramNames = {"id"})
	public Response<String> downChannelSubject(@PathVariable Long id){
		Response<String> response = new Response<String>();
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, id);
		if (channelSubjectBean==null) {
			response.setMessage("专题不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("下架专题["+channelSubjectBean.getSubjectName()+"]");
			channelSubjectService.changeChannelSubjectStatus(id, ChannelSubjectStatusEnum.DOWN);
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
