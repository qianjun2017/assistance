package com.cc.push.service;

import com.cc.common.web.Page;
import com.cc.push.bean.PageBean;
import com.cc.push.form.PageQueryForm;

public interface PageService {

	/**
	 * 保存页面信息
	 * @param pageBean
	 */
	void savePage(PageBean pageBean);
	
	/**
	 * 删除指定页面
	 * @param id
	 */
	void deletePage(Long id);
	
	/**
	 * 分页查询页面
	 * @param form
	 * @return
	 */
	Page<PageBean> queryPagePage(PageQueryForm form);
}
