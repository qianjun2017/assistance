/**
 * 
 */
package com.cc.film.service;

import java.util.List;

import com.cc.common.web.Page;
import com.cc.film.bean.ActorBean;
import com.cc.film.bean.DirectorBean;
import com.cc.film.bean.FilmAttributeBean;
import com.cc.film.bean.FilmBean;
import com.cc.film.bean.FilmCommentBean;
import com.cc.film.bean.FilmEvaluateBean;
import com.cc.film.bean.FilmStillBean;
import com.cc.film.enums.FilmStatusEnum;
import com.cc.film.form.FilmCommentQueryForm;
import com.cc.film.form.FilmExchangeQueryForm;
import com.cc.film.form.FilmQueryForm;
import com.cc.film.http.response.data.FilmData;
import com.cc.film.result.FilmCommentResult;
import com.cc.film.result.FilmCountryResult;
import com.cc.film.result.FilmLanguageResult;
import com.cc.film.result.FilmYearResult;

/**
 * 影片
 * @author Administrator
 *
 */
public interface FilmService {

	/**
	 * 保存影片数据
	 * @param filmData
	 */
	void saveFilmData(FilmData filmData);
	
	/**
	 * 分页查询影片
	 * @param form
	 * @return
	 */
	Page<FilmBean> queryFilmBeanPage(FilmQueryForm form);
	
	/**
	 * 查询影片演员
	 * @param filmId
	 * @return
	 */
	List<ActorBean> queryFilmActorList(Long filmId);
	
	/**
	 * 查询影片导演
	 * @param filmId
	 * @return
	 */
	List<DirectorBean> queryFilmDirectorList(Long filmId);
	
	/**
	 * 删除影片播放地址
	 * @param id
	 */
	void deleteFilmUrl(Long id);

	/**
	 * 设置影片默认播放地址
	 * @param id
	 */
	void defaultFilmUrl(Long id);
	
	/**
	 * 修改影片状态
	 * @param id
	 * @param status
	 */
	void changeFilmStatus(Long id, FilmStatusEnum status);
	
	/**
	 * 分页查询影片评论
	 * @param form
	 * @return
	 */
	Page<FilmCommentResult> queryFilmCommentPage(FilmCommentQueryForm form);
	
	/**
	 * 新增评论
	 * @param filmCommentBean
	 */
	void saveFilmComment(FilmCommentBean filmCommentBean);
	
	/**
	 * 删除评论
	 * @param id
	 */
	void deleteFilmComment(Long id);
	
	/**
	 * 给影片点赞
	 * @param id
	 */
	void gaveAThumbUp(Long id);
	
	/**
	 * 踩一脚
	 * @param id
	 */
	void gaveAThumbDown(Long id);

	/**
	 * 分页查询影片兑换记录
	 * @param form
	 * @return
	 */
	Page<FilmBean> queryExchangeFilmPage(FilmExchangeQueryForm form);
	
	/**
	 * 保存影片评分
	 * @param filmEvaluateBean
	 */
	void saveFilmEvaluate(FilmEvaluateBean filmEvaluateBean);
	
	/**
	 * 保存影片属性
	 * @param filmAttributeBean
	 */
	void saveFilmAttribute(FilmAttributeBean filmAttributeBean);
	
	/**
	 * 删除影片属性
	 * @param filmId
	 * @param attribute
	 */
	void deleteFilmAttribute(Long filmId, String attribute);
	
	/**
	 * 保存剧照
	 * @param filmStillBean
	 */
	void saveFilmStill(FilmStillBean filmStillBean);
	
	/**
	 * 删除剧照
	 * @param id
	 */
	void deleteFilmStill(Long id);
	
	/**
	 * 分页查询最新影片
	 * @param form
	 * @return
	 */
	Page<FilmBean> queryNewFilmBeanPage(FilmQueryForm form);
	
	/**
	 * 分页查询推荐影片
	 * @param form
	 * @return
	 */
	Page<FilmBean> queryRecommendFilmBeanPage(FilmQueryForm form);
	
	/**
	 * 分页查询热播影片
	 * @param form
	 * @return
	 */
	Page<FilmBean> queryHotFilmBeanPage(FilmQueryForm form);
	
	/**
	 * 查询影片播放次数
	 */
	Integer queryFilmPlayed(Long id);
	
	/**
	 * 查询电影年份
	 * @return
	 */
	List<FilmYearResult> queryFilmYearList();
	
	/**
	 * 查询电影语言
	 * @return
	 */
	List<FilmLanguageResult> queryFilmLanguageList();
	
	/**
	 * 查询电影国家
	 * @return
	 */
	List<FilmCountryResult> queryFilmCountryList();
}
