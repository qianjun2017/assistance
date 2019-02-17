/**
 * 
 */
package com.cc.lottery.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.lottery.form.LotteryCustomerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryCustomerListResult;
import com.cc.lottery.result.LotteryListResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface LotteryDao extends CrudDao {

	/**
	 * 查询抽奖列表
	 */
	List<LotteryListResult> queryLotteryList(LotteryQueryForm form);
	
	/**
	 * 查询中奖列表
	 * @param form
	 * @return
	 */
	List<LotteryCustomerListResult> queryLotteryCustomerList(LotteryCustomerQueryForm form);
}
