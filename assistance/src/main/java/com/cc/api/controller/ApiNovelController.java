package com.cc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.enums.ExchangeStatusEnum;
import com.cc.exchange.service.ExchangeService;
import com.cc.novel.bean.AuthorBean;
import com.cc.novel.bean.NovelAuthorBean;
import com.cc.novel.bean.NovelBean;
import com.cc.novel.bean.NovelChapterBean;
import com.cc.novel.bean.NovelChapterContentBean;
import com.cc.novel.bean.NovelChapterUserBean;
import com.cc.novel.bean.NovelPlotBean;
import com.cc.novel.enums.NovelLoadingStatusEnum;
import com.cc.novel.enums.NovelStatusEnum;
import com.cc.novel.form.NovelChapterQueryForm;
import com.cc.novel.form.NovelExchangeQueryForm;
import com.cc.novel.form.NovelQueryForm;
import com.cc.novel.result.NovelChapterResult;
import com.cc.novel.result.NovelResult;
import com.cc.novel.service.NovelService;
import com.cc.system.log.utils.LogContextUtil;

@Controller
@RequestMapping("/api/novel")
public class ApiNovelController {
	
	@Autowired
	private NovelService novelService;
	
	@Autowired
	private ExchangeService exchangeService;

	/**
	 * 分页查询小说
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<NovelBean> queryNovelBeanPage(@ModelAttribute NovelQueryForm form){
		form.setStatus(NovelStatusEnum.ON.getCode());
		return novelService.queryNovelBeanPage(form);
	}
	
	/**
	 * 查询小说
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<NovelResult> queryNovel(@PathVariable Long id){
		Response<NovelResult> response = new Response<NovelResult>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		NovelResult novelResult = new NovelResult();
		novelResult.setId(novelBean.getId());
		novelResult.setImgUrl(novelBean.getImgUrl());
		novelResult.setCreateTime(novelBean.getCreateTime());
		novelResult.setIntegration(novelBean.getIntegration());
		novelResult.setName(novelBean.getName());
		novelResult.setStatus(novelBean.getStatus());
		novelResult.setNovelStatus(novelBean.getNovelStatus());
		novelResult.setDownloading(novelBean.getDownloading());
		novelResult.setUrl(novelBean.getUrl());
		novelResult.setType(novelBean.getType());
		novelResult.setLastChapter(novelBean.getLastChapter());
		novelResult.setLastTime(novelBean.getLastTime());
		List<NovelPlotBean> novelIntroBeanList = NovelPlotBean.findAllByParams(NovelPlotBean.class, "novelId", novelBean.getId());
		if (!ListTools.isEmptyOrNull(novelIntroBeanList)) {
			novelResult.setPlot(new String(novelIntroBeanList.get(0).getPlot()));
		}
		List<NovelAuthorBean> novelAuthorBeanList = NovelAuthorBean.findAllByParams(NovelAuthorBean.class, "novelId", novelBean.getId());
		if (!ListTools.isEmptyOrNull(novelAuthorBeanList)) {
			AuthorBean authorBean = AuthorBean.get(AuthorBean.class, novelAuthorBeanList.get(0).getAuthorId());
			novelResult.setAuthorName(authorBean.getAuthorName());
		}
		response.setData(novelResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询小说章节
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/chapter/page", method = RequestMethod.GET)
	public Page<NovelChapterBean> queryNovelChapterBeanPage(@ModelAttribute NovelChapterQueryForm form){
		Page<NovelChapterBean> page = new Page<NovelChapterBean>();
		if (form.getNovelId()==null){
			page.setMessage("请选择小说");
			return page;
		}
		return novelService.queryNovelChapterBeanPage(form);
	}
	
	/**
	 * 查询小说章节详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/chapter/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<NovelChapterResult> queryNovelChapter(@PathVariable Long id){
		Response<NovelChapterResult> response = new Response<NovelChapterResult>();
		NovelChapterBean novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, id);
		if (novelChapterBean==null){
			response.setMessage("章节不存在或已删除");
			return response;
		}
		NovelChapterResult novelChapterResult = new NovelChapterResult();
		novelChapterResult.setId(novelChapterBean.getId());
		novelChapterResult.setName(novelChapterBean.getName());
		novelChapterResult.setNovelId(novelChapterBean.getNovelId());
		novelChapterResult.setPreId(novelChapterBean.getPreId());
		if (novelChapterBean.getPreId()!=null){
			NovelChapterBean novelPreChapterBean = NovelChapterBean.get(NovelChapterBean.class, novelChapterBean.getPreId());
			if (novelPreChapterBean!=null){
				novelChapterResult.setPreName(novelPreChapterBean.getName());
			}
		}
		novelChapterResult.setNextId(novelChapterBean.getNextId());
		if (novelChapterBean.getNextId()!=null){
			NovelChapterBean novelNextChapterBean = NovelChapterBean.get(NovelChapterBean.class, novelChapterBean.getNextId());
			if (novelNextChapterBean!=null){
				novelChapterResult.setNextName(novelNextChapterBean.getName());
			}
		}
		NovelChapterContentBean novelChapterContentBean = novelService.queryNovelChapterContent(novelChapterBean.getId());
		if(novelChapterContentBean!=null && novelChapterContentBean.getContent()!=null){
			novelChapterResult.setContent(new String(novelChapterContentBean.getContent()));
		}
		response.setData(novelChapterResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 取消兑换申请
	 * @param novelId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/cancel/{novelId:\\d+}", method = RequestMethod.POST)
	public Response<Object> requestCancelExchange(@PathVariable Long novelId, @RequestParam Long leaguerId){
		Response<Object> response = new Response<Object>();
		List<ExchangeBean> exchangeBeanList = ExchangeBean.findAllByParams(ExchangeBean.class, "itemId", novelId, "leaguerId", leaguerId, "channelCode", "novel", "sort", "createTime", "order", "desc");
		if (ListTools.isEmptyOrNull(exchangeBeanList)) {
			response.setMessage("您还未兑换小说");
			return response;
		}
		ExchangeBean exchangeBean = exchangeBeanList.get(0);
		ExchangeStatusEnum exchangeStatusEnum = ExchangeStatusEnum.getExchangeStatusEnumByCode(exchangeBean.getStatus());
		if (ExchangeStatusEnum.PENDING.equals(exchangeStatusEnum)) {
			response.setMessage("取消兑换申请处理中");
			return response;
		}
		if (ExchangeStatusEnum.CANCELLED.equals(exchangeStatusEnum)) {
			response.setMessage("兑换已取消");
			return response;
		}
		try {
			exchangeService.requestCancelExchange(exchangeBean.getId());
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
		}
		return response;
	}

	/**
	 * 阅读小说
	 * @param novelId
	 * @param chapterId
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public Response<Long> readNovel(Long novelId, Long chapterId, Long leaguerId){
		Response<Long> response = new Response<Long>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, novelId);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		if (chapterId!=null){
			response.setData(chapterId);
		}else{
			List<NovelChapterUserBean> novelChapterUserBeanList = NovelChapterUserBean.findAllByParams(NovelChapterUserBean.class, "userId", leaguerId, "novelId", novelId);
			if (ListTools.isEmptyOrNull(novelChapterUserBeanList)){
				NovelChapterBean novelChapterBean = novelService.queryNovelMinOrderedChapter(novelId);
				if(novelChapterBean!=null){
					response.setData(novelChapterBean.getId());
				}else{
					response.setMessage("章节维护中，请通过章节列表选择具体章节");
					return response;
				}
			}else{
				response.setData(novelChapterUserBeanList.get(0).getChapterId());
			}
		}
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 阅读小说章节，记录阅读位置
	 * @param id
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/chapter/read/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> readNovelChapter(@PathVariable Long id, @RequestParam Long leaguerId){
		Response<String> response = new Response<String>();
		NovelChapterBean novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, id);
		if (novelChapterBean==null) {
			response.setMessage("小说章节不存在或已删除");
			return response;
		}
		NovelChapterUserBean novelChapterUserBean;
		List<NovelChapterUserBean> novelChapterUserBeanList = NovelChapterUserBean.findAllByParams(NovelChapterUserBean.class, "userId", leaguerId, "novelId", novelChapterBean.getNovelId());
		if(ListTools.isEmptyOrNull(novelChapterUserBeanList)){
			novelChapterUserBean = new NovelChapterUserBean();
			novelChapterUserBean.setNovelId(novelChapterBean.getNovelId());
			novelChapterUserBean.setUserId(leaguerId);
		}else{
			novelChapterUserBean = novelChapterUserBeanList.get(0);
			
		}
		novelChapterUserBean.setChapterId(id);
		try {
			novelService.saveNovelChapterUserBean(novelChapterUserBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 兑换小说
	 * @param id
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> exchange(@PathVariable Long id, @RequestParam Long leaguerId){
		Response<String> response = new Response<String>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		ExchangeBean exchangeBean = new ExchangeBean();
		exchangeBean.setChannelCode("novel");
		exchangeBean.setCreateTime(DateTools.now());
		exchangeBean.setIntegration(novelBean.getIntegration());
		exchangeBean.setItemId(novelBean.getId());
		exchangeBean.setItemName(novelBean.getName());
		exchangeBean.setLeaguerId(leaguerId);
		try {
			exchangeService.saveExchange(exchangeBean);
			response.setSuccess(Boolean.TRUE);
			if(NovelLoadingStatusEnum.UNLOAD.equals(NovelLoadingStatusEnum.getNovelLoadingStatusEnumByCode(novelBean.getDownloading()))){
				try {
					novelService.downloadNovel(novelBean.getId(), Boolean.FALSE);
					LogContextUtil.setOperContent("已发送下载请求");
				} catch (LogicException e) {
					LogContextUtil.setOperContent(e.getErrContent());
				} catch (Exception e) {
					LogContextUtil.setOperContent("下载小说时发生系统内部错误");
				}
			}
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 获取兑换小说
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/page", method = RequestMethod.GET)
	public Page<NovelBean> queryExchangeNovelPage(@ModelAttribute NovelExchangeQueryForm form){
		return novelService.queryExchangeNovelPage(form);
	}
}
