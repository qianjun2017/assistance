/**
 * 
 */
package com.cc.novel.service;

import com.cc.common.web.Page;
import com.cc.novel.bean.NovelBean;
import com.cc.novel.bean.NovelChapterBean;
import com.cc.novel.bean.NovelChapterContentBean;
import com.cc.novel.bean.NovelChapterUserBean;
import com.cc.novel.enums.NovelStatusEnum;
import com.cc.novel.form.NovelChapterQueryForm;
import com.cc.novel.form.NovelExchangeQueryForm;
import com.cc.novel.form.NovelQueryForm;
import com.cc.novel.http.response.data.NovelChapterData;
import com.cc.novel.http.response.data.NovelData;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface NovelService {

	/**
	 * 保存小说数据
	 * @param novelData
	 */
	void saveNovelData(NovelData novelData);

	/**
	 * 保存小说章节数据
	 * @param novelChapterData
	 */
	void saveNovelChapterData(NovelChapterData novelChapterData);
	
	/**
	 * 分页查询小说列表
	 * @param form
	 * @return
	 */
	Page<NovelBean> queryNovelBeanPage(NovelQueryForm form);

	/**
	 * 修改小说状态
	 * @param id
	 * @param status
	 */
	void changeNovelStatus(Long id, NovelStatusEnum status);

	/**
	 * 分页查询小说章节列表
	 * @param form
	 * @return
	 */
	Page<NovelChapterBean> queryNovelChapterBeanPage(NovelChapterQueryForm form);

	/**
	 * 编辑小说章节
	 * @param novelChapterBean
	 * @param novelChapterContentBean
	 */
	void updateNovelChapter(NovelChapterBean novelChapterBean, NovelChapterContentBean novelChapterContentBean);

	/**
	 * 删除小说章节
	 * @param id
	 */
	void deleteNovelChapter(Long id);

	/**
	 * 下载小说
	 * @param id
	 * @param now
	 */
	void downloadNovel(Long id, Boolean now);

	/**
	 * 停止下载小说
	 * @param id
	 */
	void stopDownloadNovel(Long id);

	/**
	 * 查询章节内容
	 * @param chapterId
	 * @return
	 */
	NovelChapterContentBean queryNovelChapterContent(Long chapterId);

	/**
	 * 编排章节顺序
	 * @param chapterId 起始章节
	 */
	void orderNovelChapter(Long chapterId);

	/**
	 * 查询小说可能的起始章节列表
	 * @param novelId
	 * @return
	 */
	List<NovelChapterBean> queryNovelOriginChapterList(Long novelId);

	/**
	 * 查询小说已编排的最大序号章节
	 * @param novelId
	 * @return
	 */
	NovelChapterBean queryNovelMaxOrderedChapter(Long novelId);

	/**
	 * 分页查询小说兑换记录
	 * @param form
	 * @return
	 */
	Page<NovelBean> queryExchangeNovelPage(NovelExchangeQueryForm form);

	/**
	 * 查询小说已编排的最小序号章节
	 * @param novelId
	 * @return
	 */
    NovelChapterBean queryNovelMinOrderedChapter(Long novelId);

	/**
	 * 保存阅读历史
	 * @param novelChapterUserBean
	 */
	void saveNovelChapterUserBean(NovelChapterUserBean novelChapterUserBean);

	/**
	 * 烂尾小说
	 * @param id
	 */
	void abandonNovel(Long id);
	
	/**
	 * 重新下载小说
	 * @param id
	 */
	void reloadNovel(Long id);

	/**
	 * 删除小说
	 * @param id
	 */
    void deleteNovel(Long id);
}
