/**
 * 
 */
package com.cc.leaguer.web;

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

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.integration.bean.IntegrationBean;
import com.cc.leaguer.bean.CardLevelBean;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.enums.LeaguerStatusEnum;
import com.cc.leaguer.form.LeaguerQueryForm;
import com.cc.leaguer.service.CardService;
import com.cc.leaguer.service.LeaguerChannelService;
import com.cc.leaguer.service.LeaguerService;
import com.cc.system.location.result.LocationResult;
import com.cc.system.location.service.LocationService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

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
	private CardService cardService;
	
	/**
	 * 查询会员信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryLeaguer(@PathVariable Long id){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		if (leaguerBean == null) {
			response.setMessage("会员不存在或已删除");
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
		List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByParams(IntegrationBean.class, "leaguerId", leaguerBean.getId());
		if(!ListTools.isEmptyOrNull(integrationBeanList)){
			IntegrationBean integrationBean = integrationBeanList.get(0);
			leaguer.put("cardNo", integrationBean.getCardNo());
			leaguer.put("integration", integrationBean.getIntegration());
			CardLevelBean cardLevelBean = cardService.queryCardLevelByGradeIntegration(integrationBean.getGradeIntegration());
			if(cardLevelBean!=null){
				leaguer.put("cardLevel", cardLevelBean.getName());
				leaguer.put("cardImage", cardLevelBean.getImageUrl());
				leaguer.put("ardColor", cardLevelBean.getColor());
			}
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
			response.setMessage("会员不存在或已删除");
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
			response.setMessage("会员不存在或已删除");
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
}
