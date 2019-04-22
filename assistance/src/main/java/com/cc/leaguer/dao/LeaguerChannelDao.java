/**
 * 
 */
package com.cc.leaguer.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.leaguer.bean.LeaguerChannelBean;

/**
 * @author Administrator
 *
 */
@Mapper
public interface LeaguerChannelDao extends CrudDao {

	/**
	 * 查询会员所属频道
	 * @param leaguerId
	 * @return
	 */
	List<LeaguerChannelBean> queryLeaguerChannelBeanList(Long leaguerId);
}
