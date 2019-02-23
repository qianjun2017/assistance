/**
 * 
 */
package com.cc.lottery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.lottery.bean.LotteryBean;
import com.cc.lottery.bean.LotteryCustomerBean;
import com.cc.lottery.bean.LotteryPrizeBean;
import com.cc.lottery.dao.LotteryDao;
import com.cc.lottery.enums.LotteryStatusEnum;
import com.cc.lottery.form.LotteryCustomerQueryForm;
import com.cc.lottery.form.LotteryQueryForm;
import com.cc.lottery.result.LotteryCustomerListResult;
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
	@Transactional
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
	@Transactional
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
	public Page<LotteryCustomerListResult> queryLotteryCustomerPage(LotteryCustomerQueryForm form) {
		Page<LotteryCustomerListResult> page = new Page<LotteryCustomerListResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<LotteryCustomerListResult> lotteryCustomerList = lotteryDao.queryLotteryCustomerList(form);
		PageInfo<LotteryCustomerListResult> pageInfo = new PageInfo<LotteryCustomerListResult>(lotteryCustomerList);
		if (ListTools.isEmptyOrNull(lotteryCustomerList)) {
			page.setMessage("没有查询到相关中奖数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(lotteryCustomerList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public int queryLotteryCustomerCount(Long customerId, Long lotteryId) {
		LotteryCustomerQueryForm form = new LotteryCustomerQueryForm();
		form.setCustomerId(customerId);
		form.setLotteryId(lotteryId);
		List<LotteryCustomerListResult> lotteryCustomerList = lotteryDao.queryLotteryCustomerList(form);
		if(ListTools.isEmptyOrNull(lotteryCustomerList)){
			return 0;
		}
		return lotteryCustomerList.size();
	}

	@Override
	public void saveLotteryCustomer(LotteryCustomerBean lotteryCustomerBean) {
		int row = lotteryCustomerBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存抽奖结果失败");
		}
	}

}
