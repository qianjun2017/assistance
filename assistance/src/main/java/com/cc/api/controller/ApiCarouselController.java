/**
 * 
 */
package com.cc.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.carousel.bean.CarouselBean;
import com.cc.carousel.bean.CarouselPlotBean;
import com.cc.carousel.enums.CarouselStatusEnum;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.carousel.service.CarouselService;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/api/carousel")
public class ApiCarouselController {

	@Autowired
	private CarouselService carouselService;
	
	/**
	 * 分页查询轮播图
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryCarouselPage(@ModelAttribute CarouselQueryForm form){
		form.setStatus(CarouselStatusEnum.ON.getCode());
		return carouselService.queryCarouselPage(form);
	}
	
	/**
	 * 查询轮播图详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/plot/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<String> queryCarouselPlot(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if (carouselBean==null) {
			response.setMessage("轮播图不存在");
			return response;
		}
		List<CarouselPlotBean> carouselPlotBeanList = CarouselPlotBean.findAllByParams(CarouselPlotBean.class, "carouselId", carouselBean.getId());
		if(ListTools.isEmptyOrNull(carouselPlotBeanList)){
			response.setMessage("轮播图详情不存在");
			return response;
		}
		CarouselPlotBean carouselPlotBean = carouselPlotBeanList.get(0);
		if(carouselPlotBean.getPlot()==null){
			response.setMessage("轮播图详情不存在");
			return response;
		}
		response.setData(new String(carouselPlotBean.getPlot()));
		response.setSuccess(Boolean.TRUE);
		return response;
	}
}
