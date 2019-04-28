package com.cc.order.dao;

import com.cc.common.orm.dao.CrudDao;
import com.cc.order.form.OrderQueryForm;
import com.cc.order.result.OrderListResult;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Mapper
public interface OrderDao extends CrudDao {
	
	/**
	 * 查询订单列表
	 * @param form
	 * @return
	 */
	List<OrderListResult> queryOrderList(OrderQueryForm form);
}
