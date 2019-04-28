/**
 * 
 */
package com.cc.film.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.SetTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.form.QueryForm;
import com.cc.film.bean.ActorBean;
import com.cc.film.bean.DirectorBean;
import com.cc.film.bean.FilmActorBean;
import com.cc.film.bean.FilmAttributeBean;
import com.cc.film.bean.FilmBean;
import com.cc.film.bean.FilmCommentBean;
import com.cc.film.bean.FilmDirectorBean;
import com.cc.film.bean.FilmEvaluateBean;
import com.cc.film.bean.FilmPlotBean;
import com.cc.film.bean.FilmStillBean;
import com.cc.film.bean.FilmUrlBean;
import com.cc.film.dao.FilmDao;
import com.cc.film.enums.FilmStatusEnum;
import com.cc.film.form.FilmCommentQueryForm;
import com.cc.film.form.FilmExchangeQueryForm;
import com.cc.film.form.FilmQueryForm;
import com.cc.film.http.response.data.FilmActorData;
import com.cc.film.http.response.data.FilmData;
import com.cc.film.http.response.data.FilmDirectorData;
import com.cc.film.http.response.data.FilmUrlData;
import com.cc.film.result.FilmCommentResult;
import com.cc.film.result.FilmCountryResult;
import com.cc.film.result.FilmLanguageResult;
import com.cc.film.result.FilmYearResult;
import com.cc.film.service.FilmService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.cc.system.log.utils.LogContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Administrator
 *
 */
@Service
public class FilmServiceImpl implements FilmService {
	
	@Autowired
	private FilmDao filmDao;
	
