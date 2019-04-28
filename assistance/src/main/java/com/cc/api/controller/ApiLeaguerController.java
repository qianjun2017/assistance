/**
 * 
 */
package com.cc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.EmailForm;
import com.cc.api.form.LeaguerForm;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.Response;
import com.cc.integration.bean.IntegrationBean;
import com.cc.leaguer.bean.CardLevelBean;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.result.LeaguerResult;
import com.cc.leaguer.service.LeaguerService;
import com.cc.map.http.request.IpLocationRequest;
import com.cc.map.http.response.IpLocationResponse;
import com.cc.map.service.MapService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.location.service.LocationService;
import com.cc.leaguer.service.CardService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/leaguer")
public class ApiLeaguerController {
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private LeaguerService leaguerService;

	@Autowired
	private MapService mapService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private LocationService locationService;

	/**
	 * 查询登录用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Response<Object> queryLeaguerInfo(@RequestParam Long leaguerId){
		Response<Object> response = new Response<Object>();
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, leaguerId);
		LeaguerResult leaguerResult = JsonTools.covertObject(leaguerBean, LeaguerResult.class);
		List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByParams(IntegrationBean.class, "leaguerId", leaguerBean.getId());
		if(!ListTools.isEmptyOrNull(integrationBeanList)){
			IntegrationBean integrationBean = integrationBeanList.get(0);
			leaguerResult.setCardNo(integrationBean.getCardNo());
			CardLevelBean cardLevelBean = cardService.queryCardLevelByGradeIntegration(integrationBean.getGradeIntegration());
			if(cardLevelBean!=null){
				leaguerResult.setCardLevel(cardLevelBean.getName());
				leaguerResult.setCardImage(cardLevelBean.getImageUrl());
				leaguerResult.setCardColor(cardLevelBean.getColor());
			}
		}
		response.setData(leaguerResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 会员更新个人信息  姓名和手机号
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Response<Object> updateLeaguerInfo(@RequestBody LeaguerForm form){
		Response<Object> response = new Response<Object>();
		if(StringTools.isNullOrNone(form.getName())){
			response.setMessage("姓名不能为空");
			return response;
		}
		if(StringTools.isNullOrNone(form.getPhone())){
			response.setMessage("手机号码不能为空");
			return response;
		}
		if(!StringTools.matches(form.getPhone(), "^1[34578]\\d{9}$")){
			response.setMessage("请输入11位有效手机号码");
            return response;
		}
		LeaguerBean leaguerBean = new LeaguerBean();
		leaguerBean.setId(form.getLeaguerId());
		leaguerBean.setLeaguerName(form.getName());
		leaguerBean.setPhone(form.getPhone());
		try {
			leaguerService.saveLeaguer(leaguerBean);
			LeaguerResult leaguerResult = JsonTools.covertObject(LeaguerBean.get(LeaguerBean.class, form.getLeaguerId()), LeaguerResult.class);
			List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByParams(IntegrationBean.class, "leaguerId", leaguerBean.getId());
			if(!ListTools.isEmptyOrNull(integrationBeanList)){
				IntegrationBean integrationBean = integrationBeanList.get(0);
				leaguerResult.setCardNo(integrationBean.getCardNo());
				CardLevelBean cardLevelBean = cardService.queryCardLevelByGradeIntegration(integrationBean.getGradeIntegration());
				if(cardLevelBean!=null){
					leaguerResult.setCardLevel(cardLevelBean.getName());
					leaguerResult.setCardImage(cardLevelBean.getImageUrl());
					leaguerResult.setCardColor(cardLevelBean.getColor());
				}
			}
			response.setData(leaguerResult);
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
	 * 绑定邮箱
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/email", method = RequestMethod.POST)
	public Response<String> bindLeaguerEmail(@RequestBody EmailForm form){
		Response<String> response = new Response<String>();
		if (StringTools.isNullOrNone(form.getEmail())) {
			response.setMessage("请输入邮箱");
			return response;
		}
		try {
			leaguerService.bindEmail(form.getLeaguerId(), form.getEmail());
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
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/checkin", method = RequestMethod.POST)
	public Response<Object> checkin(@RequestParam Long leaguerId){
		Response<Object> response = new Response<Object>();
		try {
			leaguerService.checkin(leaguerId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			return response;
		}
		return response;
	}
	
	/**
	 * 会员定位
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/locate", method = RequestMethod.POST)
	public Response<Object> locate(@RequestParam Long leaguerId){
		Response<Object> response = new Response<Object>();
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
		ipLocationRequest.setIp(RequestContextUtil.getIpAddr(RequestContextUtil.httpServletRequest()));
		IpLocationResponse ipLocationResponse = mapService.queryIpLocation(ipLocationRequest);
		if(!ipLocationResponse.isSuccess()){
			response.setMessage(ipLocationResponse.getMessage());
			return response;
		}
		try {
			LeaguerBean leaguerBean = new LeaguerBean();
			leaguerBean.setId(leaguerId);
			leaguerBean.setLocationId(locationService.saveLocation("中国", ipLocationResponse.getContent().getAddressDetail().getProvince(), ipLocationResponse.getContent().getAddressDetail().getCity(), ipLocationResponse.getContent().getAddressDetail().getDistrict()));
			leaguerBean.update();
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
			return response;
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			return response;
		}
		return response;
	}
}
