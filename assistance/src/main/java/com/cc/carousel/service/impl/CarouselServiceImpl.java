/**
 * 
 */
package com.cc.carousel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.carousel.bean.CarouselBean;
import com.cc.carousel.bean.CarouselChannelBean;
import com.cc.carousel.bean.CarouselPlotBean;
import com.cc.carousel.dao.CarouselDao;
import com.cc.carousel.enums.CarouselStatusEnum;
import com.cc.carousel.form.CarouselForm;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.carousel.service.CarouselService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
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
public class CarouselServiceImpl implements CarouselService {
	
	@Autowired
	private CarouselDao carouselDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveCarousel(CarouselForm carousel) {
		CarouselBean carouselBean = null;
		if (carousel.getId()!=null) {
			carouselBean = CarouselBean.get(CarouselBean.class, carousel.getId());
			if (carouselBean == null) {
				throw new LogicException("E001", "轮播图不存在");
			}
		}else {
			carouselBean = new CarouselBean();
			carouselBean.setCreateTime(DateTools.now());
			carouselBean.setStatus(CarouselStatusEnum.DRAFT.getCode());
			carouselBean.setClicked(0);
		}
		carouselBean.setImageUrl(carousel.getImageUrl());
		carouselBean.setName(carousel.getName());
		carouselBean.setPath(carousel.getPath());
		int row = carouselBean.save();
		if (row!=1) {
			throw new LogicException("E002", "保存轮播图失败");
		}
		Example carouselChannelBeanExample = new Example(CarouselChannelBean.class);
		Criteria carouselChannelBeanCriteria = carouselChannelBeanExample.createCriteria();
		carouselChannelBeanCriteria.andEqualTo("carouselId", carouselBean.getId());
		CarouselChannelBean.deleteByExample(CarouselChannelBean.class, carouselChannelBeanExample);
		if(!ListTools.isEmptyOrNull(carousel.getChannelList())){
			for (Integer channelId : carousel.getChannelList()) {
				CarouselChannelBean carouselChannelBean = new CarouselChannelBean();
				carouselChannelBean.setCarouselId(carouselBean.getId());
				carouselChannelBean.setChannelId(new Long(channelId));
				row = carouselChannelBean.save();
				if (row!=1) {
					throw new LogicException("E003", "保存轮播图失败");
				}
			}
		}
		List<CarouselPlotBean> carouselPlotBeanList = CarouselPlotBean.findAllByParams(CarouselPlotBean.class, "carouselId", carouselBean.getId());
		if(ListTools.isEmptyOrNull(carouselPlotBeanList)){
			if(!StringTools.isNullOrNone(carousel.getPlot())){
				CarouselPlotBean carouselPlotBean = new CarouselPlotBean();
				carouselPlotBean.setCarouselId(carouselBean.getId());
				carouselPlotBean.setPlot(carousel.getPlot().getBytes());
				row = carouselPlotBean.save();
				if (row!=1) {
					throw new LogicException("E003", "保存轮播图详情失败");
				}
			}
			return;
		}
		if(StringTools.isNullOrNone(carousel.getPlot())){
			Example example = new Example(CarouselPlotBean.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("carouselId", carouselBean.getId());
			CarouselPlotBean.deleteByExample(CarouselPlotBean.class, example);
		}else{
			CarouselPlotBean carouselPlotBean = new CarouselPlotBean();
			carouselPlotBean.setPlot(carousel.getPlot().getBytes());
			Example example = new Example(CarouselPlotBean.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("carouselId", carouselBean.getId());
			carouselPlotBean.updateByExample(example);
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteCarousel(Long id) {
		Example example = new Example(CarouselPlotBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("carouselId", id);
		CarouselPlotBean.deleteByExample(CarouselPlotBean.class, example);
		Example carouselChannelBeanExample = new Example(CarouselChannelBean.class);
		Criteria carouselChannelBeanCriteria = carouselChannelBeanExample.createCriteria();
		carouselChannelBeanCriteria.andEqualTo("carouselId", id);
		CarouselChannelBean.deleteByExample(CarouselChannelBean.class, carouselChannelBeanExample);
		CarouselBean carouselBean = new CarouselBean();
		carouselBean.setId(id);
		int row = carouselBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除轮播图失败");
		}
	}

	@Override
	public Page<Map<String, Object>> queryCarouselPage(CarouselQueryForm form) {
		Page<Map<String, Object>> page = new Page<Map<String,Object>>();
		PageHelper.orderBy(String.format("c.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<CarouselBean> carouselBeanList = carouselDao.queryCarouselList(form);
		PageInfo<CarouselBean> pageInfo = new PageInfo<CarouselBean>(carouselBeanList);
		if (ListTools.isEmptyOrNull(carouselBeanList)) {
			page.setMessage("没有查询到相关轮播图数据");
			return page;
		}
		List<Map<String, Object>> carouselList = new ArrayList<Map<String,Object>>();
		for (CarouselBean carouselBean : carouselBeanList) {
			Map<String, Object> carousel = new HashMap<String, Object>();
			carousel.put("id", carouselBean.getId());
			carousel.put("status", carouselBean.getStatus());
			carousel.put("statusName", CarouselStatusEnum.getNameByCode(carouselBean.getStatus()));
			carousel.put("name", carouselBean.getName());
			carousel.put("createTime", DateTools.getFormatDate(carouselBean.getCreateTime(), DateTools.DATEFORMAT));
			carousel.put("imageUrl", carouselBean.getImageUrl());
			carousel.put("path", carouselBean.getPath());
			carousel.put("clicked", carouselBean.getClicked());
			carouselList.add(carousel);
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(carouselList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void changeCarouselStatus(Long id, CarouselStatusEnum status) {
		CarouselBean carouselBean = new CarouselBean();
		carouselBean.setId(id);
		carouselBean.setStatus(status.getCode());
		int row = carouselBean.update();
		if (row!=1) {
			throw new LogicException("E001", "修改轮播图状态失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void clickCarousel(Long id) {
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if (carouselBean!=null) {
			carouselBean.setClicked(carouselBean.getClicked() + 1);
			int row = carouselBean.update();
			if (row != 1) {
				throw new LogicException("E001", "点击轮播图失败");
			} 
		}
	}

	@Override
	public List<CarouselBean> queryCarouselList(CarouselQueryForm form) {
		PageHelper.orderBy(String.format("c.%s %s", form.getSort(), form.getOrder()));
		List<CarouselBean> carouselBeanList = carouselDao.queryCarouselList(form);
		return carouselBeanList;
	}

}
