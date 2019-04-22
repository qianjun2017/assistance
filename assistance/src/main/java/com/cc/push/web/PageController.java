package com.cc.push.web;

import java.util.List;
import java.util.Map;

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
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.push.bean.PageBean;
import com.cc.push.form.PageQueryForm;
import com.cc.push.service.PageService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

@Controller
@RequestMapping("/page")
public class PageController {

	@Autowired
	private PageService pageService;

	/**
	 * 分页查询页面信息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "page" })
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<PageBean> queryPagePage(@ModelAttribute PageQueryForm form){
		return pageService.queryPagePage(form);
	}
	
	/**
	 * 查询页面详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "page.update" })
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<PageBean> queryPage(@PathVariable Long id){
		Response<PageBean> response = new Response<PageBean>();
		PageBean pageBean = PageBean.get(PageBean.class, id);
		if(pageBean==null){
			response.setMessage("页面不存在或已删除");
			return response;
		}
		response.setData(pageBean);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 删除页面
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "page.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.PAGEMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除页面", paramNames = {"id"})
	public Response<Object> deletePage(@PathVariable Long id){
		Response<Object> response = new Response<Object>();
		PageBean pageBean = PageBean.get(PageBean.class, id);
		if(pageBean==null){
			response.setMessage("页面不存在或已删除");
			return response;
		}
		try {
			pageService.deletePage(id);
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
	 * 新增页面
	 * @param pageMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "page.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.PAGEMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增页面")
	public Response<Object> addPage(@RequestBody Map<String, Object> pageMap){
		Response<Object> response = new Response<Object>();
		PageBean pageBean = JsonTools.toObject(JsonTools.toJsonString(pageMap), PageBean.class);
		if(StringTools.isNullOrNone(pageBean.getName())){
			response.setMessage("请输入页面名称");
			return response;
		}
		if(StringTools.isNullOrNone(pageBean.getCode())){
			response.setMessage("请输入页面代码");
			return response;
		}
		List<PageBean> pageBeanList = PageBean.findAllByParams(PageBean.class, "code", pageBean.getCode());
		if(!ListTools.isEmptyOrNull(pageBeanList)){
			response.setMessage("页面代码已存在，请重新输入");
			return response;
		}
		if(StringTools.isNullOrNone(pageBean.getPage())){
			response.setMessage("请输入页面路径");
			return response;
		}
		try {
			pageService.savePage(pageBean);
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
	 * 修改页面
	 * @param pageMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "page.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.PAGEMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改页面")
	public Response<Object> updatePage(@RequestBody Map<String, Object> pageMap){
		Response<Object> response = new Response<Object>();
		Object id = pageMap.get("id");
		if(id==null){
			response.setMessage("缺少页面主键");
			return response;
		}
		PageBean oldPageBean = PageBean.get(PageBean.class, Long.valueOf(StringTools.toString(id)));
		if(oldPageBean==null){
			response.setMessage("页面不存在或已删除");
			return response;
		}
		PageBean pageBean = JsonTools.toObject(JsonTools.toJsonString(pageMap), PageBean.class);
		if(StringTools.isNullOrNone(pageBean.getName())){
			response.setMessage("请输入页面名称");
			return response;
		}
		oldPageBean.setName(pageBean.getName());
		if(StringTools.isNullOrNone(pageBean.getCode())){
			response.setMessage("请输入页面编码");
			return response;
		}
		List<PageBean> pageBeanList = PageBean.findAllByParams(PageBean.class, "code", pageBean.getCode());
		if(!ListTools.isEmptyOrNull(pageBeanList)){
			for(PageBean codePageBean: pageBeanList){
				if(codePageBean.getCode().equals(pageBean.getCode()) && !codePageBean.getId().equals(pageBean.getId())){
					response.setMessage("页面代码已存在，请重新输入");
					return response;
				}
			}
		}
		oldPageBean.setCode(pageBean.getCode());
		if(StringTools.isNullOrNone(pageBean.getPage())){
			response.setMessage("请输入页面路径");
			return response;
		}
		oldPageBean.setPage(pageBean.getPage());
		try {
			pageService.savePage(oldPageBean);
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
	 * 查询全部页面
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Response<Object> queryAllPages(){
		Response<Object> response = new Response<Object>();
		List<PageBean> pageBeanList = PageBean.findAllByParams(PageBean.class);
		if(ListTools.isEmptyOrNull(pageBeanList)){
			response.setMessage("没有查询到任何页面");
			return response;
		}
		response.setData(pageBeanList);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
