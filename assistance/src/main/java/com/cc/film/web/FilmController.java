/**
 * 
 */
package com.cc.film.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.exchange.bean.ExchangeBean;
import com.cc.exchange.enums.ExchangeStatusEnum;
import com.cc.exchange.service.ExchangeService;
import com.cc.film.bean.FilmAttributeBean;
import com.cc.film.bean.FilmBean;
import com.cc.film.bean.FilmCommentBean;
import com.cc.film.bean.FilmEvaluateBean;
import com.cc.film.bean.FilmPlotBean;
import com.cc.film.bean.FilmStillBean;
import com.cc.film.bean.FilmUrlBean;
import com.cc.film.enums.FilmStatusEnum;
import com.cc.film.form.FilmCommentQueryForm;
import com.cc.film.form.FilmExchangeQueryForm;
import com.cc.film.form.FilmQueryForm;
import com.cc.film.result.FilmCommentResult;
import com.cc.film.result.FilmResult;
import com.cc.film.service.FilmService;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/film")
public class FilmController {

	@Autowired
	private FilmService filmService;
	
	@Autowired
	private ExchangeService exchangeService;
	
	/**
	 * 查询影片年份、语言、国家枚举值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/enums", method = RequestMethod.GET)
	public Response<Map<String, Object>> queryEnums(){
		Response<Map<String, Object>> response = new Response<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("countries", filmService.queryFilmCountryList());
		map.put("years", filmService.queryFilmYearList());
		map.put("languages", filmService.queryFilmLanguageList());
		response.setData(map);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询影片
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.SEARCH, title = "电影检索")
	public Page<FilmBean> queryFilmBeanPage(@ModelAttribute FilmQueryForm form){
		if(!StringTools.isNullOrNone(form.getKeywords())){
			LogContextUtil.setOperContent(form.getKeywords());
		}else{
			LogContextUtil.setRecordLog(Boolean.FALSE);
		}
		Page<FilmBean> page = filmService.queryFilmBeanPage(form);
		return page;
	}
	
	/**
	 * 分页查询最新影片
	 * @param form 只接受page和pageSize参数 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/new/page", method = RequestMethod.GET)
	public Page<FilmBean> queryNewFilmBeanPage(@ModelAttribute FilmQueryForm form){
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
	public Page<FilmBean> queryRecommendFilmBeanPage(@ModelAttribute FilmQueryForm form){
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
	public Page<FilmBean> queryHotFilmBeanPage(@ModelAttribute FilmQueryForm form){
		Page<FilmBean> page = filmService.queryHotFilmBeanPage(form);
		return page;
	}
	
	/**
	 * 查询影片
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.detail", "leaguer.film"}, logical = Logical.OR)
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<FilmResult> queryFilm(@PathVariable Long id){
		Response<FilmResult> response = new Response<FilmResult>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在");
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
	 * 上架
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.up" })
	@RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.UP, title = "上架影片", paramNames = {"id"})
	public Response<String> upFilm(@PathVariable Long id){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("上架影片["+filmBean.getName()+"]");
			filmService.changeFilmStatus(id, FilmStatusEnum.ON);
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
	 * 下架
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.down" })
	@RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架影片", paramNames = {"id"})
	public Response<String> downFilm(@PathVariable Long id){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("下架影片["+filmBean.getName()+"]");
			filmService.changeFilmStatus(id, FilmStatusEnum.DOWN);
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
	 * 查询影片的播放地址
	 * @param filmId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{filmId}/urls", method = RequestMethod.GET)
	public Response<Object> queryFilmUrlList(@PathVariable Long filmId){
		Response<Object> response = new Response<Object>();
		List<FilmUrlBean> filmUrlBeanList = FilmUrlBean.findAllByParams(FilmUrlBean.class, "filmId", filmId);
		if (ListTools.isEmptyOrNull(filmUrlBeanList)) {
			response.setMessage("无可用播放地址");
			return response;
		}
		response.setData(filmUrlBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 删除影片播放地址
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.url.delete" })
	@RequestMapping(value = "/url/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除播放地址", paramNames = {"id"})
	public Response<String> deleteFilmUrl(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.deleteFilmUrl(id);
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
	 * 设置影片默认播放地址
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.url.default" })
	@RequestMapping(value = "/url/default/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "设置默认播放地址", paramNames = {"id"})
	public Response<String> defaultFilmUrl(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.defaultFilmUrl(id);
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
	 * @param commentMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.film" })
	@RequestMapping(value="/comment/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.COMMENT, title = "新增评论")
	public Response<String> addFileComment(@RequestBody Map<String, String> commentMap){
		Response<String> response = new Response<String>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
		if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
			response.setMessage("评论失败");
			return response;
		}
		String filmId = commentMap.get("filmId");
		if (StringTools.isNullOrNone(filmId)) {
			response.setMessage("评论失败");
			return response;
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, Long.valueOf(filmId));
		if(filmBean==null){
			response.setMessage("评论失败");
			return response;
		}
		String comment = commentMap.get("comment");
		if (StringTools.isNullOrNone(comment)) {
			response.setMessage("请输入评论");
			return response;
		}
		FilmCommentBean filmCommentBean = new FilmCommentBean();
		filmCommentBean.setFilmId(Long.valueOf(filmId));
		filmCommentBean.setFilmName(filmBean.getName());
		filmCommentBean.setLeaguerId(leaguerBeanList.get(0).getId());
		filmCommentBean.setLeaguerName(leaguerBeanList.get(0).getLeaguerName());
		filmCommentBean.setComment(comment.getBytes());
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
	 * 删除评论
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.comment.delete" })
	@RequestMapping(value = "/comment/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.COMMENT, title = "删除评论", paramNames = {"id"})
	public Response<String> deleteFilmComment(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.deleteFilmComment(id);
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
	@RequiresPermissions(value = { "leaguer.film" })
	@RequestMapping(value = "/thumb/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.THUMB, title = "点赞", paramNames = {"id"})
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
	@RequiresPermissions(value = { "leaguer.film" })
	@RequestMapping(value = "/thumb/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.THUMB, title = "踩一脚", paramNames = {"id"})
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
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.film" })
	@RequestMapping(value = "/exchange/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.EXCHANGE, title = "兑换", paramNames = {"id"})
	public Response<String> exchange(@PathVariable Long id){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在");
			return response;
		}
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
		if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
			response.setMessage("兑换失败");
			return response;
		}
		ExchangeBean exchangeBean = new ExchangeBean();
		exchangeBean.setChannelCode("film");
		exchangeBean.setCreateTime(DateTools.now());
		exchangeBean.setIntegration(filmBean.getIntegration());
		exchangeBean.setItemId(filmBean.getId());
		exchangeBean.setItemName(filmBean.getName());
		exchangeBean.setLeaguerId(leaguerBeanList.get(0).getId());
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
	 * 获取兑换影片
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/page", method = RequestMethod.GET)
	public Page<FilmBean> queryExchangeFilmPage(@ModelAttribute FilmExchangeQueryForm form){
		Page<FilmBean> page = new Page<FilmBean>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			page.setMessage("请先登录");
			return page;
		}
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
				page.setMessage("查询兑换记录失败");
				return page;
			}
			form.setLeaguerId(leaguerBeanList.get(0).getId());
		}
		page = filmService.queryExchangeFilmPage(form);
		return page;
	}
	
	/**
	 * 影片评分
	 * @param evaluateMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.evaluate.edit" })
	@RequestMapping(value = "/evaluate", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.EVALUATE, title = "影片评分")
	public Response<String> evaluate(@RequestBody Map<String, Object> evaluateMap) {
		Response<String> response = new Response<String>();
		FilmEvaluateBean filmEvaluateBean = JsonTools.toObject(JsonTools.toJsonString(evaluateMap), FilmEvaluateBean.class);
		try {
			filmService.saveFilmEvaluate(filmEvaluateBean);
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
	 * 获取影片评分
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/evaluate/{id:\\d+}", method = RequestMethod.GET)
	public Response<FilmEvaluateBean> queryFilmEvaluate(@PathVariable Long id){
		Response<FilmEvaluateBean> response = new Response<FilmEvaluateBean>();
		FilmEvaluateBean filmEvaluateBean = FilmEvaluateBean.get(FilmEvaluateBean.class, id);
		if(filmEvaluateBean==null){
			response.setMessage("暂无影片评分");
			return response;
		}
		response.setData(filmEvaluateBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 播放影片
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/play/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.PLAY, title = "观看影片")
	public Response<String> playFilm(@PathVariable Long id){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, id);
		if (filmBean==null) {
			response.setMessage("影片不存在");
			return response;
		}
		com.cc.user.bean.TUserBean tUserBean = SecurityContextUtil.getCurrentUser();
		if(tUserBean==null){
			response.setMessage("未登录用户");
			return response;
		}
		LogContextUtil.setOperContent("用户["+tUserBean.getUserName()+"]正在观看影片["+filmBean.getName()+"]");
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 添加影片属性
	 * @param attributeMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attribute/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.ATTRIBUTE, title = "新增属性")
	public Response<String> addFileAttribute(@RequestBody Map<String, String> attributeMap){
		Response<String> response = new Response<String>();
		String filmId = attributeMap.get("filmId");
		if (StringTools.isNullOrNone(filmId)) {
			response.setMessage("影片不存在");
			return response;
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, Long.valueOf(filmId));
		if(filmBean==null){
			response.setMessage("影片不存在");
			return response;
		}
		String attribute = attributeMap.get("attribute");
		if (StringTools.isNullOrNone(attribute)) {
			response.setMessage("属性不能为空");
			return response;
		}
		List<FilmAttributeBean> filmAttributeBeanList = FilmAttributeBean.findAllByParams(FilmAttributeBean.class, "filmId", Long.valueOf(filmId), "attribute", attribute);
		if (!ListTools.isEmptyOrNull(filmAttributeBeanList)) {
			response.setMessage("属性已存在");
			return response;
		}
		FilmAttributeBean filmAttributeBean = new FilmAttributeBean();
		filmAttributeBean.setFilmId(Long.valueOf(filmId));
		filmAttributeBean.setAttribute(attribute);
		try {
			filmService.saveFilmAttribute(filmAttributeBean);
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
	 * 是否存在影片属性
	 * @param attribute
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attribute/has", method = RequestMethod.GET)
	public Response<String> hasFileAttribute(Long filmId, String attribute){
		Response<String> response = new Response<String>();
		FilmBean filmBean = FilmBean.get(FilmBean.class, filmId);
		if(filmBean==null){
			response.setMessage("影片不存在");
			return response;
		}
		if (StringTools.isNullOrNone(attribute)) {
			response.setMessage("属性不能为空");
			return response;
		}
		List<FilmAttributeBean> filmAttributeBeanList = FilmAttributeBean.findAllByParams(FilmAttributeBean.class, "filmId", Long.valueOf(filmId), "attribute", attribute);
		if (!ListTools.isEmptyOrNull(filmAttributeBeanList)) {
			response.setSuccess(Boolean.TRUE);
			return response;
		}
		return response;
	}
	
	/**
	 * 删除影片属性
	 * @param attributeMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attribute/delete", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.ATTRIBUTE, title = "删除属性")
	public Response<String> deleteFileAttribute(@RequestBody Map<String, String> attributeMap){
		Response<String> response = new Response<String>();
		String filmId = attributeMap.get("filmId");
		if (StringTools.isNullOrNone(filmId)) {
			response.setMessage("影片不存在");
			return response;
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, Long.valueOf(filmId));
		if(filmBean==null){
			response.setMessage("影片不存在");
			return response;
		}
		String attribute = attributeMap.get("attribute");
		if (StringTools.isNullOrNone(attribute)) {
			response.setMessage("属性不能为空");
			return response;
		}
		try {
			filmService.deleteFilmAttribute(Long.valueOf(filmId), attribute);
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
	 * 查询影片属性
	 * @param filmId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/attribute/{filmId:\\d+}", method = RequestMethod.GET)
	public Response<Object> queryFileAttribute(@PathVariable Long filmId){
		Response<Object> response = new Response<Object>();
		List<FilmAttributeBean> filmAttributeBeanList = FilmAttributeBean.findAllByParams(FilmAttributeBean.class, "filmId", filmId);
		if (ListTools.isEmptyOrNull(filmAttributeBeanList)) {
			response.setMessage("属性为空");
			return response;
		}
		response.setData(filmAttributeBeanList.stream().map(filmAttributeBean->filmAttributeBean.getAttribute()).collect(Collectors.toList()));
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 新增影片剧照
	 * @param filmStillMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.still.add" })
	@RequestMapping(value = "/still/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.STILL, title = "上传剧照")
	public Response<String> addFilmStill(@RequestBody Map<String, String> filmStillMap){
		Response<String> response = new Response<String>();
		String filmId = filmStillMap.get("filmId");
		if (StringTools.isNullOrNone(filmId)) {
			response.setMessage("影片不存在");
			return response;
		}
		FilmBean filmBean = FilmBean.get(FilmBean.class, Long.valueOf(filmId));
		if(filmBean==null){
			response.setMessage("影片不存在");
			return response;
		}
		String stillUrl = filmStillMap.get("stillUrl");
		if (StringTools.isNullOrNone(stillUrl)) {
			response.setMessage("剧照不能为空");
			return response;
		}
		try {
			FilmStillBean filmStillBean = new FilmStillBean();
			filmStillBean.setFilmId(Long.valueOf(filmId));
			filmStillBean.setStillUrl(stillUrl);
			filmService.saveFilmStill(filmStillBean);
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
	 * 删除剧照
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "film.still.delete" })
	@RequestMapping(value = "/still/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.FILMMANAGEMENT, operType = OperTypeEnum.STILL, title = "删除剧照", paramNames = {"id"})
	public Response<String> deleteFilmStill(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			filmService.deleteFilmStill(id);
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
	 * 查询影片剧照
	 * @param filmId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/still/{filmId:\\d+}", method = RequestMethod.GET)
	public Response<Object> queryFileStill(@PathVariable Long filmId){
		Response<Object> response = new Response<Object>();
		List<FilmStillBean> filmStillBeanList = FilmStillBean.findAllByParams(FilmStillBean.class, "filmId", filmId);
		if (ListTools.isEmptyOrNull(filmStillBeanList)) {
			response.setMessage("剧照为空");
			return response;
		}
		response.setData(filmStillBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 取消兑换申请
	 * @param filmId
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "leaguer.film" })
	@RequestMapping(value = "/exchange/cancel/{filmId:\\d+}", method = RequestMethod.POST)
	public Response<Object> requestCancelExchange(@PathVariable Long filmId){
		Response<Object> response = new Response<Object>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
		if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size()>1) {
			response.setMessage("取消兑换申请失败");
			return response;
		}
		List<ExchangeBean> exchangeBeanList = ExchangeBean.findAllByParams(ExchangeBean.class, "itemId", filmId, "leaguerId", leaguerBeanList.get(0).getId(), "channelCode", "film", "sort", "createTime", "order", "desc");
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

