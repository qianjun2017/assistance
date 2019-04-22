/**
 * 
 */
package com.cc.exchange.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.exchange.form.ExchangeQueryForm;
import com.cc.exchange.result.ExchangeResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface ExchangeDao extends CrudDao {

	/**
	 * 交易记录查询
	 * @param form
	 * @return
	 */
	List<ExchangeResult> queryExchangeList(ExchangeQueryForm form);
}
