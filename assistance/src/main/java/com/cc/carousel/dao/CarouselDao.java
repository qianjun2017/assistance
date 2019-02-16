/**
 * 
 */
package com.cc.carousel.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cc.carousel.bean.CarouselBean;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.common.orm.dao.CrudDao;

/**
 * @author Administrator
 *
 */
@Mapper
public interface CarouselDao extends CrudDao {

	/**
	 * 查询轮播图列表
	 * @param form
	 * @return
	 */
	List<CarouselBean> queryCarouselList(CarouselQueryForm form);
}
