/**
 * 
 */
package com.cc.carousel.service;

import java.util.Map;

import com.cc.carousel.enums.CarouselStatusEnum;
import com.cc.carousel.form.CarouselForm;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.common.web.Page;

/**
 * @author Administrator
 *
 */
public interface CarouselService {

	/**
	 * 保存轮播图
	 * @param carousel
	 */
	void saveCarousel(CarouselForm carousel);
	
	/**
	 * 删除轮播图
	 * @param id
	 */
	void deleteCarousel(Long id);
	
	/**
	 * 查询轮播图
	 * @param form
	 * @return
	 */
	Page<Map<String, Object>> queryCarouselPage(CarouselQueryForm form);
	
	/**
	 * 改变轮播图状态
	 * @param id
	 * @param status
	 */
	void changeCarouselStatus(Long id, CarouselStatusEnum status);
	
	/**
	 * 点击轮播图
	 * @param id
	 */
	void clickCarousel(Long id);
}
