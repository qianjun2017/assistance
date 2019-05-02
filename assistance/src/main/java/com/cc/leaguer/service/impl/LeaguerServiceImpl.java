/**
 * 
 */
package com.cc.leaguer.service.impl;

import java.text.DecimalFormat;
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
import com.cc.integration.service.IntegrationService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.enums.LeaguerStatusEnum;
import com.cc.leaguer.form.LeaguerQueryForm;
import com.cc.leaguer.service.LeaguerService;
import com.cc.system.location.result.LocationResult;
import com.cc.system.location.service.LocationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class LeaguerServiceImpl implements LeaguerService {
	
	private static Logger logger = LoggerFactory.getLogger(LeaguerService.class);
	
	@Autowired
	private IntegrationService integrationService;

	@Autowired
	private IntegrationEventService integrationEventService;

	@Autowired
	private LocationService locationService;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveLeaguer(LeaguerBean leaguerBean) {
		Long leaguerId = leaguerBean.getId();
		int row = leaguerBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存会员失败");
		}
		if(leaguerId==null){
			integrationService.createIntegration(leaguerBean.getId());
			List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.REGISTER.getCode());
			if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
				return;
			}
			if (integrationEventBeanList.size()>1) {
				logger.error("注册送积分事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(leaguerBean.getId());
				event.happened();
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void lockLeaguer(Long id) {
		LeaguerBean leaguerBean = new LeaguerBean();
		leaguerBean.setId(id);
		leaguerBean.setStatus(LeaguerStatusEnum.LOCKED.getCode());
		int row = leaguerBean.update();
		if(row!=1){
			throw new LogicException("E001", "锁定会员失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void unLockLeaguer(Long id) {
		LeaguerBean leaguerBean = new LeaguerBean();
		leaguerBean.setId(id);
		leaguerBean.setStatus(LeaguerStatusEnum.NORMAL.getCode());
		int row = leaguerBean.update();
		if(row!=1){
			throw new LogicException("E001", "解锁会员失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryLeaguerPage(LeaguerQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(LeaguerBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getLeaguerName())){
			criteria.andLike("leaguerName", "%"+form.getLeaguerName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getCity())) {
			criteria.andEqualTo("city", form.getCity());
		}
		if (!StringTools.isNullOrNone(form.getCountry())) {
			criteria.andEqualTo("country", form.getCountry());
		}
		if (!StringTools.isNullOrNone(form.getProvince())) {
			criteria.andEqualTo("province", form.getProvince());
		}
		if (!StringTools.isNullOrNone(form.getStatus())) {
			criteria.andEqualTo("status", form.getStatus());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findByExample(LeaguerBean.class, example);
		PageInfo<LeaguerBean> pageInfo = new PageInfo<LeaguerBean>(leaguerBeanList);
		if (ListTools.isEmptyOrNull(leaguerBeanList)) {
			page.setMessage("没有查询到相关会员数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> leaguerList = new ArrayList<Map<String,Object>>();
		for (LeaguerBean leaguerBean : leaguerBeanList) {
			Map<String, Object> leaguer = new HashMap<String, Object>();
			leaguer.put("id", leaguerBean.getId());
			leaguer.put("leaguerName", leaguerBean.getLeaguerName());
			leaguer.put("avatarUrl", leaguerBean.getAvatarUrl());
			LocationResult location = locationService.queryLocation(leaguerBean.getLocationId());
			leaguer.put("country", location.getCountry());
			leaguer.put("province", location.getProvince());
			leaguer.put("city", location.getCity());
			leaguer.put("status", leaguerBean.getStatus());
			leaguer.put("statusName", LeaguerStatusEnum.getNameByCode(leaguerBean.getStatus()));
			leaguer.put("email", leaguerBean.getEmail());
			leaguer.put("credit", leaguerBean.getCredit());
			leaguer.put("createTime", DateTools.getFormatDate(leaguerBean.getCreateTime(), DateTools.DATEFORMAT3));
			leaguerList.add(leaguer);
		}
		page.setData(leaguerList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void bindEmail(Long id, String email) {
		LeaguerBean leaguerBean = new LeaguerBean();
		leaguerBean.setId(id);
		leaguerBean.setEmail(email);
		int row = leaguerBean.update();
		if(row!=1){
			throw new LogicException("E001", "绑定会员邮箱失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void addLeaguerCredit(Long id, int credit) {
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		Float maxCredit = 100f;
		if (leaguerBean.getCredit().compareTo(maxCredit)<0) {
			LeaguerBean leaguer = new LeaguerBean();
			leaguer.setId(id);
			Float total = leaguerBean.getCredit()+credit;
			if (total.compareTo(maxCredit)<0) {
				leaguer.setCredit(total);
			}else{
				leaguer.setCredit(maxCredit);
			}
			int row = leaguer.update();
			if (row!=1) {
				throw new LogicException("E001", "提升信用失败");
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void downHalfLeaguerCredit(Long id) {
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, id);
		Float credit = leaguerBean.getCredit()/2;
		DecimalFormat df = new DecimalFormat("0.00");
		String format = df.format(credit);
		LeaguerBean leaguer = new LeaguerBean();
		leaguer.setId(id);
		leaguer.setCredit(Float.valueOf(format));
		int row = leaguer.update();
		if (row!=1) {
			throw new LogicException("E001", "折半信用失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void checkin(Long id) {
		List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.CHECKIN.getCode());
		if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
			throw new LogicException("E001", "系统暂未开启签到功能");
		}
		if (integrationEventBeanList.size()>1) {
			logger.error("签到送积分事件不唯一");
		}
		Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
		if (event!=null) {
			event.setLeaguerId(id);
			event.happened();
		}else{
			throw new LogicException("E001", "系统暂未开启签到功能");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void bindPhone(Long id, String phone) {
		LeaguerBean leaguerBean = new LeaguerBean();
		leaguerBean.setId(id);
		leaguerBean.setPhone(phone);;
		int row = leaguerBean.update();
		if(row!=1){
			throw new LogicException("E001", "绑定会员手机号码失败");
		}
	}

}
