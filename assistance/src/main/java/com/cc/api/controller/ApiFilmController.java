package com.cc.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.api.form.CommentForm;
import com.cc.common.exception.LogicException;
import com.cc.common.form.QueryForm;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.enums.ExchangeStatusEnum;
import com.cc.exchange.service.ExchangeService;
import com.cc.film.bean.FilmBean;
import com.cc.film.bean.FilmCommentBean;
import com.cc.film.bean.FilmPlotBean;
import com.cc.film.bean.FilmUrlBean;
import com.cc.film.enums.FilmStatusEnum;
import com.cc.film.form.FilmCommentQueryForm;
import com.cc.film.form.FilmQueryForm;
import com.cc.film.result.FilmCommentResult;
import com.cc.film.result.FilmResult;
import com.cc.film.service.FilmService;
import com.cc.leaguer.bean.LeaguerBean;

@Controller
@RequestMapping("/api/film")
public class ApiFilmController {

	@Autowired
	private FilmService filmService;

	@Autowired
	private ExchangeService exchangeService;
	
	/**
	 * 查询影片年份、国家枚举值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enums", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryEnums(){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("countries", filmService.queryFilmCountryList());
		map.put("years", filmService.queryFilmYearList());
		response.setData(map);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 检索影片
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<FilmBean> queryFilmBeanPage(@ModelAttribute FilmQueryForm form){
		form.setStatus(FilmStatusEnum.ON.getCode());
		return filmService.queryFilmBeanPage(form);
	}
	
	/**
	 * 分页查询最新影片
	 * @param form 只接受page和pageSize参数 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/new/page", method = RequestMethod.GET)
	public Page<FilmBean> queryNewFilmBeanPage(@ModelAttribute QueryForm form){
		Page<FilmBean> page = filmService.queryNewFilmBeanPage(form);
		return page;
	}
	
	/**
	 * 分页查询推荐影片
	 * @param form 只接受page和pageSize参数 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recommend/page", method = RequestMethod.GET)
	public Page<FilmBean> queryRecommendFilmBeanPage(@ModelAttribute QueryForm form){
		Page<FilmBean> page = filmService.queryRecommendFilmBeanPage(form);
		return page;
	}
	
	/**
	 * 分页查询热播影片
	 * @param form 只接受page和pageSize参数 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hot/page", method = RequestMethod.GET)
	public Page<FilmBean> queryHotFilmBeanPage(@ModelAttribute QueryForm form){
		Page<FilmBean> page = filmService.queryHotFilmBeanPage(form);
		return page;
	}
	
	/**
	 * 查询影片
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<FilmResult> queryFilm(@PathVariable Long id){
		Response<FilmResult> response = new Response<FilmResult>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在或已删除");
			return response;
		}
		FilmResult filmResult = new FilmResult();
		filmResult.setFilm(filmBean);
		filmResult.setActors(filmService.queryFilmActorList(filmBean.getId()));
		filmResult.setDirectors(filmService.queryFilmDirectorList(filmBean.getId()));
		List<FilmPlotBean> filmPlotBeanList = FilmPlotBean.findAllByParams(FilmPlotBean.class, "filmId", filmBean.getId());
		if (!ListTools.isEmptyOrNull(filmPlotBeanList)) {
			filmResult.setPlot(new String(filmPlotBeanList.get(0).getPlot()));
		}
		List<FilmUrlBean> filmUrlBeanList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmBean.getId());
		if (!ListTools.isEmptyOrNull(filmUrlBeanList)) {
			filmResult.setUrls(filmUrlBeanList);
			for (FilmUrlBean filmUrlBean : filmUrlBeanList) {
				if (filmUrlBean.getDefaultUrl()) {
					filmResult.setDefaultUrl(filmUrlBean.getUrl());
					break;
				}
			}
		}
		filmResult.setPlayed(filmService.queryFilmPlayed(filmBean.getId()));
		response.setData(filmResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 查询影片评论
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/comment/page", method = RequestMethod.GET)
	public Page<FilmCommentResult> queryFilmCommentPage(@ModelAttribute FilmCommentQueryForm form){
		Page<FilmCommentResult> page = filmService.queryFilmCommentPage(form);
		return page;
	}
	
	/**
	 * 评论
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/comment/add", method = RequestMethod.POST)
	public Response<String> addFileComment(@RequestBody CommentForm form){
		Response<String> response = new Response<String>();
		if(form.getFilmId()==null){
			response.setMessage("请选择影片");
			return response;
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, form.getFilmId());
		if(filmBean==null){
			response.setMessage("影片不存在或已删除");
			return response;
		}
		if (StringTools.isNullOrNone(form.getComment())) {
			response.setMessage("请输入评论");
			return response;
		}
		LeaguerBean leaguerBean = LeaguerBean.get(LeaguerBean.class, form.getLeaguerId());
		FilmCommentBean filmCommentBean = new FilmCommentBean();
		filmCommentBean.setFilmId(form.getFilmId());
		filmCommentBean.setFilmName(filmBean.getName());
		filmCommentBean.setLeaguerId(form.getLeaguerId());
		filmCommentBean.setLeaguerName(leaguerBean.getLeaguerName());
		filmCommentBean.setComment(form.getComment().getBytes());
		filmCommentBean.setCreateTime(DateTools.now());
		try {
			filmService.saveFilmComment(filmCommentBean);
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
	 * 点赞
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/thumb/up/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> gaveThumbUp(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.gaveAThumbUp(id);
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
	 * 踩一脚
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/thumb/down/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> gaveThumbDown(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.gaveAThumbDown(id);
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
	 * 兑换影片
	 * @param id
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> exchange(@PathVariable Long id, @RequestParam Long leaguerId){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在或已删除");
			return response;
		}
		ExchangeBean exchangeBean = new ExchangeBean();
		exchangeBean.setChannelCode("film");
		exchangeBean.setCreateTime(DateTools.now());
		exchangeBean.setIntegration(filmBean.getIntegration());
		exchangeBean.setItemId(filmBean.getId());
		exchangeBean.setItemName(filmBean.getName());
		exchangeBean.setLeaguerId(leaguerId);
		try {
			exchangeService.saveExchange(exchangeBean);
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
	 * 取消兑换申请
	 * @param filmId
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/cancel/{filmId:\\d+}", method = RequestMethod.POST)
	public Response<Object> requestCancelExchange(@PathVariable Long filmId, @RequestParam Long leaguerId){
		Response<Object> response = new Response<Object>();
		List<ExchangeBean> exchangeBeanList = ExchangeBean.findAllByParams(ExchangeBean.class, "itemId", filmId, "leaguerId", leaguerId, "channelCode", "film", "sort", "createTime", "order", "desc");
		if (ListTools.isEmptyOrNull(exchangeBeanList)) {
			response.setMessage("您还未兑换影片");
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
}
