/**
 * 
 */
package com.cc.channel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.dao.ChannelDao;
import com.cc.channel.enums.ChannelStatusEnum;
import com.cc.channel.enums.ChannelTypeEnum;
import com.cc.channel.form.ChannelItemQueryForm;
import com.cc.channel.form.ChannelQueryForm;
import com.cc.channel.result.ChannelItemResult;
import com.cc.channel.service.ChannelService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class ChannelServiceImpl implements ChannelService {
	
	@Autowired
	private ChannelDao channelDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveChannel(ChannelBean channelBean) {
		int row = channelBean.save();
		if (row!=1) {
			throw new LogicException("E001","保存频道失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteChannel(Long id) {
		ChannelBean channelBean = new ChannelBean();
		channelBean.setId(id);
		channelBean.setStatus(ChannelStatusEnum.DELETE.getCode());
		int row = channelBean.update();
		if (row!=1) {
			throw new LogicException("E001","删除频道失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryChannelPage(ChannelQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		Example example = new Example(ChannelBean.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo("status", ChannelStatusEnum.DELETE.getCode());
		if (!StringTools.isNullOrNone(form.getStatus())) {
			criteria.andEqualTo("status", form.getStatus());
		}
		if (!StringTools.isNullOrNone(form.getChannelCode())) {
			criteria.andLike("channelCode", "%"+form.getChannelCode()+"%");
		}
		if (!StringTools.isNullOrNone(form.getChannelName())) {
			criteria.andLike("channelName", "%"+form.getChannelName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getChannelType())) {
			criteria.andEqualTo("channelType", form.getChannelType());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ChannelBean> channelBeanList = ChannelBean.findByExample(ChannelBean.class, example);
		PageInfo<ChannelBean> pageInfo = new PageInfo<ChannelBean>(channelBeanList);
		if (ListTools.isEmptyOrNull(channelBeanList)) {
			page.setMessage("没有查询到相关频道数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		List<Map<String, Object>> channelList = new ArrayList<Map<String,Object>>();
		for (ChannelBean channelBean : channelBeanList) {
			Map<String, Object> channel = new HashMap<String, Object>();
			channel.put("id", channelBean.getId());
			channel.put("channelCode", channelBean.getChannelCode());
			channel.put("channelName", channelBean.getChannelName());
			channel.put("channelIcon", channelBean.getChannelIcon());
			channel.put("channelType", channelBean.getChannelType());
			channel.put("channelTypeName", ChannelTypeEnum.getNameByCode(channelBean.getChannelType()));
			channel.put("status", channelBean.getStatus());
			channel.put("statusName", ChannelStatusEnum.getNameByCode(channelBean.getStatus()));
			channelList.add(channel);
		}
		page.setData(channelList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public List<ChannelBean> queryChannelBeanList(String channelCode) {
		if (!StringTools.isNullOrNone(channelCode)) {
			Example example = new Example(ChannelBean.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andLike("channelCode", "%"+channelCode+"%");
			criteria.andNotEqualTo("status", ChannelStatusEnum.DELETE.getCode());
			List<ChannelBean> channelBeanList = ChannelBean.findByExample(ChannelBean.class, example);
			return channelBeanList;
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void closeChannel(Long id) {
		ChannelBean channelBean = new ChannelBean();
		channelBean.setId(id);
		channelBean.setStatus(ChannelStatusEnum.CLOSE.getCode());
		int row = channelBean.update();
		if (row!=1) {
			throw new LogicException("E001","关闭频道失败");
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void openChannel(Long id) {
		ChannelBean channelBean = new ChannelBean();
		channelBean.setId(id);
		channelBean.setStatus(ChannelStatusEnum.NORMAL.getCode());
		int row = channelBean.update();
		if (row!=1) {
			throw new LogicException("E001","开启频道失败");
		}
	}

	@Override
	public List<ChannelBean> queryChannelBeanList(ChannelQueryForm form) {
		Example example = new Example(ChannelBean.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andNotEqualTo("status", ChannelStatusEnum.DELETE.getCode());
		if (!StringTools.isNullOrNone(form.getStatus())) {
			criteria.andEqualTo("status", form.getStatus());
		}
		if (!StringTools.isNullOrNone(form.getChannelCode())) {
			criteria.andLike("channelCode", "%"+form.getChannelCode()+"%");
		}
		if (!StringTools.isNullOrNone(form.getChannelName())) {
			criteria.andLike("channelName", "%"+form.getChannelName()+"%");
		}
		if (!StringTools.isNullOrNone(form.getChannelType())) {
			criteria.andEqualTo("channelType", form.getChannelType());
		}
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		List<ChannelBean> channelBeanList = ChannelBean.findByExample(ChannelBean.class, example);
		return channelBeanList;
	}

	@Override
	public List<ChannelBean> queryChannelBeanList(List<Long> channelIdList) {
		if (!ListTools.isEmptyOrNull(channelIdList)) {
			Example example = new Example(ChannelBean.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andIn("id", channelIdList);
			List<ChannelBean> channelBeanList = ChannelBean.findByExample(ChannelBean.class, example);
			return channelBeanList;
		}
		return null;
	}
	
	@Override
	public Page<ChannelItemResult> queryChannelItemPage(ChannelItemQueryForm form) {
		Page<ChannelItemResult> page = new Page<ChannelItemResult>();
		if(StringTools.isNullOrNone(form.getKeywords())){
			page.setMessage("请输入搜索关键字");
			return page;
		}
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, form.getChannelId());
		if(channelBean==null || !ChannelStatusEnum.NORMAL.equals(ChannelStatusEnum.getChannelStatusEnumByCode(channelBean.getStatus()))){
			page.setMessage("没有查询到相关频道数据");
			return page;
		}
		form.setChannelCode(channelBean.getChannelCode());
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<ChannelItemResult> channelItemResultList = channelDao.queryChannelItemList(form);
		PageInfo<ChannelItemResult> pageInfo = new PageInfo<ChannelItemResult>(channelItemResultList);
		if (ListTools.isEmptyOrNull(channelItemResultList)) {
			page.setMessage("没有查询到相关频道数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(channelItemResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

}
