/**
 * 
 */
package com.cc.push.service;

import java.util.List;

import com.cc.common.web.Page;
import com.cc.push.bean.PushBean;
import com.cc.push.bean.TemplateBean;
import com.cc.push.bean.TemplateLibraryBean;
import com.cc.push.form.TemplateLibraryQueryFrom;
import com.cc.push.form.TemplateQueryFrom;
import com.cc.push.result.TemplateLibraryResult;

/**
 * @author ws_yu
 *
 */
public interface TemplateService {

	/**
	 * 分页查询个人模板库
	 * @param form
	 * @return
	 */
	Page<TemplateBean> queryTemplatePage(TemplateQueryFrom form);

	/**
	 * 分页查询小程序模板库标题列表
	 * @param form
	 * @return
	 */
	Page<TemplateLibraryBean> queryTemplateLibraryPage(TemplateLibraryQueryFrom form);
	
	/**
	 * 同步帐号下已存在的模板列表
	 */
	void syncTemplate();
	
	/**
	 * 同步小程序模板库标题列表
	 */
	void syncTemplateLibrary();
	
	/**
	 * 查询小程序模板详情
	 * @param id
	 * @return
	 */
	TemplateLibraryResult queryTemplateLibrary(Long id);

	/**
	 * 添加个人模板
	 * @param id
	 * @param keywordIdList
	 */
	void addTemplate(String id, List<Long> keywordIdList);

	/**
	 * 删除个人模板
	 * @param id
	 */
	void deleteTemplate(Long id);
	
	/**
	 * 推送模板消息
	 * @param pushBean
	 */
	void pushTemplateMessage(PushBean pushBean);

}
