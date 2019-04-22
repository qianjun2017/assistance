package com.cc.system.log.dao;

import com.cc.common.orm.dao.CrudDao;
import com.cc.system.log.form.SearchForm;
import com.cc.system.log.result.SearchResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/8/16.
 */
@Mapper
public interface LogDao extends CrudDao {

    /**
     * 查询影片搜索关键词列表
     * @param searchForm
     * @return
     */
    List<SearchResult> querySearchKeywordsList(SearchForm searchForm);
}
