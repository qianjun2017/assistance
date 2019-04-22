/**
 * 
 */
package com.cc.push.web;

import java.util.ArrayList;
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
import com.cc.push.bean.TemplateBean;
import com.cc.push.bean.TemplateKeywordBean;
import com.cc.push.bean.TemplateLibraryBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateKeywordResult;
import com.cc.push.result.TemplateLibraryResult;
import com.cc.push.result.TemplateResult;
import com.cc.push.service.TemplateService;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/template")
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	/**
	 * 分页获取帐号下已存在的模板列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<TemplateBean> queryTemplatePage(@ModelAttribute TemplateQueryFrom form){
		Page<TemplateBean> page = templateService.queryTemplatePage(form);
		return page;
	}
	
	/**
	 * 分页获取小程序模板库标题列表
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/library/page", method = RequestMethod.GET)
	public Page<TemplateLibraryBean> queryTemplateLibraryPage(@ModelAttribute TemplateLibraryQueryFrom form){
		Page<TemplateLibraryBean> page = templateService.queryTemplateLibraryPage(form);
		return page;
	}
	
	/**
	 * 同步帐号下已存在的模板列表
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.sync" })
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.TEMPLATEMANAGEMENT, operType = OperTypeEnum.SYNC, title = "同步个人模板")
	public Response<String> syncTemplate(){
		Response<String> response = new Response<String>();
		templateService.syncTemplate();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 同步小程序模板库标题列表
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.library.sync" })
	@RequestMapping(value = "/library/sync", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.TEMPLATEMANAGEMENT, operType = OperTypeEnum.SYNC, title = "同步模板库")
	public Response<String> syncTemplateLibrary(){
		Response<String> response = new Response<String>();
		templateService.syncTemplateLibrary();
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 获取帐号下已存在的模板详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.detail" })
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<TemplateResult> queryTemplate(@PathVariable Long id){
		Response<TemplateResult> response = new Response<TemplateResult>();
		TemplateBean templateBean = TemplateBean.get(TemplateBean.class, id);
		if(templateBean==null){
			response.setMessage("消息模板不存在或已删除");
			return response;
		}
		TemplateResult templateResult = JsonTools.covertObject(templateBean, TemplateResult.class);
		List<TemplateKeywordBean> templateKeywordBeanList = TemplateKeywordBean.findAllByParams(TemplateKeywordBean.class, "templateId", templateBean.getId());
		if(!ListTools.isEmptyOrNull(templateKeywordBeanList)){
			List<TemplateKeywordResult> keywordList = new ArrayList<TemplateKeywordResult>();
			for(TemplateKeywordBean templateKeywordBean: templateKeywordBeanList){
				keywordList.add(JsonTools.covertObject(templateKeywordBean, TemplateKeywordResult.class));
			}
			templateResult.setKeywordList(keywordList);
		}
		response.setData(templateResult);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 获取模板库模板详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.add" })
	@RequestMapping(value = "/library/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<TemplateLibraryResult> queryTemplateLibrary(@PathVariable Long id){
		Response<TemplateLibraryResult> response = new Response<TemplateLibraryResult>();
		try {
			TemplateLibraryResult templateLibraryResult = templateService.queryTemplateLibrary(id);
			response.setData(templateLibraryResult);
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
	 * 新增个人模板
	 * @param templateMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.TEMPLATEMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增个人模板")
	public Response<String> addTemplate(@RequestBody Map<String, Object> templateMap){
		Response<String> response = new Response<String>();
		Object id = templateMap.get("id");
		if (id==null) {
			response.setMessage("请选择模板");
			return response;
		}
		Object object = templateMap.get("keywordList");
		if (object==null) {
			response.setMessage("请选择模板关键字");
			return response;
		}
		List<Integer> keywordList = (List<Integer>) object;
		if (ListTools.isEmptyOrNull(keywordList)) {
			response.setMessage("请选择模板关键字");
			return response;
		}
		List<Long> keywordIdList = new ArrayList<Long>();
		for (Integer keyword : keywordList) {
			keywordIdList.add(Long.valueOf(StringTools.toString(keyword)));
		}
		if(keywordIdList.size()>10){
			response.setMessage("最多选择10个关键字");
			return response;
		}
		try {
			templateService.addTemplate(StringTools.toString(id), keywordIdList);
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
	 * 删除帐号下已存在的模板详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "template.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.TEMPLATEMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除个人模板")
	public Response<String> deleteTemplate(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			templateService.deleteTemplate(id);
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
