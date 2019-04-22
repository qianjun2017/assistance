/**
 * 
 */
package com.cc.integration.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.integration.bean.IntegrationBean;
import com.cc.integration.service.IntegrationService;
import com.cc.leaguer.bean.LeaguerBean;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class IntegrationServiceImpl implements IntegrationService {
	
	private static Logger logger = LoggerFactory.getLogger(IntegrationService.class);
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void createIntegration(Long leaguerId) {
		IntegrationBean integrationBean = new IntegrationBean();
		integrationBean.setLeaguerId(leaguerId);
		integrationBean.setIntegration(0l);
		integrationBean.setGradeIntegration(0l);
		integrationBean.setCreateTime(DateTools.now());
		int row = integrationBean.save();
		if (row!=1) {
			throw new LogicException("E001", "创建积分账户失败");
		}
		
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deposit(Long leaguerId, Long integration, Long gradeIntegration) {
		if (integration==0l && gradeIntegration == 0l) {
			logger.info("消费积分和等级积分为0,无需存入");
			return;
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, leaguerId);
		if(leaguerBean==null){
			logger.error("会员不存在,leaguerId["+leaguerId+"]");
			throw new LogicException("E001", "会员不存在");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("leaguerId", leaguerId);
		int row = 0;
		int count = 0;
		while (row!=1 && count<10) {
			List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByMap(IntegrationBean.class, paramMap);
			if (ListTools.isEmptyOrNull(integrationBeanList)) {
				throw new LogicException("E002", "会员["+leaguerBean.getLeaguerName()+"]尚未创建积分账户");
			}
			if (integrationBeanList.size()>1) {
				throw new LogicException("E003", "会员["+leaguerBean.getLeaguerName()+"]积分账户不唯一");
			}
			IntegrationBean integrationBean = integrationBeanList.get(0);
			if (integrationBean.getIntegration() + integration<0) {
				throw new LogicException("E004", "积分不足");
			}
			if (integrationBean.getGradeIntegration() + gradeIntegration<0) {
				throw new LogicException("E004", "等级积分不足");
			}
			Example example = new Example(IntegrationBean.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("id", integrationBean.getId());
			criteria.andEqualTo("integration", integrationBean.getIntegration());
			criteria.andEqualTo("gradeIntegration", integrationBean.getGradeIntegration());
			integrationBean.setIntegration(integrationBean.getIntegration() + integration);
			integrationBean.setGradeIntegration(integrationBean.getGradeIntegration() + gradeIntegration);
			row = integrationBean.updateByExample(example);
			count ++;
		}
		if(row!=1){
			String msg = "会员["+leaguerBean.getLeaguerName()+"]存入积分失败,[integration:"+integration+",gradeIntegration:"+gradeIntegration+"]";
			logger.error(msg);
			throw new LogicException("E004", msg);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void withdraw(Long leaguerId, Long integration) {
		if (integration==0l) {
			logger.info("消费积分为0,无需扣减");
			return;
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, leaguerId);
		if(leaguerBean==null){
			logger.error("会员不存在,leaguerId["+leaguerId+"]");
			throw new LogicException("E001", "会员不存在");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("leaguerId", leaguerId);
		int row = 0;
		int count = 0;
		while (row!=1 && count<10) {
			List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByMap(IntegrationBean.class, paramMap);
			if (ListTools.isEmptyOrNull(integrationBeanList)) {
				throw new LogicException("E002", "会员["+leaguerBean.getLeaguerName()+"]尚未创建积分账户");
			}
			if (integrationBeanList.size()>1) {
				throw new LogicException("E003", "会员["+leaguerBean.getLeaguerName()+"]积分账户不唯一");
			}
			IntegrationBean integrationBean = integrationBeanList.get(0);
			if (integrationBean.getIntegration() - integration<0) {
				throw new LogicException("E004", "积分不足");
			}
			Example example = new Example(IntegrationBean.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("id", integrationBean.getId());
			criteria.andEqualTo("integration", integrationBean.getIntegration());
			integrationBean.setIntegration(integrationBean.getIntegration() - integration);
			row = integrationBean.updateByExample(example);
			count ++;
		}
		if(row!=1){
			String msg = "会员["+leaguerBean.getLeaguerName()+"]扣减积分失败,[integration:"+integration+"]";
			logger.error(msg);
			throw new LogicException("E005", msg);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void createIntegrations() {
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class);
		if (ListTools.isEmptyOrNull(leaguerBeanList)) {
			return;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (LeaguerBean leaguerBean : leaguerBeanList) {
			paramMap.put("leaguerId", leaguerBean.getId());
			List<IntegrationBean> integrationBeanList = IntegrationBean.findAllByMap(IntegrationBean.class, paramMap);
			if (ListTools.isEmptyOrNull(integrationBeanList)) {
				createIntegration(leaguerBean.getId());
			}
		}
	}

}
