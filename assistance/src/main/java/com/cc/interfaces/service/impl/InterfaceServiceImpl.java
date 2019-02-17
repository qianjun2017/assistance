/**
 * 
 */
package com.cc.interfaces.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.interfaces.bean.InterfaceBean;
import com.cc.interfaces.enums.InterfaceEnum;
import com.cc.interfaces.form.InterfaceQueryForm;
import com.cc.interfaces.service.InterfaceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author ws_yu
 *
 */
@Service
public class InterfaceServiceImpl implements InterfaceService {

	@Override
	@Transactional
	public void saveInterface(InterfaceBean interfaceBean) {
		int row = interfaceBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存接口失败");
		}
	}

	@Override
	@Transactional
	public void saveInterface(InterfaceEnum interfaceEnum, String requestBody, String responseBody) {
		InterfaceBean interfaceBean = new InterfaceBean();
		interfaceBean.setCode(interfaceEnum.getCode());
		interfaceBean.setName(interfaceEnum.getName());
		interfaceBean.setType(interfaceEnum.getType());
		if(!StringTools.isNullOrNone(requestBody)){
			interfaceBean.setRequest(requestBody.getBytes());
		}
		if(!StringTools.isNullOrNone(responseBody)){
			interfaceBean.setResponse(responseBody.getBytes());
		}
		interfaceBean.setCreateTime(DateTools.now());
		saveInterface(interfaceBean);
	}

	@Override
	public Page<Map<String, Object>> queryInterfacePage(InterfaceQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(InterfaceBean.class);
		Example.Criteria criteria = example.createCriteria();
		if (!StringTools.isNullOrNone(form.getCode())) {
			criteria.andEqualTo("code", form.getCode());
		}
		if (form.getCreateTimeStart() != null){
			criteria.andGreaterThan("createTime", form.getCreateTimeStart());
		}
		if (form.getCreateTimeEnd() != null) {
			criteria.andLessThan("createTime", form.getCreateTimeEnd());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<InterfaceBean> interfaceBeanList = InterfaceBean.findByExample(InterfaceBean.class, example);
		PageInfo<InterfaceBean> pageInfo = new PageInfo<InterfaceBean>(interfaceBeanList);
		if (ListTools.isEmptyOrNull(interfaceBeanList)) {
			page.setMessage("没有查询到相关接口调用数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> interfaceMapList = new ArrayList<Map<String,Object>>();
		for (InterfaceBean interfaceBean : interfaceBeanList) {
			Map<String, Object> interfaceBeanMap = new HashMap<String, Object>();
			interfaceBeanMap.put("id", interfaceBean.getId());
			interfaceBeanMap.put("code", interfaceBean.getCode());
			interfaceBeanMap.put("name", interfaceBean.getName());
			interfaceBeanMap.put("createTime", DateTools.getFormatDate(interfaceBean.getCreateTime(), DateTools.DATEFORMAT));
			interfaceBeanMap.put("type", interfaceBean.getType());
			interfaceBeanMap.put("request", new String(interfaceBean.getRequest()));
			interfaceBeanMap.put("response", new String(interfaceBean.getResponse()));
			interfaceMapList.add(interfaceBeanMap);
		}
		page.setData(interfaceMapList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
