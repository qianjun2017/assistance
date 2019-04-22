/**
 * 
 */
package com.cc.film.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.common.orm.dao.CrudDao;
import com.cc.film.bean.ActorBean;
import com.cc.film.bean.DirectorBean;
import com.cc.film.bean.FilmBean;
import com.cc.film.form.FilmExchangeQueryForm;
import com.cc.film.form.FilmQueryForm;
import com.cc.film.result.FilmCountryResult;
import com.cc.film.result.FilmLanguageResult;
import com.cc.film.result.FilmYearResult;

/**
 * @author Administrator
 *
 */
@Mapper
public interface FilmDao extends CrudDao {

	/**
	 * 查询影片列表
	 * @param form
	 * @return
	 */
	List<FilmBean> queryFilmList(FilmQueryForm form);
	
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
	 * 查询影片兑换记录
	 * @param form
	 * @return
	 */
	List<FilmBean> queryExchangeFilmList(FilmExchangeQueryForm form);

	/**
	 * 查询最新影片列表
	 * @param form
	 * @return
	 */
	List<FilmBean> queryNewFilmList(FilmQueryForm form);
	
	/**
	 * 查询推荐影片列表
	 * @param form
	 * @return
	 */
	List<FilmBean> queryRecommendFilmList(FilmQueryForm form);
	
	/**
	 * 查询热播影片列表
	 * @param form
	 * @return
	 */
	List<FilmBean> queryHotFilmList(FilmQueryForm form);
	
	/**
	 * 查询影片播放次数
	 * @param filmId
	 * @return
	 */
	Integer queryFilmPlayed(Long filmId);

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
