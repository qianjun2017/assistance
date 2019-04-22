/**
 * 
 */
package com.cc.leaguer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.leaguer.bean.CardBean;
import com.cc.leaguer.bean.CardLevelBean;
import com.cc.leaguer.service.CardService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class CardServiceImpl implements CardService {

	@Override
	public CardLevelBean queryCardLevelByGradeIntegration(Long gradeIntegration) {
		Example example = new Example(CardLevelBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andLessThanOrEqualTo("gradeIntegration", gradeIntegration);
		example.orderBy("gradeIntegration").desc();
		List<CardLevelBean> cardLevelBeanList = CardLevelBean.findByExample(CardLevelBean.class, example);
		if(!ListTools.isEmptyOrNull(cardLevelBeanList)){
			return cardLevelBeanList.get(0);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveCard(CardBean cardBean) {
		int row = cardBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存会员卡失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveCardLevel(CardLevelBean cardLevelBean) {
		int row = cardLevelBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存会员卡级别失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteCardLevel(Long id) {
		CardLevelBean cardLevelBean = new CardLevelBean();
		cardLevelBean.setId(id);
		int row = cardLevelBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除会员卡级别失败");
		}
	}

}
