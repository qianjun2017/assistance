/**
 * 
 */
package com.cc.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryLeaguerBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.bean.LotteryRetailerBean;
import com.cc.lottery.dao.LotteryDao;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryLeaguerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryLeaguerListResult;
import com.cc.lottery.result.LotteryListResult;
import com.cc.lottery.service.LotteryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class LotteryServiceImpl implements LotteryService {
	
	@Autowired
	private LotteryDao lotteryDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveLottery(LotteryBean lotteryBean) {
		int row = lotteryBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存抽奖失败");
		}
		if(!ListTools.isEmptyOrNull(lotteryBean.getPrizeList())){
			for(LotteryPrizeBean lotteryPrizeBean: lotteryBean.getPrizeList()){
				lotteryPrizeBean.setLotteryId(lotteryBean.getId());
				row = lotteryPrizeBean.save();
				if (row!=1) {
					throw new LogicException("E002","保存抽奖奖项失败");
				}
			}
		}
	}

	@Override
	public Page<LotteryListResult> queryLotteryPage(LotteryQueryForm form) {
		Page<LotteryListResult> page = new Page<LotteryListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<LotteryListResult> lotteryList = lotteryDao.queryLotteryList(form);
		PageInfo<LotteryListResult> pageInfo = new PageInfo<LotteryListResult>(lotteryList);
		if (ListTools.isEmptyOrNull(lotteryList)) {
			page.setMessage("没有查询到相关抽奖数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(lotteryList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateLottery(LotteryBean lotteryBean) {
		LotteryBean updateLotteryBean = LotteryBean.get(LotteryBean.class, lotteryBean.getId());
		if(updateLotteryBean==null){
			throw new LogicException("E001", "抽奖不存在");
		}
		updateLotteryBean.setCount(lotteryBean.getCount());
		updateLotteryBean.setShare(lotteryBean.getShare());
		updateLotteryBean.setSame(lotteryBean.getSame());
		int row = updateLotteryBean.updateForce();
		if (row!=1) {
			throw new LogicException("E002","修改抽奖失败");
		}
		Example example = new Example(LotteryPrizeBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("lotteryId", lotteryBean.getId());
		LotteryPrizeBean updateLotteryPrizeBean = new LotteryPrizeBean();
		updateLotteryPrizeBean.setStatus(LotteryStatusEnum.OVER.getCode());
		updateLotteryPrizeBean.updateByExample(example);
		if(!ListTools.isEmptyOrNull(lotteryBean.getPrizeList())){
			for(LotteryPrizeBean lotteryPrizeBean: lotteryBean.getPrizeList()){
				lotteryPrizeBean.setLotteryId(lotteryBean.getId());
				row = lotteryPrizeBean.save();
				if (row!=1) {
					throw new LogicException("E003","保存抽奖奖项失败");
				}
			}
		}
	}

	@Override
	public Page<LotteryLeaguerListResult> queryLotteryLeaguerPage(LotteryLeaguerQueryForm form) {
		Page<LotteryLeaguerListResult> page = new Page<LotteryLeaguerListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<LotteryLeaguerListResult> lotteryLeaguerList = lotteryDao.queryLotteryLeaguerList(form);
		PageInfo<LotteryLeaguerListResult> pageInfo = new PageInfo<LotteryLeaguerListResult>(lotteryLeaguerList);
		if (ListTools.isEmptyOrNull(lotteryLeaguerList)) {
			page.setMessage("没有查询到相关中奖数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(lotteryLeaguerList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public int queryLotteryLeaguerCount(Long leaguerId, Long lotteryId) {
		LotteryLeaguerQueryForm form = new LotteryLeaguerQueryForm();
		form.setLeaguerId(leaguerId);
		form.setLotteryId(lotteryId);
		List<LotteryLeaguerListResult> lotteryLeaguerList = lotteryDao.queryLotteryLeaguerList(form);
		if(ListTools.isEmptyOrNull(lotteryLeaguerList)){
			return 0;
		}
		return lotteryLeaguerList.size();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveLotteryLeaguer(LotteryLeaguerBean lotteryLeaguerBean) {
		int row = lotteryLeaguerBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存抽奖结果失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveLotteryRetailer(LotteryRetailerBean lotteryRetailerBean) {
		int row = lotteryRetailerBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存会员商家失败");
		}
	}

}
