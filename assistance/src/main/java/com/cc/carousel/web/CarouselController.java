/**
 * 
 */
package com.cc.carousel.web;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.carousel.bean.CarouselBean;
import com.cc.carousel.bean.CarouselPlotBean;
import com.cc.carousel.enums.CarouselStatusEnum;
import com.cc.carousel.form.CarouselForm;
import com.cc.carousel.form.CarouselQueryForm;
import com.cc.carousel.result.CarouselResult;
import com.cc.carousel.service.CarouselService;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/carousel")
public class CarouselController {

	@Autowired
	private CarouselService carouselService;
	
	/**
	 * 增加轮播图
	 * @param carouselMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "carousel.add" })
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CAROUSELMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增轮播图")
	public Response<String> addCarousel(@RequestBody Map<String, Object> carouselMap){
		Response<String> response = new Response<String>();
		CarouselForm carousel = JsonTools.toObject(JsonTools.toJsonString(carouselMap), CarouselForm.class);
		if (StringTools.isNullOrNone(carousel.getName())) {
			response.setMessage("请输入轮播图名称");
			return response;
		}
		if (StringTools.isNullOrNone(carousel.getImageUrl())) {
			response.setMessage("请上传轮播图");
			return response;
		}
		if(StringTools.isAllNullOrNone(new String[]{carousel.getPath(), carousel.getPlot()})){
			response.setMessage("跳转路径和详情必须输入一个");
			return response;
		}
		if(!StringTools.isAnyNullOrNone(new String[]{carousel.getPath(), carousel.getPlot()})){
			response.setMessage("跳转路径和详情不能同时输入");
			return response;
		}
		try {
			carouselService.saveCarousel(carousel);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 修改轮播图
	 * @param carouselMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "carousel.update" })
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CAROUSELMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改轮播图")
	public Response<String> updateCarousel(@RequestBody Map<String, Object> carouselMap){
		Response<String> response = new Response<String>();
		CarouselForm carousel = JsonTools.toObject(JsonTools.toJsonString(carouselMap), CarouselForm.class);
		if (StringTools.isNullOrNone(carousel.getName())) {
			response.setMessage("请输入轮播图名称");
			return response;
		}
		if (StringTools.isNullOrNone(carousel.getImageUrl())) {
			response.setMessage("请上传轮播图");
			return response;
		}
		if(StringTools.isAllNullOrNone(new String[]{carousel.getPath(), carousel.getPlot()})){
			response.setMessage("跳转路径和详情必须输入一个");
			return response;
		}
		if(!StringTools.isAnyNullOrNone(new String[]{carousel.getPath(), carousel.getPlot()})){
			response.setMessage("跳转路径和详情不能同时输入");
			return response;
		}
		try {
			carouselService.saveCarousel(carousel);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 上架轮播图
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "carousel.up" })
	@RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CAROUSELMANAGEMENT, operType = OperTypeEnum.UP, title = "上架轮播图", paramNames = {"id"})
	public Response<String> upCarousel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if(carouselBean==null){
			response.setMessage("轮播图不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("上架轮播图["+carouselBean.getName()+"]");
			carouselService.changeCarouselStatus(id, CarouselStatusEnum.ON);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 下架轮播图
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "carousel.down" })
	@RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CAROUSELMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架轮播图", paramNames = {"id"})
	public Response<String> downCarousel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if(carouselBean==null){
			response.setMessage("轮播图不存在");
			return response;
		}
		try {
			LogContextUtil.setOperContent("下架轮播图["+carouselBean.getName()+"]");
			carouselService.changeCarouselStatus(id, CarouselStatusEnum.DOWN);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 删除轮播图
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "carousel.delete" })
	@RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.CAROUSELMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除轮播图", paramNames = {"id"})
	public Response<String> deleteCarousel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if(carouselBean==null){
			response.setMessage("轮播图不存在");
			return response;
		}
		if (CarouselStatusEnum.ON.getCode().equals(carouselBean.getStatus())) {
			response.setMessage("轮播图上架中，不能删除");
			return response;
		}
		try {
			LogContextUtil.setOperContent("删除轮播图["+carouselBean.getName()+"]");
			carouselService.deleteCarousel(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 查询轮播图详情
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<CarouselResult> queryCarousel(@PathVariable Long id){
		Response<CarouselResult> response = new Response<CarouselResult>();
		CarouselBean carouselBean = CarouselBean.get(CarouselBean.class, id);
		if (carouselBean==null) {
			response.setMessage("轮播图不存在");
			return response;
		}
		CarouselResult carouselResult = JsonTools.toObject(JsonTools.toJsonString(carouselBean), CarouselResult.class);
		List<CarouselPlotBean> carouselPlotBeanList = CarouselPlotBean.findAllByParams(CarouselPlotBean.class, "carouselId", carouselBean.getId());
		if(!ListTools.isEmptyOrNull(carouselPlotBeanList)){
			CarouselPlotBean carouselPlotBean = carouselPlotBeanList.get(0);
			if(carouselPlotBean.getPlot()!=null){
				carouselResult.setPlot(new String(carouselPlotBean.getPlot()));
			}
		}
		response.setData(carouselResult);
		response.setSuccess(Boolean.TRUE);
		return response;
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
	
	/**
	 * 分页查询轮播图
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<Map<String, Object>> queryCarouselPage(@ModelAttribute CarouselQueryForm form){
		return carouselService.queryCarouselPage(form);
	}
	
	/**
	 * 点击轮播图
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/click/{id:\\d+}", method = RequestMethod.POST)
	public Response<String> clickCarousel(@PathVariable Long id){
		Response<String> response = new Response<String>();
		try {
			carouselService.clickCarousel(id);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
}
