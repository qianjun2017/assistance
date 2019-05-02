/**
 * 
 */
package com.cc.lottery.service;

import com.cc.common.web.Page;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryLeaguerBean;
import com.cc.lottery.bean.LotteryRetailerBean;
import com.cc.lottery.form.LotteryLeaguerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryLeaguerListResult;
import com.cc.lottery.result.LotteryListResult;

/**
 * @author Administrator
 *
 */
public interface LotteryService {

	/**
	 * 保存抽奖
	 * @param lotteryBean
	 */
	void saveLottery(LotteryBean lotteryBean);
	
	/**
	 * 分页查询抽奖列表
	 * @param form
	 * @return
	 */
	Page<LotteryListResult> queryLotteryPage(LotteryQueryForm form);

	/**
	 * 修改抽奖
	 * @param lotteryBean
	 */
	void updateLottery(LotteryBean lotteryBean);
	
	/**
	 * 分页查询中奖列表
	 * @param form
	 * @return
	 */
	Page<LotteryLeaguerListResult> queryLotteryLeaguerPage(LotteryLeaguerQueryForm form);
	
	/**
	 * 查询用户参与抽奖次数
	 * @param leaguerId
	 * @param lotteryId
	 * @return
	 */
	int queryLotteryLeaguerCount(Long leaguerId, Long lotteryId);
	
	/**
	 * 保存抽奖结果
	 * @param lotteryLeaguerBean
	 */
	void saveLotteryLeaguer(LotteryLeaguerBean lotteryLeaguerBean);
	
	/**
	 * 保存会员商家信息
	 * @param lotteryRetailerBean
	 */
	void saveLotteryRetailer(LotteryRetailerBean lotteryRetailerBean);
}
