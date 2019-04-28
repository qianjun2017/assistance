/**
 * 
 */
package com.cc.novel.web;

import java.util.List;
import java.util.Map;

import com.cc.novel.bean.*;
import com.cc.novel.enums.SpiderTypeEnum;
import com.cc.novel.result.NovelChapterResult;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.novel.enums.NovelLoadingStatusEnum;
import com.cc.novel.enums.NovelStatusEnum;
import com.cc.novel.form.NovelChapterQueryForm;
import com.cc.novel.form.NovelQueryForm;
import com.cc.novel.result.NovelResult;
import com.cc.novel.service.NovelService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/novel")
public class NovelController {

	@Autowired
	private NovelService novelService;

	/**
	 * 查询爬虫类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/spider/type", method = RequestMethod.GET)
	public Response<Map<String, String>> querySpiderType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> spiderTypeMap = SpiderTypeEnum.getSpiderTypeMap();
		if (spiderTypeMap==null || spiderTypeMap.isEmpty()) {
			response.setMessage("暂无爬虫类型数据");
			return response;
		}
		response.setData(spiderTypeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 分页查询小说
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<NovelBean> queryNovelBeanPage(@ModelAttribute NovelQueryForm form){
		return novelService.queryNovelBeanPage(form);
	}
	
	/**
	 * 查询小说
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.detail", "leaguer.novel"}, logical = Logical.OR)
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
	 * 上架
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.up" })
	@RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.UP, title = "上架小说", paramNames = {"id"})
	public Response<String> upNovel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		try {
			LogContextUtil.setOperContent("上架小说["+novelBean.getName()+"]");
			novelService.changeNovelStatus(id, NovelStatusEnum.ON);
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
	@RequiresPermissions(value = { "novel.down" })
	@RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架小说", paramNames = {"id"})
	public Response<String> downFilm(@PathVariable Long id){
		Response<String> response = new Response<String>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		try {
			LogContextUtil.setOperContent("下架小说["+novelBean.getName()+"]");
			NovelLoadingStatusEnum novelLoadingStatusEnum = NovelLoadingStatusEnum.getNovelLoadingStatusEnumByCode(novelBean.getDownloading());
			if(NovelLoadingStatusEnum.LOADING.equals(novelLoadingStatusEnum)) {
				response.setMessage("下架前请先停止下载任务");
				return response;
			}
			novelService.changeNovelStatus(id, NovelStatusEnum.DOWN);
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
	@RequiresPermissions(value = { "novel.chapter.detail", "novel.chapter.edit", "leaguer.novel"}, logical = Logical.OR)
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
	 * 修改小说章节
	 * @param chapterMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.chapter.edit" })
	@RequestMapping(value = "/chapter/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "编辑小说章节", excludeParamNames = { "content" })
	public Response<String> updateNovelChapter(@RequestBody Map<String, String> chapterMap){
		Response<String> response = new Response<String>();
		String id = chapterMap.get("id");
		if(StringTools.isNullOrNone(id)){
			response.setMessage("章节主键不存在");
			return response;
		}
		NovelChapterBean novelChapterBean = NovelChapterBean.get(NovelChapterBean.class, Long.valueOf(id));
		if (novelChapterBean==null){
			response.setMessage("章节不存在或已删除");
			return response;
		}
		String preId = chapterMap.get("preId");
		if (StringTools.isNullOrNone(preId)){
			novelChapterBean.setPreId(null);
		}else {
			novelChapterBean.setPreId(Long.valueOf(preId));
		}
		String nextId = chapterMap.get("nextId");
		if (StringTools.isNullOrNone(nextId)){
			novelChapterBean.setNextId(null);
		}else {
			novelChapterBean.setNextId(Long.valueOf(nextId));
		}
		String name = chapterMap.get("name");
		if (StringTools.isNullOrNone(name)){
			response.setMessage("章节名称不能为空");
			return response;
		}
		novelChapterBean.setName(name);
		String content = chapterMap.get("content");
		NovelChapterContentBean novelChapterContentBean = new NovelChapterContentBean();
		if (!StringTools.isNullOrNone(content)){
			novelChapterContentBean.setContent(content.getBytes());
		}
		try {
			novelService.updateNovelChapter(novelChapterBean, novelChapterContentBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e){
			response.setMessage(e.getErrContent());
		} catch (Exception e){
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 删除小说章节
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.chapter.delete" })
	@RequestMapping(value = "/chapter/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除小说章节", paramNames = { "id" })
	public Response<String> updateNovelChapter(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			novelService.deleteNovelChapter(id);
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
	 * 下载小说章节
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.download" })
	@RequestMapping(value = "/download/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DOWNLOAD, title = "下载小说", paramNames = { "id" })
	public Response<Object> downloadNovelChapter(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			novelService.downloadNovel(id, Boolean.FALSE);
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
	 * 停止下载小说章节
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.download.stop" })
	@RequestMapping(value = "/download/stop/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DOWNLOAD, title = "停止下载小说", paramNames = { "id" })
	public Response<Object> stopDownloadNovelChapter(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			novelService.stopDownloadNovel(id);
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
	 * 编排小说章节顺序
	 * @param orderMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.order" })
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.ORDER, title = "编排小说章节顺序")
	public Response<Object> orderNovelChapter(@RequestBody Map<String, String> orderMap){
		Response<Object> response = new Response<Object>();
		if (StringTools.isNullOrNone(orderMap.get("novelId"))){
			response.setMessage("请选择小说");
			return response;
		}
		Long novelId = Long.valueOf(orderMap.get("novelId"));
		NovelBean novelBean = NovelBean.get(NovelBean.class, novelId);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		Long chapterId;
		if (StringTools.isNullOrNone(orderMap.get("chapterId"))){
			NovelChapterBean novelChapterBean = novelService.queryNovelMaxOrderedChapter(novelId);
			if(novelChapterBean==null) {
				List<NovelChapterBean> novelOriginChapterList = novelService.queryNovelOriginChapterList(novelId);
				if (ListTools.isEmptyOrNull(novelOriginChapterList)) {
					response.setMessage("没有发现可能的起始章节");
					return response;
				}
				if (novelOriginChapterList.size() > 0) {
					response.setMessage("请选择起始章节");
					response.setData(novelOriginChapterList);
					return response;
				}
			}
			chapterId = novelChapterBean.getId();
		}else {
			chapterId = Long.valueOf(orderMap.get("chapterId"));
		}
		try {
			novelService.orderNovelChapter(chapterId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e){
			response.setMessage(e.getErrContent());
		} catch (Exception e){
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 立即下载小说章节
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.download.now" })
	@RequestMapping(value = "/download/now/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DOWNLOADNOW, title = "立即下载小说", paramNames = { "id" })
	public Response<Object> downloadNovelChapterNow(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			novelService.downloadNovel(id, Boolean.TRUE);
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
	 * 标记小说为烂尾小说
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.abandon" })
	@RequestMapping(value = "/abandon/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.ABANDON, title = "烂尾小说", paramNames = { "id" })
	public Response<Object> abandonNovel(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		NovelBean novelBean = NovelBean.get(NovelBean.class, id);
		if (novelBean==null) {
			response.setMessage("小说不存在或已删除");
			return response;
		}
		try {
			LogContextUtil.setOperContent("放弃写作小说["+novelBean.getName()+"]");
			NovelLoadingStatusEnum novelLoadingStatusEnum = NovelLoadingStatusEnum.getNovelLoadingStatusEnumByCode(novelBean.getDownloading());
			if(NovelLoadingStatusEnum.LOADING.equals(novelLoadingStatusEnum)) {
				response.setMessage("放弃前请先停止下载任务");
				return response;
			}
			novelService.abandonNovel(id);
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
	 * 重载小说
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.reload" })
	@RequestMapping(value = "/reload/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.RELOAD, title = "重新下载小说基本信息", paramNames = { "id" })
	public Response<Object> reloadNovel(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			novelService.reloadNovel(id);
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
	 * 删除小说
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "novel.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.NOVELMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除小说", paramNames = { "id" })
	public Response<Object> deleteNovel(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		try {
			novelService.deleteNovel(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
