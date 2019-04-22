/**
 * 
 */
package com.cc.system.log.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.system.log.bean.OperationLogBean;
import com.cc.system.log.dao.LogDao;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.form.LogQueryForm;
import com.cc.system.log.form.SearchForm;
import com.cc.system.log.result.SearchResult;
import com.cc.system.log.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogDao logDao;
	
	@Override
	public Page<Map<String, Object>> queryOperationLogPage(LogQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(OperationLogBean.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringTools.isNullOrNone(form.getContent())){
			criteria.andLike("content", "%"+form.getContent()+"%");
		}
		if(!StringTools.isNullOrNone(form.getTitle())){
			criteria.andLike("title", "%"+form.getTitle()+"%");
		}
		if(!StringTools.isNullOrNone(form.getUserName())){
			criteria.andLike("userName", "%"+form.getUserName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getOperateTime())) {
			criteria.andBetween("operateTime", DateTools.getDate(form.getOperateTime()+" 00:00:00"), DateTools.getDate(form.getOperateTime()+" 23:59:59"));
		}
		if (!StringTools.isNullOrNone(form.getModule())) {
			criteria.andEqualTo("module", form.getModule());
		}
		if (!StringTools.isNullOrNone(form.getOperType())) {
			criteria.andEqualTo("operType", form.getOperType());
		}
		if(form.getUserId()!=null){
			criteria.andEqualTo("userId", form.getUserId());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<OperationLogBean> oprationLogBeanList = OperationLogBean.findByExample(OperationLogBean.class, example);
		PageInfo<OperationLogBean> pageInfo = new PageInfo<OperationLogBean>(oprationLogBeanList);
		if (ListTools.isEmptyOrNull(oprationLogBeanList)) {
			page.setMessage("没有查询到相关操作日志数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> logList = new ArrayList<Map<String,Object>>();
		for (OperationLogBean oprationLogBean : oprationLogBeanList) {
			Map<String, Object> log = new HashMap<String, Object>();
			log.put("id", oprationLogBean.getId());
			log.put("module", ModuleEnum.getNameByCode(oprationLogBean.getModule()));
			log.put("operType", OperTypeEnum.getNameByCode(oprationLogBean.getOperType()));
			log.put("userName", oprationLogBean.getUserName());
			log.put("operateTime", DateTools.getFormatDate(oprationLogBean.getOperateTime(), DateTools.DATEFORMAT));
			log.put("title", oprationLogBean.getTitle());
			logList.add(log);
		}
		page.setData(logList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public OperationLogBean getOperationLogBeanById(Long id) {
		return OperationLogBean.get(OperationLogBean.class, id);
	}
	
	@Override
	public Page<SearchResult> querySearchKeywordsPage(SearchForm form) {
		Page<SearchResult> page = new Page<SearchResult>();
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<SearchResult> searchResultList = logDao.querySearchKeywordsList(form);
		PageInfo<SearchResult> pageInfo = new PageInfo<SearchResult>(searchResultList);
		if (ListTools.isEmptyOrNull(searchResultList)) {
			page.setMessage("没有查询到相关关键字数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(searchResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
