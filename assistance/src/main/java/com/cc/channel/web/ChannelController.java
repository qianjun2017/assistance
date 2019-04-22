/**
 * 
 */
package com.cc.channel.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cc.channel.enums.ChannelStatusEnum;
import com.cc.channel.enums.ChannelTypeEnum;
import com.cc.channel.form.ChannelItemQueryForm;
import com.cc.channel.form.ChannelQueryForm;
import com.cc.channel.result.ChannelItemResult;
import com.cc.channel.service.ChannelService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.spider.bean.ChannelSpiderBean;
import com.cc.spider.service.ChannelSpiderService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/channel")
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelSpiderService channelSpiderService;
	
	/**
	 * 新增频道
	 * @param channelMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增频道")
	public Response<String> addChannel(@RequestBody Map<String, String> channelMap){
		Response<String> response = new Response<String>();
		ChannelBean channelBean = new ChannelBean();
		String channelName = channelMap.get("channelName");
		if (StringTools.isNullOrNone(channelName)) {
			response.setMessage("请输入频道名称");
			return response;
		}
		channelBean.setChannelName(channelName);
		String channelCode = channelMap.get("channelCode");
		if (StringTools.isNullOrNone(channelCode)) {
			response.setMessage("请输入频道代码");
			return response;
		}
		List<ChannelBean> channelBeanList = channelService.queryChannelBeanList(channelCode);
		if (!ListTools.isEmptyOrNull(channelBeanList)) {
			response.setMessage("频道代码重复，请重新输入");
			return response;
		}
		channelBean.setChannelCode(channelCode);
		channelBean.setChannelIcon(channelMap.get("channelIcon"));
		String channelType = channelMap.get("channelType");
		if (StringTools.isNullOrNone(channelType)) {
			response.setMessage("请选择频道类型");
			return response;
		}
		ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getChannelTypeEnumByCode(channelType);
		if(channelTypeEnum==null){
			response.setMessage("频道类型错误，请重新选择");
			return response;
		}
		channelBean.setChannelType(channelType);
		channelBean.setHome(Boolean.valueOf(channelMap.get("home")));
		channelBean.setMenu(Boolean.valueOf(channelMap.get("menu")));
		channelBean.setHot(Boolean.valueOf(channelMap.get("hot")));
		channelBean.setNews(Boolean.valueOf(channelMap.get("news")));
		channelBean.setRecommend(Boolean.valueOf(channelMap.get("recommend")));
		channelBean.setCarousel(Boolean.valueOf(channelMap.get("carousel")));
		channelBean.setSubject(Boolean.valueOf(channelMap.get("subject")));
		channelBean.setLocation(Boolean.valueOf(channelMap.get("location")));
		channelBean.setOrdinary(Boolean.valueOf(channelMap.get("ordinary")));
		channelBean.setSearch(Boolean.valueOf(channelMap.get("search")));
		if(channelBean.getCarousel()){
			channelBean.setHeight(Long.valueOf(channelMap.get("height")));
		}
		channelBean.setPadding(Long.valueOf(channelMap.get("padding")));
		channelBean.setStatus(ChannelStatusEnum.NORMAL.getCode());
		try {
			channelService.saveChannel(channelBean);
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
	 * 修改频道
	 * @param channelMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.edit" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改频道")
	public Response<String> updateChannel(@RequestBody Map<String, String> channelMap){
		Response<String> response = new Response<String>();
		String id = channelMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("缺少频道主键");
			return response;
		}
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, Long.valueOf(id));
		if (channelBean==null) {
			response.setMessage("频道不存在");
			return response;
		}
		String channelName = channelMap.get("channelName");
		if (StringTools.isNullOrNone(channelName)) {
			response.setMessage("请输入频道名称");
			return response;
		}
		channelBean.setChannelName(channelName);
		String channelCode = channelMap.get("channelCode");
		if (StringTools.isNullOrNone(channelCode)) {
			response.setMessage("请输入频道代码");
			return response;
		}
		List<ChannelBean> channelBeanList = channelService.queryChannelBeanList(channelCode);
		if (!ListTools.isEmptyOrNull(channelBeanList)) {
			for(ChannelBean channel: channelBeanList){
				if(!channel.getId().equals(channelBean.getId())){
					response.setMessage("频道代码重复，请重新输入");
					return response;
				}
			}
		}
		channelBean.setChannelCode(channelCode);
		channelBean.setChannelIcon(channelMap.get("channelIcon"));
		String channelType = channelMap.get("channelType");
		if (StringTools.isNullOrNone(channelType)) {
			response.setMessage("请选择频道类型");
			return response;
		}
		ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getChannelTypeEnumByCode(channelType);
		if(channelTypeEnum==null){
			response.setMessage("频道类型错误，请重新选择");
			return response;
		}
		channelBean.setChannelType(channelType);
		channelBean.setHome(Boolean.valueOf(channelMap.get("home")));
		channelBean.setMenu(Boolean.valueOf(channelMap.get("menu")));
		channelBean.setHot(Boolean.valueOf(channelMap.get("hot")));
		channelBean.setNews(Boolean.valueOf(channelMap.get("news")));
		channelBean.setRecommend(Boolean.valueOf(channelMap.get("recommend")));
		channelBean.setCarousel(Boolean.valueOf(channelMap.get("carousel")));
		channelBean.setSubject(Boolean.valueOf(channelMap.get("subject")));
		channelBean.setLocation(Boolean.valueOf(channelMap.get("location")));
		channelBean.setOrdinary(Boolean.valueOf(channelMap.get("ordinary")));
		channelBean.setSearch(Boolean.valueOf(channelMap.get("search")));
		if(channelBean.getCarousel()){
			channelBean.setHeight(Long.valueOf(channelMap.get("height")));
		}
		channelBean.setPadding(Long.valueOf(channelMap.get("padding")));
		try {
			channelService.saveChannel(channelBean);
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
	 * 删除频道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除频道", paramNames = {"id"})
	public Response<String> deleteChannel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelService.deleteChannel(id);
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
	 * 关闭频道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.close" })
	@RequestMapping(value = "/close/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.CLOSE, title = "关闭频道", paramNames = {"id"})
	public Response<String> closeChannel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelService.closeChannel(id);
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
	 * 开启频道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.open" })
	@RequestMapping(value = "/open/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.OPEN, title = "开启频道", paramNames = {"id"})
	public Response<String> openChannel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelService.openChannel(id);
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
	 * 分页查询频道
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryChannelPage(@ModelAttribute ChannelQueryForm form){
		Page<Map<String, Object>> page = channelService.queryChannelPage(form);
		return page;
	}
	
	/**
	 * 频道详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryChannel(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		try {
			Map<String, Object> data =  new HashMap<String, Object>();
			ChannelBean channelBean = ChannelBean.get(ChannelBean.class, id);
			if (channelBean==null) {
				response.setMessage("频道不存在");
				return response;
			}
			data.put("channelBean", channelBean);
			List<ChannelSpiderBean> channelSpiderBeanList = channelSpiderService.queryChannelSpiderBeanList(id);
			if (!ListTools.isEmptyOrNull(channelSpiderBeanList)) {
				data.put("channelSpiderBeanList", channelSpiderBeanList);
			}
			response.setData(data);
			response.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询频道爬虫
	 * @param channelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{channelId:\\d+}/spiders", method = RequestMethod.GET)
	public Response<Object> queryChannelSpiderList(@PathVariable Long channelId){
		Response<Object> response = new Response<Object>();
		List<ChannelSpiderBean> channelSpiderBeanList = channelSpiderService.queryChannelSpiderBeanList(channelId);
		response.setData(channelSpiderBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 新增频道爬虫
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.spider" })
	@RequestMapping(value = "/spider/add/{channelId}/{spiderNo}/{spiderType}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.AUTHORIZE,title = "新增频道爬虫", paramNames = {"channelId", "spiderNo", "spiderType"})
	public Response<String> addChannelSpider(@PathVariable Long channelId, @PathVariable String spiderNo, @PathVariable String spiderType){
		Response<String> response = new Response<String>();
		try {
			ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelId);
			if (channelBean==null) {
				response.setMessage("频道不存在");
				return response;
			}
			ChannelSpiderBean channelSpiderBean = new ChannelSpiderBean();
			channelSpiderBean.setSpiderNo(spiderNo);
			channelSpiderBean.setChannelId(channelId);
			channelSpiderBean.setSpiderType(spiderType);
			channelSpiderService.saveChannelSpider(channelSpiderBean);
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
	 * 删除频道爬虫
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "channel.spider" })
	@RequestMapping(value = "/spider/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CHANNELMANAGEMENT, operType = OperTypeEnum.AUTHORIZE, title = "删除频道爬虫", paramNames = {"id"})
	public Response<String> deleteChannelSpider(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			channelSpiderService.deleteChannelSpider(id);
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
	 * 查询所有的频道
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Response<Object> queryAllChannelList(){
		Response<Object> response = new Response<Object>();
		List<ChannelBean> channelBeanList = ChannelBean.findAllByParams(ChannelBean.class, "status", ChannelStatusEnum.NORMAL.getCode());
		if(ListTools.isEmptyOrNull(channelBeanList)){
			response.setMessage("没有查询频道信息");
			return response;
		}
		response.setData(channelBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询所有的普通频道
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ordinary/all", method = RequestMethod.GET)
	public Response<Object> queryAllOrdinaryChannelList(){
		Response<Object> response = new Response<Object>();
		List<ChannelBean> channelBeanList = ChannelBean.findAllByParams(ChannelBean.class, "status", ChannelStatusEnum.NORMAL.getCode(), "channelType", ChannelTypeEnum.ORDINARY.getCode());
		if(ListTools.isEmptyOrNull(channelBeanList)){
			response.setMessage("没有查询频道信息");
			return response;
		}
		response.setData(channelBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询频道数据
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/item/page", method = RequestMethod.GET)
	public Page<ChannelItemResult> queryChannelItemPage(@ModelAttribute ChannelItemQueryForm form){
		return channelService.queryChannelItemPage(form);
	}
}
