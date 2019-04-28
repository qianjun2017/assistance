/**
 * 
 */
package com.cc.channel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.bean.ChannelSubjectBean;
import com.cc.channel.bean.ChannelSubjectItemBean;
import com.cc.channel.dao.ChannelSubjectDao;
import com.cc.channel.enums.ChannelSubjectStatusEnum;
import com.cc.channel.form.ChannelSubjectItemQueryForm;
import com.cc.channel.form.ChannelSubjectQueryForm;
import com.cc.channel.result.ChannelSubjectItemResult;
import com.cc.channel.result.ChannelSubjectResult;
import com.cc.channel.service.ChannelSubjectService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class ChannelSubjectServiceImpl implements ChannelSubjectService {
	
	@Autowired
	private ChannelSubjectDao channelSubjectDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveChannelSubject(ChannelSubjectBean channelSubjectBean) {
		int row = channelSubjectBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存专题失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteChannelSubject(Long id) {
		Example example = new Example(ChannelSubjectItemBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("subjectId", id);
		ChannelSubjectItemBean.deleteByExample(ChannelSubjectItemBean.class, example);
		ChannelSubjectBean channelSubjectBean = new ChannelSubjectBean();
		channelSubjectBean.setId(id);
		int row = channelSubjectBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除专题失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveChannelSubjectItemList(List<ChannelSubjectItemBean> channelSubjectItemBeanList) {
		for(ChannelSubjectItemBean channelSubjectItemBean: channelSubjectItemBeanList) {
			List<ChannelSubjectItemBean> findAllByParams = ChannelSubjectItemBean.findAllByParams(ChannelSubjectItemBean.class, "subjectId", channelSubjectItemBean.getSubjectId(), "itemId", channelSubjectItemBean.getItemId());
			if(ListTools.isEmptyOrNull(findAllByParams)){
				int row = channelSubjectItemBean.save();
				if (row != 1) {
					throw new LogicException("E001", "保存专题内容失败");
				} 
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteChannelSubjectItem(Long id) {
		ChannelSubjectItemBean channelSubjectItemBean = new ChannelSubjectItemBean();
		channelSubjectItemBean.setId(id);
		int row = channelSubjectItemBean.delete();
		if(row!=1){
			throw new LogicException("E001", "删除专题内容失败");
		}
	}

	@Override
	public Page<ChannelSubjectResult> queryChannelSubjectPage(ChannelSubjectQueryForm form) {
		Page<ChannelSubjectResult> page = new Page<ChannelSubjectResult>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		Example example = new Example(ChannelSubjectBean.class);
		Criteria criteria = example.createCriteria();
		if(form.getChannelId()!=null){
			criteria.andEqualTo("channelId", form.getChannelId());
		}
		if(!StringTools.isNullOrNone(form.getStatus())) {
			criteria.andLike("status", form.getStatus());
		}
		if(!StringTools.isNullOrNone(form.getSubjectName())) {
			criteria.andLike("subjectName", form.getSubjectName());
		}
		List<ChannelSubjectBean> channelSubjectBeanList = ChannelSubjectBean.findByExample(ChannelSubjectBean.class, example);
		PageInfo<ChannelSubjectBean> pageInfo = new PageInfo<ChannelSubjectBean>(channelSubjectBeanList);
		if (ListTools.isEmptyOrNull(channelSubjectBeanList)) {
			page.setMessage("没有查询到相关专题数据");
			return page;
		}
		List<ChannelSubjectResult> channelSubjectResultList = new ArrayList<ChannelSubjectResult>();
		for (ChannelSubjectBean channelSubjectBean : channelSubjectBeanList) {
			ChannelSubjectResult channelSubjectResult = new ChannelSubjectResult();
			channelSubjectResult.setId(channelSubjectBean.getId());
			channelSubjectResult.setCreateTime(channelSubjectBean.getCreateTime());
			channelSubjectResult.setSubjectName(channelSubjectBean.getSubjectName());
			channelSubjectResult.setStatus(channelSubjectBean.getStatus());
			channelSubjectResult.setStatusName(ChannelSubjectStatusEnum.getNameByCode(channelSubjectBean.getStatus()));
			ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelSubjectBean.getChannelId());
			if(channelBean!=null){
				channelSubjectResult.setChannelName(channelBean.getChannelName());
			}
			channelSubjectResultList.add(channelSubjectResult);
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(channelSubjectResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public List<ChannelSubjectItemResult> queryChannelSubjectItemList(ChannelSubjectItemQueryForm form) {
		ChannelSubjectBean channelSubjectBean = ChannelSubjectBean.get(ChannelSubjectBean.class, form.getSubjectId());
		if(channelSubjectBean!=null){
			ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelSubjectBean.getChannelId());
			if(channelBean!=null){
				form.setChannelCode(channelBean.getChannelCode());
				return channelSubjectDao.queryChannelSubjectItemList(form);
			}
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void changeChannelSubjectStatus(Long subjectId, ChannelSubjectStatusEnum channelSubjectStatusEnum) {
		ChannelSubjectBean channelSubjectBean = new ChannelSubjectBean();
		channelSubjectBean.setId(subjectId);
		channelSubjectBean.setStatus(channelSubjectStatusEnum.getCode());
		int row = channelSubjectBean.update();
		if(row!=1){
			throw new LogicException("E001", "变更专题状态失败");
		}
	}

	@Override
	public List<ChannelSubjectBean> queryChannelSubjectList(Long channelId) {
		return ChannelSubjectBean.findAllByParams(ChannelSubjectBean.class, "channelId", channelId, "sort", "createTime", "order", "desc");
	}

}
