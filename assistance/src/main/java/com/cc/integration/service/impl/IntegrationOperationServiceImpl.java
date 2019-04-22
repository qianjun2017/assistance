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
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.integration.bean.IntegrationOperationBean;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.form.IntegrationOperationQueryForm;
import com.cc.integration.service.IntegrationOperationService;
import com.cc.leaguer.bean.LeaguerBean;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class IntegrationOperationServiceImpl implements IntegrationOperationService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveIntegrationOperation(IntegrationOperationBean integrationOperationBean) {
		int row = integrationOperationBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存积分记录失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryIntegrationOperationPage(IntegrationOperationQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(IntegrationOperationBean.class);
		Example.Criteria criteria = example.createCriteria();
		if (!StringTools.isNullOrNone(form.getEventType())) {
			criteria.andEqualTo("eventType", form.getEventType());
		}
		if (!StringTools.isNullOrNone(form.getComment())) {
			criteria.andLike("comment", "%"+form.getComment()+"%");
		}
		if (form.getLeaguerId() != null) {
			criteria.andEqualTo("leaguerId", form.getLeaguerId());
		}
		if (form.getCreateTimeStart() != null){
			criteria.andGreaterThan("createTime", form.getCreateTimeStart());
		}
		if (form.getCreateTimeEnd() != null) {
			criteria.andLessThan("createTime", form.getCreateTimeEnd());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<IntegrationOperationBean> integrationOperationBeanList = IntegrationOperationBean.findByExample(IntegrationOperationBean.class, example);
		PageInfo<IntegrationOperationBean> pageInfo = new PageInfo<IntegrationOperationBean>(integrationOperationBeanList);
		if (ListTools.isEmptyOrNull(integrationOperationBeanList)) {
			page.setMessage("没有查询到相关积分记录数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> integrationOperationList = new ArrayList<Map<String,Object>>();
		for (IntegrationOperationBean integrationOperationBean : integrationOperationBeanList) {
			Map<String, Object> integrationOperation = new HashMap<String, Object>();
			integrationOperation.put("id", integrationOperationBean.getId());
			integrationOperation.put("eventTypeName", IntegrationEventTypeEnum.getNameByCode(integrationOperationBean.getEventType()));
			LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, integrationOperationBean.getLeaguerId());
			integrationOperation.put("leaguerName", leaguerBean.getLeaguerName());
			integrationOperation.put("comment", integrationOperationBean.getComment());
			integrationOperation.put("createTime", DateTools.getFormatDate(integrationOperationBean.getCreateTime(), DateTools.DATEFORMAT));
			integrationOperation.put("integration", integrationOperationBean.getIntegration()>0?"+"+integrationOperationBean.getIntegration():integrationOperationBean.getIntegration());
			integrationOperationList.add(integrationOperation);
		}
		page.setData(integrationOperationList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
