/**
 * 
 */
package com.cc.exchange.service;

import com.cc.common.web.Page;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.form.ExchangeQueryForm;
import com.cc.exchange.result.ExchangeResult;

/**
 * @author Administrator
 *
 */
public interface ExchangeService {

	/**
	 * 保存兑换记录
	 * @param exchangeBean
	 */
	void saveExchange(ExchangeBean exchangeBean);
	
	/**
	 * 取消兑换
	 * @param id
	 */
	void cancelExchange(Long id);
	
	/**
	 * 取消兑换申请
	 * @param id
	 */
	void requestCancelExchange(Long id);
	
	/**
	 * 交易记录
	 * @param form
	 * @return
	 */
	Page<ExchangeResult> queryExchangePage(ExchangeQueryForm form);

	/**
	 * 审批取消兑换申请
	 * @param exchangeBean
	 */
	void auditCancelExchange(ExchangeBean exchangeBean);
}
