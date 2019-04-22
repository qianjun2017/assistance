/**
 * 
 */
package com.cc.novel.dao;

import java.util.List;

import com.cc.novel.bean.NovelChapterContentBean;
import com.cc.novel.form.NovelExchangeQueryForm;
import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.novel.bean.NovelBean;
import com.cc.novel.bean.NovelChapterBean;
import com.cc.novel.form.NovelChapterQueryForm;
import com.cc.novel.form.NovelQueryForm;

/**
 * @author Administrator
 *
 */
@Mapper
public interface NovelDao extends CrudDao {

	/**
	 * 查询小说列表
	 * @param form
	 * @return
	 */
	List<NovelBean> queryNovelList(NovelQueryForm form);

	/**
	 * 查询小说章节列表
	 * @param form
	 * @return
	 */
	List<NovelChapterBean> queryNovelChapterList(NovelChapterQueryForm form);

	/**
	 * 查询章节内容
	 * @param chapterId
	 * @return
	 */
	NovelChapterContentBean queryNovelChapterContent(Long chapterId);

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
	 * 查询小说兑换记录
	 * @param form
	 * @return
	 */
    List<NovelBean> queryExchangeNovelList(NovelExchangeQueryForm form);

	/**
	 * 查询小说已编排的最小序号章节
	 * @param novelId
	 * @return
	 */
    NovelChapterBean queryNovelMinOrderedChapter(Long novelId);
}
