/**
 * 
 */
package com.cc.leaguer.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.enums.LeaguerStatusEnum;
import com.cc.leaguer.form.LeaguerQueryForm;
import com.cc.leaguer.service.LeaguerChannelService;
import com.cc.leaguer.service.LeaguerService;
import com.cc.map.http.request.IpLocationRequest;
import com.cc.map.http.response.IpLocationResponse;
import com.cc.map.service.MapService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.location.result.LocationResult;
import com.cc.system.location.service.LocationService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.form.LogQueryForm;
import com.cc.system.log.service.LogService;
import com.cc.system.shiro.SecurityContextUtil;

/**
 * 会员
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/leaguer")
public class LeaguerController {
	
	@Autowired
	private LeaguerService leaguerService;
	
	@Autowired
	private LeaguerChannelService leaguerChannelService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private MapService mapService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 查询会员信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryLeaguer(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		LeaguerBean leaguerBean = null;
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				response.setMessage("您还未注册");
				return response;
			}
			leaguerBean = leaguerBeanList.get(0);
		}else{
			leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		}
		if (leaguerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		Map<String, Object> leaguer = new HashMap<String, Object>();
		leaguer.put("id", leaguerBean.getId());
		leaguer.put("leaguerName", leaguerBean.getLeaguerName());
		leaguer.put("avatarUrl", leaguerBean.getAvatarUrl());
		leaguer.put("status", leaguerBean.getStatus());
		leaguer.put("statusName", LeaguerStatusEnum.getNameByCode(leaguerBean.getStatus()));
		leaguer.put("email", leaguerBean.getEmail());
		leaguer.put("credit", leaguerBean.getCredit());
		LocationResult location = locationService.queryLocation(leaguerBean.getLocationId());
		leaguer.put("province", location.getProvince());
		leaguer.put("country", location.getCountry());
		leaguer.put("city", location.getCity());
		leaguer.put("createTime", DateTools.getFormatDate(leaguerBean.getCreateTime(), DateTools.DATEFORMAT3));
		List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerBean.getId());
		if(!ListTools.isEmptyOrNull(leaguerChannelBeanList)){
			leaguer.put("channelList", leaguerChannelBeanList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
		}
		response.setData(leaguer);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 锁定会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.lock" })
	@RequestMapping(value = "/lock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.LOCK, title = "锁定会员", paramNames = {"id"})
	public Response<String> lockLeaguer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		if (leaguerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		try {
			leaguerService.lockLeaguer(id);
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
	 * 解锁会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.unlock" })
	@RequestMapping(value = "/unlock/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.UNLOCK, title = "解锁会员", paramNames = {"id"})
	public Response<String> unlockLeaguer(@PathVariable Long id){
		Response<String> response = new Response<String>();
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		if (leaguerBean == null) {
			response.setMessage("会员不存在");
			return response;
		}
		try {
			leaguerService.unLockLeaguer(id);
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
	 * 分页查询会员信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryLeaguerPage(@ModelAttribute LeaguerQueryForm form){
		Page<Map<String, Object>> page = leaguerService.queryLeaguerPage(form);
		return page;
	}
	
	/**
	 * 绑定邮箱
	 * @param email
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.BIND, title = "绑定邮箱")
	public Response<String> bindLeaguerEmail(String email){
		Response<String> response = new Response<String>();
		if (StringTools.isNullOrNone(email)) {
			response.setMessage("请输入邮箱");
			return response;
		}
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
		if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
			response.setMessage("绑定邮箱失败");
			return response;
		}
		try {
			leaguerService.bindEmail(leaguerBeanList.get(0).getId(), email);
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
	 * 频道设置
	 * @param authorizeMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.channel.authorize" })
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.AUTHORIZE, title = "频道设置")
	public Response<String> authorizeChannel(@RequestBody Map<String, Object> authorizeMap){
		Response<String> response = new Response<String>();
		Object leaguerId = authorizeMap.get("leaguerId");
		if (leaguerId==null) {
			response.setMessage("请选择会员");
			return response;
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, Long.valueOf(StringTools.toString(leaguerId)));
		if (leaguerBean==null) {
			response.setMessage("会员[id:"+leaguerId+"]不存在");
			return response;
		}
		Object object = authorizeMap.get("channelList");
		if (object==null) {
			response.setMessage("请选择频道");
			return response;
		}
		List<Integer> channelList = (List<Integer>) object;
		if (ListTools.isEmptyOrNull(channelList)) {
			response.setMessage("请选择频道");
			return response;
		}
		List<Long> channelIdList = new ArrayList<Long>();
		for (Integer channel : channelList) {
			channelIdList.add(new Long(channel));
		}
		try {
			leaguerChannelService.updateLeaguerChannel(Long.valueOf(StringTools.toString(leaguerId)), channelIdList);
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
	 * 会员签到
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkin", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.CHECKIN, title = "会员签到")
	public Response<Object> checkin(){
		Response<Object> response = new Response<Object>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				response.setMessage("您还未注册");
				return response;
			}
			try {
				leaguerService.checkin(leaguerBeanList.get(0).getId());
				response.setSuccess(Boolean.TRUE);
			} catch (LogicException e) {
				response.setMessage(e.getErrContent());
				return response;
			} catch (Exception e) {
				response.setMessage("系统内部错误");
				return response;
			}
		}else{
			response.setMessage("对不起,您无权操作");
		}
		return response;
	}
	
	/**
	 * 会员定位
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/locate", method = RequestMethod.POST)
	public Response<Object> locateLeaguer(HttpServletRequest request){
		Response<Object> response = new Response<Object>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (!UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			response.setMessage("暂不支持定位功能");
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
		if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
			response.setMessage("您还未注册");
			return response;
		}
		LeaguerBean leaguerBean = leaguerBeanList.get(0);
		IpLocationRequest ipLocationRequest = new IpLocationRequest();
		SystemConfigBean akSystemConfigBean = systemConfigService.querySystemConfigBean("map.ak");
		if(akSystemConfigBean==null){
			response.setMessage("系统内部错误");
			return response;
		}
		ipLocationRequest.setAk(akSystemConfigBean.getPropertyValue());
		SystemConfigBean skSystemConfigBean = systemConfigService.querySystemConfigBean("map.sk");
		if(skSystemConfigBean!=null){
			ipLocationRequest.setSk(skSystemConfigBean.getPropertyValue());
		}
		ipLocationRequest.setIp(RequestContextUtil.getIpAddr(request));
		IpLocationResponse ipLocationResponse = mapService.queryIpLocation(ipLocationRequest);
		if(!ipLocationResponse.isSuccess()){
			response.setMessage(ipLocationResponse.getMessage());
			return response;
		}
		leaguerBean.setLocationId(locationService.saveLocation("中国", ipLocationResponse.getContent().getAddressDetail().getProvince(), ipLocationResponse.getContent().getAddressDetail().getCity(), ipLocationResponse.getContent().getAddressDetail().getDistrict()));
		leaguerBean.update();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 定位会员
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.locate" })
	@RequestMapping(value="/locate/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.LEAGUERMANAGEMENT, operType = OperTypeEnum.LOCATE, title = "定位会员", paramNames = {"id"})
	public Response<Object> locateLeaguer(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		if(leaguerBean==null){
			response.setMessage("会员不存在");
			return response;
		}
		IpLocationRequest ipLocationRequest = new IpLocationRequest();
		SystemConfigBean akSystemConfigBean = systemConfigService.querySystemConfigBean("map.ak");
		if(akSystemConfigBean==null){
			response.setMessage("请配置百度开发者ak");
			return response;
		}
		ipLocationRequest.setAk(akSystemConfigBean.getPropertyValue());
		SystemConfigBean skSystemConfigBean = systemConfigService.querySystemConfigBean("map.sk");
		if(skSystemConfigBean!=null){
			ipLocationRequest.setSk(skSystemConfigBean.getPropertyValue());
		}
		LogQueryForm form = new LogQueryForm();
		form.setUserId(leaguerBean.getUid());
		form.setPageSize("1");
		Page<Map<String, Object>> page = logService.queryOperationLogPage(form);
		if(!page.isSuccess() || ListTools.isEmptyOrNull(page.getData())){
			response.setMessage("暂未查询到会员的操作日志记录");
			return response;
		}
		ipLocationRequest.setIp(logService.getOperationLogBeanById(Long.valueOf(String.valueOf(page.getData().get(0).get("id")))).getClientIp());
		IpLocationResponse ipLocationResponse = mapService.queryIpLocation(ipLocationRequest);
		if(!ipLocationResponse.isSuccess()){
			response.setMessage(ipLocationResponse.getMessage());
			return response;
		}
		leaguerBean.setLocationId(locationService.saveLocation("中国", ipLocationResponse.getContent().getAddressDetail().getProvince(), ipLocationResponse.getContent().getAddressDetail().getCity(), ipLocationResponse.getContent().getAddressDetail().getDistrict()));
		leaguerBean.update();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
