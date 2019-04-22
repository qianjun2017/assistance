/**
 * 
 */
package com.cc.novel.service.impl;

import java.util.List;

import com.cc.novel.bean.*;
import com.cc.novel.enums.NovelLoadingStatusEnum;
import com.cc.novel.form.NovelExchangeQueryForm;
import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.http.response.SpiderResponse;
import com.cc.spider.http.service.SpiderService;
import com.cc.system.log.utils.LogContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.novel.dao.NovelDao;
import com.cc.novel.enums.NovelFinishStatusEnum;
import com.cc.novel.enums.NovelStatusEnum;
import com.cc.novel.form.NovelChapterQueryForm;
import com.cc.novel.form.NovelQueryForm;
import com.cc.novel.http.response.data.NovelChapterData;
import com.cc.novel.http.response.data.NovelData;
import com.cc.novel.service.NovelService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Administrator
 *
 */
@Service
public class NovelServiceImpl implements NovelService {

	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private NovelDao novelDao;

	@Autowired
	private SpiderService spiderService;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveNovelData(NovelData novelData) {
		List<NovelBean> novelBeanList = NovelBean.findAllByParams(NovelBean.class, "spiderNo", novelData.getSpiderNo(), "itemId", novelData.getId());
		if (ListTools.isEmptyOrNull(novelBeanList)) {
			addNovelData(novelData);
		}else {
			NovelBean novelBean = novelBeanList.get(0);
			novelBean.setImgUrl(novelData.getImg());
			novelBean.setItemId(novelData.getId());
			novelBean.setName(novelData.getName());
			novelBean.setUrl(novelData.getUrl());
			novelBean.setType(novelData.getType());
			novelBean.setLastChapter(novelData.getLastChapter());
			novelBean.setLastTime(DateTools.getDate(novelData.getLastTime()));
			if("a".equals(novelData.getStatus())){
				novelBean.setNovelStatus(NovelFinishStatusEnum.CLOSED.getCode());
			}
			novelBean.update();
			List<NovelPlotBean> novelIntroBeanList = NovelPlotBean.findAllByParams(NovelPlotBean.class, "novelId", novelBean.getId());
			if (!StringTools.isNullOrNone(novelData.getIntro())) {
				if (ListTools.isEmptyOrNull(novelIntroBeanList)) {
					NovelPlotBean novelIntroBean = new NovelPlotBean();
					novelIntroBean.setPlot(novelData.getIntro().getBytes());
					novelIntroBean.setNovelId(novelBean.getId());
					novelIntroBean.insert();
				} else {
					NovelPlotBean novelIntroBean = novelIntroBeanList.get(0);
					novelIntroBean.setPlot(novelData.getIntro().getBytes());
					novelIntroBean.updateForce();
				}
			} else if (!ListTools.isEmptyOrNull(novelIntroBeanList)) {
				novelIntroBeanList.get(0).delete();
			}
			List<NovelAuthorBean> novelAuthorBeanList = NovelAuthorBean.findAllByParams(NovelAuthorBean.class, "novelId", novelBean.getId());
			if (!StringTools.isNullOrNone(novelData.getAuthor())) {
				List<AuthorBean> authorBeanList = AuthorBean.findAllByParams(AuthorBean.class, "authorName", novelData.getAuthor());
				AuthorBean authorBean;
				if (ListTools.isEmptyOrNull(authorBeanList)) {
					authorBean = new AuthorBean();
					authorBean.setAuthorName(novelData.getAuthor());
					authorBean.insert();
				}else {
					authorBean = authorBeanList.get(0);
				}
				if (ListTools.isEmptyOrNull(novelAuthorBeanList)) {
					NovelAuthorBean novelAuthorBean = new NovelAuthorBean();
					novelAuthorBean.setAuthorId(authorBean.getId());
					novelAuthorBean.setNovelId(novelBean.getId());
					novelAuthorBean.insert();
				}else{
					NovelAuthorBean novelAuthorBean = novelAuthorBeanList.get(0);
					novelAuthorBean.setAuthorId(authorBean.getId());
					novelAuthorBean.update();
				}
			} else if (!ListTools.isEmptyOrNull(novelAuthorBeanList)) {
				novelAuthorBeanList.get(0).delete();
			}
		}
	}
	
