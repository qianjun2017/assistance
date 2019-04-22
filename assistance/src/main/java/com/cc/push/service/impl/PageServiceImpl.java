package com.cc.push.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.push.service.PageService;
import com.cc.push.bean.PageBean;
import com.cc.push.form.PageQueryForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class PageServiceImpl implements PageService {

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void savePage(PageBean pageBean) {
		int row = pageBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存页面失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deletePage(Long id) {
		PageBean pageBean = new PageBean();
		pageBean.setId(id);
		int row = pageBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除页面失败");
		}
	}

	@Override
	public Page<PageBean> queryPagePage(PageQueryForm form) {
		Page<PageBean> page = new Page<PageBean>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		Example example = new Example(PageBean.class);
		Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getCode())){
			criteria.andLike("code", "%"+form.getCode()+"%");
		}
		if(!StringTools.isNullOrNone(form.getName())){
			criteria.andLike("name", "%"+form.getName()+"%");
		}
		List<PageBean> pageBeanList = PageBean.findByExample(PageBean.class, example);
		PageInfo<PageBean> pageInfo = new PageInfo<PageBean>(pageBeanList);
		if (ListTools.isEmptyOrNull(pageBeanList)) {
			page.setMessage("没有查询到相关页面数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(pageBeanList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}
}
