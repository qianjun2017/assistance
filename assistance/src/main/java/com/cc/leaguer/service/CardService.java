/**
 * 
 */
package com.cc.leaguer.service;

import com.cc.leaguer.bean.CardBean;
import com.cc.leaguer.bean.CardLevelBean;

/**
 * @author Administrator
 *
 */
public interface CardService {

	/**
	 * 查询积分所属会员等级
	 * @param gradeIntegration
	 * @return
	 */
	CardLevelBean queryCardLevelByGradeIntegration(Long gradeIntegration);

	/**
	 * 保存会员卡
	 * @param cardBean
	 */
	void saveCard(CardBean cardBean);

	/**
	 * 保存会员卡级别
	 * @param cardLevelBean
	 */
	void saveCardLevel(CardLevelBean cardLevelBean);

	/**
	 * 删除会员卡级别
	 * @param id
	 */
	void deleteCardLevel(Long id);
}
