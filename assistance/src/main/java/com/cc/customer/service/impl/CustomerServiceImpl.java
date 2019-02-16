/**
 * 
 */
package com.cc.customer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.customer.bean.CustomerBean;
import com.cc.customer.enums.CustomerStatusEnum;
import com.cc.customer.form.CustomerQueryForm;
import com.cc.customer.service.CustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveCustomer(CustomerBean customerBean) {
		int row = customerBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存客户失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void lockCustomer(Long id) {
		CustomerBean customerBean = new CustomerBean();
		customerBean.setId(id);
		customerBean.setStatus(CustomerStatusEnum.LOCKED.getCode());
		int row = customerBean.update();
		if(row!=1){
			throw new LogicException("E001", "锁定客户失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void unLockCustomer(Long id) {
		CustomerBean customerBean = new CustomerBean();
		customerBean.setId(id);
		customerBean.setStatus(CustomerStatusEnum.NORMAL.getCode());
		int row = customerBean.update();
		if(row!=1){
			throw new LogicException("E001", "锁定客户失败");
		}
	}

	@Override
	public Page<CustomerBean> queryCustomerPage(CustomerQueryForm form) {
		Page<CustomerBean> page = new Page<CustomerBean>();
		Example example = new Example(CustomerBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getNickName())){
			criteria.andLike("nickName", "%"+form.getNickName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getStatus())) {
			criteria.andEqualTo("status", form.getStatus());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<CustomerBean> customerBeanList = CustomerBean.findByExample(CustomerBean.class, example);
		PageInfo<CustomerBean> pageInfo = new PageInfo<CustomerBean>(customerBeanList);
		if (ListTools.isEmptyOrNull(customerBeanList)) {
			page.setMessage("没有查询到相关客户数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(customerBeanList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public Boolean hasCustomerRegister(String openid) {
		List<CustomerBean> customerBeanList = CustomerBean.findAllByParams(CustomerBean.class, "openid", openid);
		return !ListTools.isEmptyOrNull(customerBeanList);
	}

}
