/**
 * 
 */
package com.cc.leaguer.service;

import java.util.Map;

import com.cc.common.web.Page;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.form.LeaguerQueryForm;

/**
 * @author Administrator
 *
 */
public interface LeaguerService {

	/**
	 * 保存会员
	 * @param leaguerBean
	 */
	void saveLeaguer(LeaguerBean leaguerBean);
	
	/**
	 * 锁定会员
	 * @param id
	 */
	void lockLeaguer(Long id);
	
	/**
	 * 解锁会员
	 * @param id
	 */
	void unLockLeaguer(Long id);
	
	/**
	 * 分页查询会员信息
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryLeaguerPage(LeaguerQueryForm form);
	
	/**
	 * 绑定邮箱
	 * @param id
	 * @param email
	 */
	void bindEmail(Long id, String email);
	
	/**
	 * 增加信用积分
	 * @param id
	 * @param credit
	 */
	void addLeaguerCredit(Long id, int credit);
	
	/**
	 * 调低会员一半信用积分
	 * @param id
	 */
	void downHalfLeaguerCredit(Long id);
	
	/**
	 * 会员签到
	 * @param id
	 */
	void checkin(Long id);
}
