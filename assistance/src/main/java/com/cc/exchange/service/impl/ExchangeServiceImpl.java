/**
 * 
 */
package com.cc.exchange.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.dao.ExchangeDao;
import com.cc.exchange.enums.ExchangeStatusEnum;
import com.cc.exchange.form.ExchangeQueryForm;
import com.cc.exchange.result.ExchangeResult;
import com.cc.exchange.service.ExchangeService;
import com.cc.integration.bean.IntegrationEventBean;
import com.cc.integration.enums.IntegrationEventTypeEnum;
import com.cc.integration.event.Event;
import com.cc.integration.event.EventFactory;
import com.cc.integration.service.IntegrationEventService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.message.service.MessageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @author Administrator
 *
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

	@Autowired
	private IntegrationEventService integrationEventService;
	
	@Autowired
	private ExchangeDao exchangeDao;
	
	@Autowired
	private MessageService messageService;
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveExchange(ExchangeBean exchangeBean) {
		exchangeBean.setStatus(ExchangeStatusEnum.SUCCESS.getCode());
		int row = exchangeBean.save();
		if (row!=1) {
			throw new LogicException("E001", "兑换失败");
		}
		List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.EXCHANGE.getCode());
		if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
			return;
		}
		if (integrationEventBeanList.size()>1) {
			logger.error("兑换事件不唯一");
		}
		Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
		if (event!=null) {
			event.setLeaguerId(exchangeBean.getLeaguerId());
			event.setData(exchangeBean);
			event.happened();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void cancelExchange(Long id) {
		ExchangeBean exchangeBean = new ExchangeBean();
		exchangeBean.setId(id);
		exchangeBean.setStatus(ExchangeStatusEnum.CANCELLED.getCode());
		int row = exchangeBean.update();
		if (row!=1) {
			throw new LogicException("E001", "取消兑换失败");
		}
		List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.EXCHANGE.getCode());
		if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
			return;
		}
		if (integrationEventBeanList.size()>1) {
			logger.error("兑换事件不唯一");
		}
		Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
		if (event!=null) {
			ExchangeBean exchange = ExchangeBean.get(ExchangeBean.class, id);
			event.setLeaguerId(exchange.getLeaguerId());
			event.setData(exchange);
			event.rollBack();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void requestCancelExchange(Long id) {
		ExchangeBean exchangeBean = new ExchangeBean();
		exchangeBean.setId(id);
		exchangeBean.setStatus(ExchangeStatusEnum.PENDING.getCode());
		int row = exchangeBean.update();
		if (row!=1) {
			throw new LogicException("E001", "申请取消兑换失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void auditCancelExchange(ExchangeBean exchangeBean) {
		int row = exchangeBean.update();
		if (row!=1) {
			throw new LogicException("E001", "审批取消兑换失败");
		}
		ExchangeStatusEnum exchangeStatusEnum = ExchangeStatusEnum.getExchangeStatusEnumByCode(exchangeBean.getStatus());
		if(ExchangeStatusEnum.CANCELLED.equals(exchangeStatusEnum)){
			List<IntegrationEventBean> integrationEventBeanList = integrationEventService.queryIntegrationEventBeanList(IntegrationEventTypeEnum.EXCHANGE.getCode());
			if (ListTools.isEmptyOrNull(integrationEventBeanList)) {
				return;
			}
			if (integrationEventBeanList.size()>1) {
				logger.error("兑换事件不唯一");
			}
			Event event = EventFactory.createEvent(integrationEventBeanList.get(0));
			if (event!=null) {
				event.setLeaguerId(exchangeBean.getLeaguerId());
				event.setData(exchangeBean);
				event.rollBack();
			}
		}else{
			LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, exchangeBean.getLeaguerId());
			if(leaguerBean!=null){
				messageService.releaseSystemMessage(leaguerBean.getUid(), "您关于【"+exchangeBean.getItemName()+"】的取消兑换申请已驳回，驳回原因【"+exchangeBean.getRemark()+"】");
			}
		}
	}

	@Override
	public Page<ExchangeResult> queryExchangePage(ExchangeQueryForm form) {
		Page<ExchangeResult> page = new Page<ExchangeResult>(); 
		PageHelper.orderBy(String.format("e.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ExchangeResult> exchangeList = exchangeDao.queryExchangeList(form);
		PageInfo<ExchangeResult> pageInfo = new PageInfo<ExchangeResult>(exchangeList);
		if (ListTools.isEmptyOrNull(exchangeList)) {
			page.setMessage("没有查询到相关兑换数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(exchangeList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
