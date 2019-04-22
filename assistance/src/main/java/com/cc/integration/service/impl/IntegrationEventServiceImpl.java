/**
 * 
 */
package com.cc.integration.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventStatusEnum;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.enums.IntegrationTypeEnum;
import com.cc.integration.form.IntegrationEventQueryForm;
import com.cc.integration.service.IntegrationEventService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class IntegrationEventServiceImpl implements IntegrationEventService {

	@Override
	public List<IntegrationEventBean> queryIntegrationEventBeanList(String eventType) {
		List<IntegrationEventBean> integrationEventBeanList = IntegrationEventBean.findAllByParams(IntegrationEventBean.class, "eventType", eventType);
		return integrationEventBeanList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveIntegrationEvent(IntegrationEventBean integrationEventBean) {
		int row = integrationEventBean.save();
		if (row!=1) {
			throw new LogicException("E001", "保存积分事件失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteIntegrationEvent(Long id) {
		IntegrationEventBean integrationEventBean = new IntegrationEventBean();
		integrationEventBean.setId(id);
		int row = integrationEventBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除积分事件失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryIntegrationEventPage(IntegrationEventQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		Map<String, Object> paramMap  = new HashMap<String, Object>();
		if (!StringTools.isNullOrNone(form.getEventType())) {
			paramMap.put("eventType", form.getEventType());
		}if (!StringTools.isNullOrNone(form.getStatus())) {
			paramMap.put("status", form.getStatus());
		}
		List<IntegrationEventBean> integrationEventBeanList = IntegrationEventBean.findAllByMap(IntegrationEventBean.class, paramMap);
		PageInfo<IntegrationEventBean> pageInfo = new PageInfo<IntegrationEventBean>(integrationEventBeanList);
		if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
			page.setMessage("没有查询到相关积分事件数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> eventList = new ArrayList<Map<String,Object>>();
		for (IntegrationEventBean integrationEventBean : integrationEventBeanList) {
			Map<String, Object> event = new HashMap<String, Object>();
			event.put("id", integrationEventBean.getId());
			event.put("eventType", integrationEventBean.getEventType());
			event.put("eventTypeName", IntegrationEventTypeEnum.getNameByCode(integrationEventBean.getEventType()));
			event.put("integrationType", integrationEventBean.getIntegrationType());
			event.put("integrationTypeName", IntegrationTypeEnum.getNameByCode(integrationEventBean.getIntegrationType()));
			event.put("status", integrationEventBean.getStatus());
			event.put("statusName", IntegrationEventStatusEnum.getNameByCode(integrationEventBean.getStatus()));
			event.put("integration", integrationEventBean.getIntegration());
			eventList.add(event);
		}
		page.setData(eventList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}
	
}