	@Autowired
	private SystemConfigService systemConfigService;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFilmData(FilmData filmData) {
		List<FilmBean> filmBeanList = FilmBean.findAllByParams(FilmBean.class, "spiderNo", filmData.getSpiderNo(), "itemId", filmData.getId());
		if (ListTools.isEmptyOrNull(filmBeanList)) {
			addFilmData(filmData);
		}else {
			FilmBean filmBean = filmBeanList.get(0);
			filmBean.setItemId(filmData.getId());
			filmBean.setCountry(filmData.getCountry());
			filmBean.setLanguage(filmData.getLanguage());
			filmBean.setName(filmData.getName());
			filmBean.setYear(filmData.getYear());
			filmBean.setListImgUrl(filmData.getListImg());
			if (filmBean.getListImgUrl()==null) {
				filmBean.setListImgUrl(filmData.getImg());
			}
			filmBean.setImgUrl(filmData.getImg());
			if (filmBean.getImgUrl()==null) {
				filmBean.setImgUrl(filmData.getListImg());
			}
			filmBean.setUrl(filmData.getDetailUrl());
			filmBean.setSpiderNo(filmData.getSpiderNo());
			filmBean.update();
			List<FilmPlotBean> filmPlotBeanList = FilmPlotBean.findAllByParams(FilmPlotBean.class, "filmId", filmBean.getId());
			if (!StringTools.isNullOrNone(filmData.getPlot())) {
				if (ListTools.isEmptyOrNull(filmPlotBeanList)) {
					FilmPlotBean filmPlotBean = new FilmPlotBean();
					filmPlotBean.setFilmId(filmBean.getId());
					filmPlotBean.setPlot(filmData.getPlot().getBytes());
					filmPlotBean.insert();
				}else {
					FilmPlotBean filmPlotBean = filmPlotBeanList.get(0);
					filmPlotBean.setPlot(filmData.getPlot().getBytes());
					filmPlotBean.update();
				}
			}else if (!ListTools.isEmptyOrNull(filmPlotBeanList)) {
				filmPlotBeanList.get(0).delete();
			}
			Set<String> urlSet = new HashSet<String>();
			if (!StringTools.isNullOrNone(filmData.getUrl())) {
				urlSet.add(filmData.getUrl());
			}
			if (!ListTools.isEmptyOrNull(filmData.getUrls())) {
				for (FilmUrlData filmUrlData : filmData.getUrls()) {
					urlSet.add(filmUrlData.getUrl());
				}
			}
			Boolean hasDefaultUrl = Boolean.TRUE;
			List<FilmUrlBean> filmUrlBeanList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmBean.getId());
			if (!ListTools.isEmptyOrNull(filmUrlBeanList)) {
				for (FilmUrlBean filmUrlBean : filmUrlBeanList) {
					if (urlSet.contains(filmUrlBean.getUrl())) {
						urlSet.remove(filmUrlBean.getUrl());
					}else{
						if (filmUrlBean.getDefaultUrl()) {
							hasDefaultUrl = Boolean.FALSE;
						}
						filmUrlBean.delete();
					}
				}
			}
			if (!SetTools.isEmptyOrNull(urlSet)) {
				for(String url : urlSet){
					FilmUrlBean filmUrlBean = new FilmUrlBean();
					filmUrlBean.setFilmId(filmBean.getId());
					filmUrlBean.setUrl(url);
					filmUrlBean.setDefaultUrl(Boolean.FALSE);
					filmUrlBean.insert();
				}
			}
			if (!hasDefaultUrl) {
				List<FilmUrlBean> filmUrlList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmBean.getId());
				if (!ListTools.isEmptyOrNull(filmUrlList)) {
					FilmUrlBean filmUrlBean = filmUrlList.get(0);
					filmUrlBean.setDefaultUrl(Boolean.TRUE);
					filmUrlBean.update();
				}
			}
			List<FilmActorBean> filmActorBeanList = FilmActorBean.findAllByParams(FilmActorBean.class, "filmId", filmBean.getId());
			if (!ListTools.isEmptyOrNull(filmActorBeanList)) {
				for (FilmActorBean filmActorBean : filmActorBeanList) {
					filmActorBean.delete();
				}
			}
			if (!ListTools.isEmptyOrNull(filmData.getActors())) {
				for (FilmActorData filmActorData : filmData.getActors()) {
					List<ActorBean> actorBeanList = ActorBean.findAllByParams(ActorBean.class, "actorName", filmActorData.getActor());
					ActorBean actorBean = null;
					if (ListTools.isEmptyOrNull(actorBeanList)) {
						actorBean = new ActorBean();
						actorBean.setActorName(filmActorData.getActor());
						actorBean.insert();
					}else {
						actorBean = actorBeanList.get(0);
					}
					FilmActorBean filmActorBean = new FilmActorBean();
					filmActorBean.setFilmId(filmBean.getId());
					filmActorBean.setActorId(actorBean.getId());
					filmActorBean.insert();
				}
			}
			List<FilmDirectorBean> filmDirectorBeanList = FilmDirectorBean.findAllByParams(FilmDirectorBean.class, "filmId", filmBean.getId());
			if (!ListTools.isEmptyOrNull(filmDirectorBeanList)) {
				for (FilmDirectorBean filmDirectorBean : filmDirectorBeanList) {
					filmDirectorBean.delete();
				}
			}
			if (!ListTools.isEmptyOrNull(filmData.getDirectors())) {
				for (FilmDirectorData filmDirectorData : filmData.getDirectors()) {
					List<DirectorBean> directorBeanList = DirectorBean.findAllByParams(DirectorBean.class, "directorName", filmDirectorData.getDirector());
					DirectorBean directorBean = null;
					if (ListTools.isEmptyOrNull(directorBeanList)) {
						directorBean = new DirectorBean();
						directorBean.setDirectorName(filmDirectorData.getDirector());
						directorBean.insert();
					}else {
						directorBean = directorBeanList.get(0);
					}
					FilmDirectorBean filmDirectorBean = new FilmDirectorBean();
					filmDirectorBean.setDirectorId(directorBean.getId());
					filmDirectorBean.setFilmId(filmBean.getId());
					filmDirectorBean.insert();
				}
			}
		}
	}

	/**
	 * 新增影片
	 * @param filmData
	 */
	private void addFilmData(FilmData filmData) {
		FilmBean filmBean = new FilmBean();
		filmBean.setItemId(filmData.getId());
		filmBean.setCountry(filmData.getCountry());
		filmBean.setLanguage(filmData.getLanguage());
		filmBean.setName(filmData.getName());
		filmBean.setYear(filmData.getYear());
		filmBean.setListImgUrl(filmData.getListImg());
		if (filmBean.getListImgUrl()==null) {
			filmBean.setListImgUrl(filmData.getImg());
		}
		filmBean.setImgUrl(filmData.getImg());
		if (filmBean.getImgUrl()==null) {
			filmBean.setImgUrl(filmData.getListImg());
		}
		filmBean.setUrl(filmData.getDetailUrl());
		filmBean.setSpiderNo(filmData.getSpiderNo());
		filmBean.setCreateTime(DateTools.now());
		filmBean.setStatus(FilmStatusEnum.ON.getCode());
		filmBean.setThumbUp(0l);
		filmBean.setThumbDown(0l);
		SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("film.integration");
		if (systemConfigBean!=null) {
			filmBean.setIntegration(Long.parseLong(systemConfigBean.getPropertyValue()));
		}
		filmBean.insert();
		Set<String> urlSet = new HashSet<String>();
		if (!StringTools.isNullOrNone(filmData.getUrl())) {
			urlSet.add(filmData.getUrl());
		}
		if (!ListTools.isEmptyOrNull(filmData.getUrls())) {
			for (FilmUrlData filmUrlData : filmData.getUrls()) {
				urlSet.add(filmUrlData.getUrl());
			}
		}
		if (!SetTools.isEmptyOrNull(urlSet)) {
			Boolean hasDefaultUrl = Boolean.FALSE;
			for(String url : urlSet){
				FilmUrlBean filmUrlBean = new FilmUrlBean();
				filmUrlBean.setFilmId(filmBean.getId());
				filmUrlBean.setUrl(url);
				if(!hasDefaultUrl){
					filmUrlBean.setDefaultUrl(Boolean.TRUE);
					hasDefaultUrl = Boolean.TRUE;
				}else {
					filmUrlBean.setDefaultUrl(Boolean.FALSE);
				}
				filmUrlBean.insert();
			}
		}
		if (!ListTools.isEmptyOrNull(filmData.getActors())) {
			for (FilmActorData filmActorData : filmData.getActors()) {
				List<ActorBean> actorBeanList = ActorBean.findAllByParams(ActorBean.class, "actorName", filmActorData.getActor());
				ActorBean actorBean = null;
				if (ListTools.isEmptyOrNull(actorBeanList)) {
					actorBean = new ActorBean();
					actorBean.setActorName(filmActorData.getActor());
					actorBean.insert();
				}else {
					actorBean = actorBeanList.get(0);
				}
				FilmActorBean filmActorBean = new FilmActorBean();
				filmActorBean.setFilmId(filmBean.getId());
				filmActorBean.setActorId(actorBean.getId());
				filmActorBean.insert();
			}
		}
		if (!ListTools.isEmptyOrNull(filmData.getDirectors())) {
			for (FilmDirectorData filmDirectorData : filmData.getDirectors()) {
				List<DirectorBean> directorBeanList = DirectorBean.findAllByParams(DirectorBean.class, "directorName", filmDirectorData.getDirector());
				DirectorBean directorBean = null;
				if (ListTools.isEmptyOrNull(directorBeanList)) {
					directorBean = new DirectorBean();
					directorBean.setDirectorName(filmDirectorData.getDirector());
					directorBean.insert();
				}else {
					directorBean = directorBeanList.get(0);
				}
				FilmDirectorBean filmDirectorBean = new FilmDirectorBean();
				filmDirectorBean.setDirectorId(directorBean.getId());
				filmDirectorBean.setFilmId(filmBean.getId());
				filmDirectorBean.insert();
			}
		}
		if (!StringTools.isNullOrNone(filmData.getPlot())) {
			FilmPlotBean filmPlotBean = new FilmPlotBean();
			filmPlotBean.setFilmId(filmBean.getId());
			filmPlotBean.setPlot(filmData.getPlot().getBytes());
			filmPlotBean.insert();
		}
	}

	@Override
	public Page<FilmBean> queryFilmBeanPage(FilmQueryForm form) {
		Page<FilmBean> page = new Page<FilmBean>(); 
		PageHelper.orderBy(String.format("f.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FilmBean> filmList = filmDao.queryFilmList(form);
		PageInfo<FilmBean> pageInfo = new PageInfo<FilmBean>(filmList);
		if (ListTools.isEmptyOrNull(filmList)) {
			page.setMessage("没有查询到相关影片数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public List<ActorBean> queryFilmActorList(Long filmId) {
		List<ActorBean> actorList = filmDao.queryFilmActorList(filmId);
		return actorList;
	}

	@Override
	public List<DirectorBean> queryFilmDirectorList(Long filmId) {
		List<DirectorBean> directorList = filmDao.queryFilmDirectorList(filmId);
		return directorList;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteFilmUrl(Long id) {
		FilmUrlBean filmUrlBean = FilmUrlBean.get(FilmUrlBean.class, id);
		if (filmUrlBean==null) {
			throw new LogicException("E001", "影片播放地址不存在");
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, filmUrlBean.getFilmId());
		StringBuffer buffer = new StringBuffer();
		buffer.append("删除影片播放地址:");
		if(filmBean!=null){
			buffer.append("影片["+filmBean.getName()+"],");
		}
		buffer.append("播放地址["+filmUrlBean.getUrl()+"]");
		int row = filmUrlBean.delete();
		if (row!=1) {
			throw new LogicException("E002", "删除影片播放地址失败");
		}
		if (filmUrlBean.getDefaultUrl()) {
			List<FilmUrlBean> filmUrlBeanList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmBean.getId());
			if (!ListTools.isEmptyOrNull(filmUrlBeanList)) {
				FilmUrlBean filmUrl = filmUrlBeanList.get(0);
				filmUrl.setDefaultUrl(Boolean.TRUE);
				filmUrl.update();
				buffer.append(",设置默认播放地址["+filmUrl.getUrl()+"]");
			}
		}
		LogContextUtil.setOperContent(buffer.substring(0));
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void defaultFilmUrl(Long id) {
		FilmUrlBean filmUrlBean = FilmUrlBean.get(FilmUrlBean.class, id);
		if (filmUrlBean==null) {
			throw new LogicException("E001", "影片播放地址不存在");
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, filmUrlBean.getFilmId());
		StringBuffer buffer = new StringBuffer();
		buffer.append("设置影片默认地址:");
		if(filmBean!=null){
			buffer.append("影片["+filmBean.getName()+"],");
		}
		List<FilmUrlBean> filmUrlBeanList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmBean.getId());
		for (FilmUrlBean filmUrl : filmUrlBeanList) {
			if (filmUrl.getDefaultUrl()) {
				filmUrl.setDefaultUrl(Boolean.FALSE);
				int row = filmUrl.update();
				if (row!=1) {
					throw new LogicException("E002", "取消影片默认播放地址失败");
				}
				break;
			}
		}
		filmUrlBean.setDefaultUrl(Boolean.TRUE);
		int row = filmUrlBean.update();
		if (row!=1) {
			throw new LogicException("E003", "设置影片默认播放地址失败");
		}
		buffer.append("默认地址["+filmUrlBean.getUrl()+"]");
		LogContextUtil.setOperContent(buffer.substring(0));
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void changeFilmStatus(Long id, FilmStatusEnum status) {
		FilmBean filmBean = new FilmBean();
		filmBean.setId(id);
		filmBean.setStatus(status.getCode());
		int row = filmBean.update();
		if (row!=1) {
			throw new LogicException("E001", "更新影片状态失败");
		}
	}

	@Override
	public Page<FilmCommentResult> queryFilmCommentPage(FilmCommentQueryForm form) {
		Page<FilmCommentResult> page = new Page<FilmCommentResult>(); 
		PageHelper.orderBy(String.format("%s %s", "createTime", "desc"));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		Example example = new Example(FilmCommentBean.class);
		Criteria criteria = example.createCriteria();
		if (form.getFilmId()!=null) {
			criteria.andEqualTo("filmId", form.getFilmId());
		}
		if(!StringTools.isNullOrNone(form.getFilmName())){
			criteria.andLike("filmName", "%"+form.getFilmName()+"%");
		}
		if(!StringTools.isNullOrNone(form.getLeaguerName())){
			criteria.andLike("leaguerName", "%"+form.getLeaguerName()+"%");
		}
		List<FilmCommentBean> filmCommentBeanList = FilmCommentBean.findByExample(FilmCommentBean.class, example);
		PageInfo<FilmCommentBean> pageInfo = new PageInfo<FilmCommentBean>(filmCommentBeanList);
		if (ListTools.isEmptyOrNull(filmCommentBeanList)) {
			page.setMessage("没有查询到相关评论数据");
			return page;
		}
		List<FilmCommentResult> filmCommentResultList = new ArrayList<FilmCommentResult>();
		for (FilmCommentBean filmCommentBean : filmCommentBeanList) {
			FilmCommentResult filmCommentResult = new FilmCommentResult();
			filmCommentResult.setId(filmCommentBean.getId());
			filmCommentResult.setFilmName(filmCommentBean.getFilmName());
			filmCommentResult.setNickName(filmCommentBean.getLeaguerName());
			filmCommentResult.setCreateTime(filmCommentBean.getCreateTime());
			if(filmCommentBean.getComment()!=null){
				filmCommentResult.setComment(new String(filmCommentBean.getComment()));
			}
			LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, filmCommentBean.getLeaguerId());
			if (leaguerBean!=null) {
				filmCommentResult.setAvatarUrl(leaguerBean.getAvatarUrl());
			}
			filmCommentResultList.add(filmCommentResult);
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmCommentResultList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFilmComment(FilmCommentBean filmCommentBean) {
		int row = filmCommentBean.save();
		if (row!=1) {
			throw new LogicException("E001", "评论失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteFilmComment(Long id) {
		FilmCommentBean filmCommentBean = new FilmCommentBean();
		filmCommentBean.setId(id);
		int row = filmCommentBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除评论失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void gaveAThumbUp(Long id) {
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		Long thumbUp = filmBean.getThumbUp();
		FilmBean filmThumb = new FilmBean();
		filmThumb.setId(id);
		filmThumb.setThumbUp(thumbUp+1);
		int row = filmThumb.update();
		if (row!=1) {
			throw new LogicException("E001", "点赞失败");
		}
		LogContextUtil.setOperContent("影片["+filmBean.getName()+"]被赞了");
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void gaveAThumbDown(Long id) {
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		Long thumbDown = filmBean.getThumbDown();
		FilmBean filmThumb = new FilmBean();
		filmThumb.setId(id);
		filmThumb.setThumbDown(thumbDown+1);
		int row = filmThumb.update();
		if (row!=1) {
			throw new LogicException("E001", "踩滑了");
		}
		LogContextUtil.setOperContent("影片["+filmBean.getName()+"]被踩了");
	}

	@Override
	public Page<FilmBean> queryExchangeFilmPage(FilmExchangeQueryForm form) {
		Page<FilmBean> page = new Page<FilmBean>(); 
		PageHelper.orderBy(String.format("e.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FilmBean> filmList = filmDao.queryExchangeFilmList(form);
		PageInfo<FilmBean> pageInfo = new PageInfo<FilmBean>(filmList);
		if (ListTools.isEmptyOrNull(filmList)) {
			page.setMessage("没有查询到相关影片数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFilmEvaluate(FilmEvaluateBean filmEvaluateBean) {
		filmEvaluateBean.setCreateTime(DateTools.now());
		int row = 0;
		if(FilmEvaluateBean.get(FilmEvaluateBean.class, filmEvaluateBean.getId())==null){
			row = filmEvaluateBean.insert();
			if(row!=1){
				throw new LogicException("E001", "保存影片评价失败");
			}
		}else {
			row = filmEvaluateBean.updateForce();
			if(row!=1){
				throw new LogicException("E001", "更新影片评价失败");
			}
		}
		int integration = 10;
		if(filmEvaluateBean.getBelle()>1){
			integration = integration + 5*(filmEvaluateBean.getBelle()-1);
		}
		if(filmEvaluateBean.getFrequency()>2){
			integration = integration + 8*(filmEvaluateBean.getFrequency()-2);
		}
		if(filmEvaluateBean.getQuality()>5){
			integration = integration + 2*(filmEvaluateBean.getQuality()-5);
		}
		if (filmEvaluateBean.getRecommend()) {
			integration = integration + 20;
		}
		FilmBean filmBean = new FilmBean();
		filmBean.setId(filmEvaluateBean.getId());
		filmBean.setIntegration(new Long(integration));
		row = filmBean.update();
		if(row!=1){
			throw new LogicException("E002", "保存影片积分失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFilmAttribute(FilmAttributeBean filmAttributeBean) {
		int row = filmAttributeBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存影片属性失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteFilmAttribute(Long filmId, String attribute) {
		Example example = new Example(FilmAttributeBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("filmId", filmId);
		criteria.andEqualTo("attribute", attribute);
		FilmAttributeBean.deleteByExample(FilmAttributeBean.class, example);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveFilmStill(FilmStillBean filmStillBean) {
		int row = filmStillBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存影片剧照失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteFilmStill(Long id) {
		FilmStillBean filmStillBean = new FilmStillBean();
		filmStillBean.setId(id);
		int row = filmStillBean.delete();
		if (row!=1) {
			throw new LogicException("E001", "删除剧照失败");
		}
	}

	@Override
	public Page<FilmBean> queryNewFilmBeanPage(QueryForm form) {
		Page<FilmBean> page = new Page<FilmBean>(); 
		PageHelper.orderBy(String.format("f.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FilmBean> filmList = filmDao.queryNewFilmList();
		PageInfo<FilmBean> pageInfo = new PageInfo<FilmBean>(filmList);
		if (ListTools.isEmptyOrNull(filmList)) {
			page.setMessage("没有查询到相关影片数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}
	
	@Override
	public Page<FilmBean> queryRecommendFilmBeanPage(QueryForm form) {
		Page<FilmBean> page = new Page<FilmBean>(); 
		PageHelper.orderBy(String.format("e.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FilmBean> filmList = filmDao.queryRecommendFilmList();
		PageInfo<FilmBean> pageInfo = new PageInfo<FilmBean>(filmList);
		if (ListTools.isEmptyOrNull(filmList)) {
			page.setMessage("没有查询到相关影片数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}
	
	@Override
	public Page<FilmBean> queryHotFilmBeanPage(QueryForm form) {
		Page<FilmBean> page = new Page<FilmBean>(); 
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<FilmBean> filmList = filmDao.queryHotFilmList();
		PageInfo<FilmBean> pageInfo = new PageInfo<FilmBean>(filmList);
		if (ListTools.isEmptyOrNull(filmList)) {
			page.setMessage("没有查询到相关影片数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(filmList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public Integer queryFilmPlayed(Long id) {
		return filmDao.queryFilmPlayed(id);
	}

	/* (non-Javadoc)
	 * @see com.cc.film.service.FilmService#queryFilmYearList()
	 */
	@Override
	public List<FilmYearResult> queryFilmYearList() {
		return filmDao.queryFilmYearList();
	}

	/* (non-Javadoc)
	 * @see com.cc.film.service.FilmService#queryFilmLanguageList()
	 */
	@Override
	public List<FilmLanguageResult> queryFilmLanguageList() {
		return filmDao.queryFilmLanguageList();
	}

	/* (non-Javadoc)
	 * @see com.cc.film.service.FilmService#queryFilmCountryList()
	 */
	@Override
	public List<FilmCountryResult> queryFilmCountryList() {
		return filmDao.queryFilmCountryList();
	}

}