	/**
	 * 新增小说数据
	 * @param novelData
	 */
	private void addNovelData(NovelData novelData) {
		NovelBean novelBean = new NovelBean();
		novelBean.setImgUrl(novelData.getImg());
		novelBean.setItemId(novelData.getId());
		novelBean.setName(novelData.getName());
		novelBean.setUrl(novelData.getUrl());
		novelBean.setType(novelData.getType());
		novelBean.setSpiderNo(novelData.getSpiderNo());
		novelBean.setDownloading(NovelLoadingStatusEnum.UNLOAD.getCode());
		novelBean.setLastChapter(novelData.getLastChapter());
		novelBean.setLastTime(DateTools.getDate(novelData.getLastTime()));
		if("a".equals(novelData.getStatus())){
			novelBean.setNovelStatus(NovelFinishStatusEnum.CLOSED.getCode());
		}else if("b".equals(novelData.getStatus())){
			novelBean.setNovelStatus(NovelFinishStatusEnum.SERIALIZING.getCode());
		}
		novelBean.setStatus(NovelStatusEnum.ON.getCode());
		novelBean.setCreateTime(DateTools.now());
		SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("novel.integration");
		if (systemConfigBean!=null) {
			novelBean.setIntegration(Long.parseLong(systemConfigBean.getPropertyValue()));
		}
		novelBean.insert();
		if(!StringTools.isNullOrNone(novelData.getAuthor())){
			List<AuthorBean> authorBeanList = AuthorBean.findAllByParams(AuthorBean.class, "authorName", novelData.getAuthor());
			AuthorBean authorBean;
			if (ListTools.isEmptyOrNull(authorBeanList)) {
				authorBean = new AuthorBean();
				authorBean.setAuthorName(novelData.getAuthor());
				authorBean.insert();
			}else {
				authorBean = authorBeanList.get(0);
			}
			NovelAuthorBean novelAuthorBean = new NovelAuthorBean();
			novelAuthorBean.setAuthorId(authorBean.getId());
			novelAuthorBean.setNovelId(novelBean.getId());
			novelAuthorBean.insert();
		}
		if (!StringTools.isNullOrNone(novelData.getIntro())) {
			NovelPlotBean novelIntroBean = new NovelPlotBean();
			novelIntroBean.setPlot(novelData.getIntro().getBytes());
			novelIntroBean.setNovelId(novelBean.getId());
			novelIntroBean.insert();
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveNovelChapterData(NovelChapterData novelChapterData) {
		SystemConfigBean systemConfigBaseBean = systemConfigService.querySystemConfigBean("base." + novelChapterData.getSpiderNo());
		List<NovelBean> novelBeanList = NovelBean.findAllByParams(NovelBean.class, "spiderNo", systemConfigBaseBean.getPropertyValue(), "itemId", novelChapterData.getNovel());
		if(ListTools.isEmptyOrNull(novelBeanList)){
			return;
		}
		NovelBean novelBean = novelBeanList.get(0);
		List<NovelChapterBean> novelChapterBeanList = NovelChapterBean.findAllByParams(NovelChapterBean.class, "novelId", novelBean.getId(), "spiderNo", novelChapterData.getSpiderNo(), "itemId", novelChapterData.getId());
		NovelChapterBean novelChapterBean;
		if (ListTools.isEmptyOrNull(novelChapterBeanList)) {
			novelChapterBean = new NovelChapterBean();
			novelChapterBean.setSpiderNo(novelChapterData.getSpiderNo());
			novelChapterBean.setCreateTime(DateTools.now());
			SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("novel.chapter.integration");
			if (systemConfigBean!=null) {
				novelChapterBean.setIntegration(Long.parseLong(systemConfigBean.getPropertyValue()));
			}
		}else{
			novelChapterBean = novelChapterBeanList.get(0);
		}
		novelChapterBean.setItemId(novelChapterData.getId());
		novelChapterBean.setName(novelChapterData.getName());
		novelChapterBean.setNovelId(novelBean.getId());
		NovelChapterBean novelPreChapterBean = null;
		if(!StringTools.isNullOrNone(novelChapterData.getPre())){
			if("0".equals(novelChapterData.getPre())){
				novelChapterBean.setOrderNo(1L);
			}else{
				String preItemId = novelChapterData.getNovel()+novelChapterData.getPre();
				List<NovelChapterBean> novelPreChapterBeanList = NovelChapterBean.findAllByParams(NovelChapterBean.class, "novelId", novelBean.getId(), "spiderNo", novelChapterData.getSpiderNo(), "itemId", preItemId);
				if (!ListTools.isEmptyOrNull(novelPreChapterBeanList)){
					novelPreChapterBean = novelPreChapterBeanList.get(0);
					novelChapterBean.setPreId(novelPreChapterBean.getId());
					if(novelPreChapterBean.getOrderNo()!=null){
						novelChapterBean.setOrderNo(novelPreChapterBean.getOrderNo()+1);
					}
				}
			}
		}else{
			novelChapterBean.setOrderNo(1L);
		}
		NovelChapterBean novelNextChapterBean = null;
		if(!StringTools.isNullOrNone(novelChapterData.getNext())){
			String nextItemId = novelChapterData.getNovel()+novelChapterData.getNext();
			List<NovelChapterBean> novelNextChapterBeanList = NovelChapterBean.findAllByParams(NovelChapterBean.class, "novelId", novelBean.getId(), "spiderNo", novelChapterData.getSpiderNo(), "itemId", nextItemId);
			if (!ListTools.isEmptyOrNull(novelNextChapterBeanList)){
				novelNextChapterBean = novelNextChapterBeanList.get(0);
				novelChapterBean.setNextId(novelNextChapterBean.getId());
			}
		}
		novelChapterBean.save();
		if (!StringTools.isNullOrNone(novelChapterData.getContent())) {
			novelChapterData.setContent(novelChapterData.getContent().replaceAll("\\<a.*\\>.*\\</a\\>", ""));
			novelChapterData.setContent(novelChapterData.getContent().replaceAll("\\<script\\>.*\\</script\\>", ""));
		}
		NovelChapterContentBean novelChapterContentBean = novelDao.queryNovelChapterContent(novelChapterBean.getId());
		if (novelChapterContentBean == null){
			if (!StringTools.isNullOrNone(novelChapterData.getContent())){
				novelChapterContentBean = new NovelChapterContentBean();
				novelChapterContentBean.setContent(novelChapterData.getContent().getBytes());
				novelChapterContentBean.setChapterId(novelChapterBean.getId());
				novelChapterContentBean.insert();
			}
		} else{
			if (!StringTools.isNullOrNone(novelChapterData.getContent())){
				novelChapterContentBean.setContent(novelChapterData.getContent().getBytes());
				novelChapterContentBean.updateForce();
			} else{
				novelChapterContentBean.delete();
			}
		}
		if (novelPreChapterBean!=null){
			novelPreChapterBean.setNextId(novelChapterBean.getId());
			novelPreChapterBean.update();
		}
		if (novelNextChapterBean!=null){
			novelNextChapterBean.setPreId(novelChapterBean.getId());
			novelNextChapterBean.update();
		}
	}

	@Override
	public Page<NovelBean> queryNovelBeanPage(NovelQueryForm form) {
		Page<NovelBean> page = new Page<NovelBean>(); 
		PageHelper.orderBy(String.format("n.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<NovelBean> novelList = novelDao.queryNovelList(form);
		PageInfo<NovelBean> pageInfo = new PageInfo<NovelBean>(novelList);
		if (ListTools.isEmptyOrNull(novelList)) {
			page.setMessage("没有查询到相关小说数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(novelList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void changeNovelStatus(Long id, NovelStatusEnum status) {
		NovelBean novelBean = new NovelBean();
		novelBean.setId(id);
		novelBean.setStatus(status.getCode());
		int row = novelBean.update();
		if (row!=1) {
			throw new LogicException("E001", "更新小说状态失败");
		}
	}

	@Override
	public Page<NovelChapterBean> queryNovelChapterBeanPage(NovelChapterQueryForm form) {
		Page<NovelChapterBean> page = new Page<NovelChapterBean>();
		PageHelper.orderBy(String.format("%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<NovelChapterBean> novelChapterList = novelDao.queryNovelChapterList(form);
		PageInfo<NovelChapterBean> pageInfo = new PageInfo<NovelChapterBean>(novelChapterList);
		if (ListTools.isEmptyOrNull(novelChapterList)) {
			page.setMessage("没有查询到相关小说章节数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(novelChapterList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void updateNovelChapter(NovelChapterBean novelChapterBean, NovelChapterContentBean novelChapterContentBean) {
		int row = novelChapterBean.updateForce();
		if (row!=1) {
			throw new LogicException("E001", "保存章节失败");
		}
		NovelChapterContentBean novelChapterOldContentBean = novelDao.queryNovelChapterContent(novelChapterBean.getId());
		if (novelChapterOldContentBean==null){
			if (novelChapterContentBean!=null && novelChapterContentBean.getContent()!=null){
				novelChapterContentBean.setChapterId(novelChapterBean.getId());
				row = novelChapterContentBean.insert();
				if (row!=1) {
					throw new LogicException("E002", "保存章节内容失败");
				}
			}
		} else {
			if (novelChapterContentBean!=null && novelChapterContentBean.getContent() != null) {
				novelChapterOldContentBean.setContent(novelChapterContentBean.getContent());
				row = novelChapterOldContentBean.updateForce();
				if (row!=1) {
					throw new LogicException("E003", "修改章节内容失败");
				}
			} else {
				row = novelChapterOldContentBean.delete();
				if (row!=1) {
					throw new LogicException("E004", "删除章节内容失败");
				}
			}

		}

	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void deleteNovelChapter(Long id) {
		NovelChapterBean novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, id);
		if (novelChapterBean==null){
			throw new LogicException("E001", "小说章节不存在");
		}
		NovelChapterBean novelPreChapterBean = NovelChapterBean.get(NovelChapterBean.class, novelChapterBean.getPreId());
		if (novelPreChapterBean!=null){
			novelPreChapterBean.setNextId(novelChapterBean.getNextId());
			novelPreChapterBean.updateForce();
		}
		NovelChapterBean novelNextChapterBean = NovelChapterBean.get(NovelChapterBean.class, novelChapterBean.getNextId());
		if (novelNextChapterBean!=null){
			novelNextChapterBean.setPreId(novelChapterBean.getPreId());
			novelNextChapterBean.updateForce();
		}
		NovelChapterContentBean novelChapterContentBean = novelDao.queryNovelChapterContent(novelChapterBean.getId());
		if (novelChapterContentBean!=null){
			novelChapterContentBean.delete();
		}
		int row = novelChapterBean.delete();
		if (row!=1) {
			throw new LogicException("E002", "删除章节失败");
		}
	}

	/**
	 * 设置爬虫访问的用户信息
	 * @param request
	 */
	private void setSpiderUserInfo(SpiderRequest request) {
		List<SystemConfigBean> systemConfigBeanList = systemConfigService.querySystemConfigBeanList("spider");
		if (!ListTools.isEmptyOrNull(systemConfigBeanList)) {
			for (SystemConfigBean systemConfigBean : systemConfigBeanList) {
				if ("spider.userName".equals(systemConfigBean.getPropertyName())) {
					request.setUserName(systemConfigBean.getPropertyValue());
				}
				if ("spider.pwd".equals(systemConfigBean.getPropertyName())) {
					request.setPwd(systemConfigBean.getPropertyValue());
				}
				if ("spider.url".equals(systemConfigBean.getPropertyName())) {
					request.setUrl(systemConfigBean.getPropertyValue()+"/url");
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void downloadNovel(Long id, Boolean now) {
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if(novelBean==null){
			throw new LogicException("E001", "小说不存在,下载请求失败");
		}
		LogContextUtil.setOperContent("下载小说["+novelBean.getName()+"]");
		SpiderRequest request = new SpiderRequest();
		setSpiderUserInfo(request);
		if (StringTools.isAnyNullOrNone(new String[] {request.getUserName(),request.getPwd(),request.getUrl()})) {
			throw new LogicException("E002", "系统参数设置不完整,下载请求失败");
		}
		SystemConfigBean systemChapterConfigBean = systemConfigService.querySystemConfigBean("chapter."+novelBean.getSpiderNo());
		request.setSpiderNo(systemChapterConfigBean.getPropertyValue());
		if (StringTools.isNullOrNone(novelBean.getUrl())){
			throw new LogicException("E005", "小说地址不存在,下载请求失败");
		}
		request.putParam("url", novelBean.getUrl());
		request.putParam("type", "special");
		if(Boolean.TRUE.equals(now)){
			request.putParam("action", "now");
		}else{
			request.putParam("action", "add");
		}
		SpiderResponse<String> spiderResponse = spiderService.manageSpiderUrl(request);
		if (!spiderResponse.isSuccess()) {
			throw new LogicException("E006", spiderResponse.getMessage());
		}
		SystemConfigBean systemBaseUpdateConfigBean = systemConfigService.querySystemConfigBean("base.update."+novelBean.getSpiderNo());
		request.setSpiderNo(systemBaseUpdateConfigBean.getPropertyValue());
		spiderResponse = spiderService.manageSpiderUrl(request);
		if (!spiderResponse.isSuccess()) {
			throw new LogicException("E007", spiderResponse.getMessage());
		}
		novelBean.setDownloading(NovelLoadingStatusEnum.LOADING.getCode());
		novelBean.save();
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void stopDownloadNovel(Long id) {
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if(novelBean==null){
			throw new LogicException("E001", "小说不存在,停止下载请求失败");
		}
		LogContextUtil.setOperContent("停止下载小说["+novelBean.getName()+"]");
		SpiderRequest request = new SpiderRequest();
		setSpiderUserInfo(request);
		if (StringTools.isAnyNullOrNone(new String[] {request.getUserName(),request.getPwd(),request.getUrl()})) {
			throw new LogicException("E002", "系统参数设置不完整,停止下载请求失败");
		}
		SystemConfigBean systemChapterConfigBean = systemConfigService.querySystemConfigBean("chapter."+novelBean.getSpiderNo());
		request.setSpiderNo(systemChapterConfigBean.getPropertyValue());
		if (StringTools.isNullOrNone(novelBean.getUrl())){
			throw new LogicException("E005", "小说地址不存在,停止下载请求失败");
		}
		request.putParam("url", novelBean.getUrl());
		request.putParam("type", "special");
		request.putParam("action", "delete");
		request.putParam("itemId", novelBean.getItemId());
		SpiderResponse<String> spiderResponse = spiderService.manageSpiderUrl(request);
		if (!spiderResponse.isSuccess()) {
			throw new LogicException("E006", spiderResponse.getMessage());
		}
		SystemConfigBean systemBaseUpdateConfigBean = systemConfigService.querySystemConfigBean("base.update."+novelBean.getSpiderNo());
		request.setSpiderNo(systemBaseUpdateConfigBean.getPropertyValue());
		spiderResponse = spiderService.manageSpiderUrl(request);
		if (!spiderResponse.isSuccess()) {
			throw new LogicException("E007", spiderResponse.getMessage());
		}
		novelBean.setDownloading(NovelLoadingStatusEnum.LOADED.getCode());
		novelBean.setNovelStatus(NovelFinishStatusEnum.CLOSED.getCode());
		novelBean.save();
	}

	@Override
	public NovelChapterContentBean queryNovelChapterContent(Long chapterId) {
		return novelDao.queryNovelChapterContent(chapterId);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void orderNovelChapter(Long chapterId) {
		NovelChapterBean novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, chapterId);
		if (novelChapterBean==null){
			throw new LogicException("E001", "起始章节不存在");
		}
		if (novelChapterBean.getOrderNo()==null){
			novelChapterBean.setOrderNo(1L);
			novelChapterBean.update();
		}
		while(novelChapterBean.getNextId()!=null){
			NovelChapterBean novelNextChapterBean = new NovelChapterBean();
			novelNextChapterBean.setOrderNo(novelChapterBean.getOrderNo()+1);
			novelNextChapterBean.setId(novelChapterBean.getNextId());
			novelNextChapterBean.update();
			novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, novelNextChapterBean.getId());
		}
	}

	@Override
	public List<NovelChapterBean> queryNovelOriginChapterList(Long novelId) {
		return novelDao.queryNovelOriginChapterList(novelId);
	}

	@Override
	public NovelChapterBean queryNovelMaxOrderedChapter(Long novelId) {
		return novelDao.queryNovelMaxOrderedChapter(novelId);
	}

	@Override
	public Page<NovelBean> queryExchangeNovelPage(NovelExchangeQueryForm form) {
		Page<NovelBean> page = new Page<NovelBean>();
		PageHelper.orderBy(String.format("e.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<NovelBean> novelList = novelDao.queryExchangeNovelList(form);
		PageInfo<NovelBean> pageInfo = new PageInfo<NovelBean>(novelList);
		if (ListTools.isEmptyOrNull(novelList)) {
			page.setMessage("没有查询到相关小说数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(novelList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	public NovelChapterBean queryNovelMinOrderedChapter(Long novelId) {
		return novelDao.queryNovelMinOrderedChapter(novelId);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveNovelChapterUserBean(NovelChapterUserBean novelChapterUserBean) {
		int row = novelChapterUserBean.save();
		if (row!=1) {
			throw new LogicException("E001", "保存阅读历史失败");
		}
	}

	/* (non-Javadoc)
	 * @see com.cc.novel.service.NovelService#abandonNovel(java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void abandonNovel(Long id) {
		NovelBean novelBean = new NovelBean();
		novelBean.setId(id);
		novelBean.setNovelStatus(NovelFinishStatusEnum.ABANDONED.getCode());
		int row = novelBean.update();
		if (row!=1) {
			throw new LogicException("E001", "保存阅读历史失败");
		}
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void reloadNovel(Long id) {
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if(novelBean==null){
			throw new LogicException("E001", "小说不存在,下载请求失败");
		}
		LogContextUtil.setOperContent("重新下载小说["+novelBean.getName()+"]");
		SpiderRequest request = new SpiderRequest();
		setSpiderUserInfo(request);
		if (StringTools.isAnyNullOrNone(new String[] {request.getUserName(),request.getPwd(),request.getUrl()})) {
			throw new LogicException("E002", "系统参数设置不完整,下载请求失败");
		}
		SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("base.update."+novelBean.getSpiderNo());
		request.setSpiderNo(systemConfigBean.getPropertyValue());
		if (StringTools.isNullOrNone(novelBean.getUrl())){
			throw new LogicException("E005", "小说地址不存在,下载请求失败");
		}
		request.putParam("url", novelBean.getUrl());
		request.putParam("type", "special");
		request.putParam("action", "now");
		SpiderResponse<String> spiderResponse = spiderService.manageSpiderUrl(request);
		if (!spiderResponse.isSuccess()) {
			throw new LogicException("E006", spiderResponse.getMessage());
		}
	}

	@Override
	public void deleteNovel(Long id) {
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if(novelBean==null){
			throw new LogicException("E001", "小说不存在");
		}
		NovelLoadingStatusEnum novelLoadingStatusEnum = NovelLoadingStatusEnum.getNovelLoadingStatusEnumByCode(novelBean.getDownloading());
		if(NovelLoadingStatusEnum.LOADING.equals(novelLoadingStatusEnum)){
			//停止下载任务
			this.stopDownloadNovel(id);
		}
		//删除章节
		Example chapterExample = new Example(NovelChapterBean.class);
		Example.Criteria chapterCriteria = chapterExample.createCriteria();
		chapterCriteria.andEqualTo("novelId", novelBean.getId());
		List<NovelChapterBean> novelChapterBeanList = NovelChapterBean.findByExample(NovelChapterBean.class, chapterExample);
		if(!ListTools.isEmptyOrNull(novelChapterBeanList)) {
			//删除章节内容
			Example chapterContentExample = new Example(NovelChapterContentBean.class);
			Example.Criteria chapterContentCriteria = chapterContentExample.createCriteria();
			for (NovelChapterBean novelChapterBean : novelChapterBeanList) {
				chapterContentCriteria.andEqualTo("chapterId", novelChapterBean.getId());
				NovelChapterContentBean.deleteByExample(NovelChapterContentBean.class, chapterContentExample);
			}
		}
		NovelChapterBean.deleteByExample(NovelChapterBean.class, chapterExample);
		//删除简介
		Example introExample = new Example(NovelPlotBean.class);
		Example.Criteria introCriteria = introExample.createCriteria();
		introCriteria.andEqualTo("novelId", novelBean.getId());
		NovelPlotBean.deleteByExample(NovelPlotBean.class, introExample);
		//删除作者
		Example authorExample = new Example(NovelAuthorBean.class);
		Example.Criteria authorCriteria = authorExample.createCriteria();
		authorCriteria.andEqualTo("novelId", novelBean.getId());
		NovelAuthorBean.deleteByExample(NovelAuthorBean.class, authorExample);
		//删除小说信息
		novelBean.delete();
	}
}
