package com.cc.goods.web;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.common.web.Response;
import com.cc.goods.bean.*;
import com.cc.goods.enums.GoodsStatusEnum;
import com.cc.goods.form.GoodsForm;
import com.cc.goods.form.GoodsQueryForm;
import com.cc.goods.result.GoodsListResult;
import com.cc.goods.result.GoodsResult;
import com.cc.goods.service.GoodsService;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.log.utils.LogContextUtil;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.system.user.bean.UserBean;
import com.cc.system.user.enums.UserTypeEnum;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品
     * @param goodsMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.add" })
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSMANAGEMENT, operType = OperTypeEnum.ADD, title = "新增商品")
    public Response<String> addGoods(@RequestBody Map<String, Object> goodsMap) {
        Response<String> response = new Response<String>();
        GoodsForm goods = JsonTools.toObject(JsonTools.toJsonString(goodsMap), GoodsForm.class);
        GoodsBean goodsBean = new GoodsBean();
        if (goods.getCategoryId()==null) {
            response.setMessage("请选择商品类型");
            return response;
        }
        GoodsCategoryBean goodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, goods.getCategoryId());
        if(goodsCategoryBean==null){
            response.setMessage("商品类型不存在，请重新选择");
            return response;
        }
        goodsBean.setCategoryId(goodsCategoryBean.getId());
        if (StringTools.isNullOrNone(goods.getName())) {
            response.setMessage("请输入商品名称");
            return response;
        }
        goodsBean.setName(goods.getName());
        UserBean userBean = SecurityContextUtil.getCurrentUser();
        goodsBean.setSellerId(userBean.getId());
        if (StringTools.isNullOrNone(goods.getPrice())) {
            response.setMessage("请输入商品价格");
            return response;
        }
        goodsBean.setPrice(Long.valueOf(StringTools.toRmbCent(goods.getPrice())));
        if(goods.getStock()==null){
            response.setMessage("请输入商品库存");
            return response;
        }
        if(goods.getStock()<=0L){
        	response.setMessage("商品库存必须大于0");
            return response;
        }
        GoodsStockBean goodsStockBean = new GoodsStockBean();
        goodsStockBean.setStock(goods.getStock());
        goodsStockBean.setLockStock(0L);
        GoodsPlotBean goodsPlotBean = new GoodsPlotBean();
        if(!StringTools.isNullOrNone(goods.getDescription())){
        	goodsPlotBean.setDescription(goods.getDescription().getBytes());
        }
        if(!StringTools.isNullOrNone(goods.getPlot())){
        	goodsPlotBean.setPlot(goods.getPlot().getBytes());
        }
        List<GoodsImageBean> goodsImageBeanList = new ArrayList<GoodsImageBean>();
        if(ListTools.isEmptyOrNull(goods.getImageList())){
            response.setMessage("至少上传一张商品图片");
            return response;
        }
        if(goodsImageBeanList.size()>5){
        	response.setMessage("商品图片不能超过5张");
            return response;
        }
        for(String imageUrl: goods.getImageList()){
            GoodsImageBean goodsImageBean = new GoodsImageBean();
            goodsImageBean.setImageUrl(imageUrl);
            goodsImageBean.setImageNo(goods.getImageList().indexOf(imageUrl)+1);
            goodsImageBeanList.add(goodsImageBean);
        }
        goodsBean.setImgUrl(goods.getImageList().get(0));
        List<GoodsChannelBean> goodsChannelBeanList = new ArrayList<GoodsChannelBean>();
        if(ListTools.isEmptyOrNull(goods.getChannelList())){
            response.setMessage("至少选择一个频道");
            return response;
        }
        for(Integer channelId: goods.getChannelList()){
            GoodsChannelBean goodsChannelBean = new GoodsChannelBean();
            goodsChannelBean.setChannelId(new Long(channelId));
            goodsChannelBeanList.add(goodsChannelBean);
        }
        if(goods.getLocationId()==null){
            response.setMessage("请选择销售区域");
            return response;
        }
        GoodsLocationBean goodsLocationBean = new GoodsLocationBean();
        goodsLocationBean.setLocationId(goods.getLocationId());
        try {
            goodsBean.setStatus(GoodsStatusEnum.DRAFT.getCode());
            goodsBean.setCreateTime(DateTools.now());
            goodsBean.setCode(StringTools.getSeqNo());
            goodsService.saveGoods(goodsBean, goodsStockBean, goodsPlotBean, goodsImageBeanList, goodsChannelBeanList, goodsLocationBean);
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
     * 修改商品
     * @param goodsMap
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.update" })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSMANAGEMENT, operType = OperTypeEnum.UPDATE, title = "修改商品")
    public Response<String> updateGoods(@RequestBody Map<String, Object> goodsMap) {
        Response<String> response = new Response<String>();
        GoodsForm goods = JsonTools.toObject(JsonTools.toJsonString(goodsMap), GoodsForm.class);
        if(goods.getId()==null){
            response.setMessage("缺少商品主键");
            return response;
        }
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, goods.getId());
        if(goodsBean==null){
            response.setMessage("商品不存在");
            return response;
        }
        if (goods.getCategoryId()==null) {
            response.setMessage("请选择商品类型");
            return response;
        }
        GoodsCategoryBean goodsCategoryBean = GoodsCategoryBean.get(GoodsCategoryBean.class, goods.getCategoryId());
        if(goodsCategoryBean==null){
            response.setMessage("商品类型不存在，请重新选择");
            return response;
        }
        goodsBean.setCategoryId(goodsCategoryBean.getId());
        if (StringTools.isNullOrNone(goods.getName())) {
            response.setMessage("请输入商品名称");
            return response;
        }
        goodsBean.setName(goods.getName());
        UserBean userBean = SecurityContextUtil.getCurrentUser();
        goodsBean.setSellerId(userBean.getId());
        if (StringTools.isNullOrNone(goods.getPrice())) {
            response.setMessage("请输入商品价格");
            return response;
        }
        goodsBean.setPrice(Long.valueOf(StringTools.toRmbCent(goods.getPrice())));
        if(goods.getStock()==null){
            response.setMessage("请输入商品库存");
            return response;
        }
        if(goods.getStock()<=0L){
        	response.setMessage("商品库存必须大于0");
            return response;
        }
        GoodsStockBean goodsStockBean = new GoodsStockBean();
        goodsStockBean.setStock(goods.getStock());
        GoodsPlotBean goodsPlotBean = new GoodsPlotBean();
        if(!StringTools.isNullOrNone(goods.getDescription())){
        	goodsPlotBean.setDescription(goods.getDescription().getBytes());
        }
        if(!StringTools.isNullOrNone(goods.getPlot())){
        	goodsPlotBean.setPlot(goods.getPlot().getBytes());
        }
        List<GoodsImageBean> goodsImageBeanList = new ArrayList<GoodsImageBean>();
        if(ListTools.isEmptyOrNull(goods.getImageList())){
            response.setMessage("至少上传一张商品图片");
            return response;
        }
        for(String imageUrl: goods.getImageList()){
            GoodsImageBean goodsImageBean = new GoodsImageBean();
            goodsImageBean.setImageUrl(imageUrl);
            goodsImageBean.setImageNo(goods.getImageList().indexOf(imageUrl)+1);
            goodsImageBeanList.add(goodsImageBean);
        }
        goodsBean.setImgUrl(goods.getImageList().get(0));
        List<GoodsChannelBean> goodsChannelBeanList = new ArrayList<GoodsChannelBean>();
        if(ListTools.isEmptyOrNull(goods.getChannelList())){
            response.setMessage("至少选择一个频道");
            return response;
        }
        for(Integer channelId: goods.getChannelList()){
            GoodsChannelBean goodsChannelBean = new GoodsChannelBean();
            goodsChannelBean.setChannelId(new Long(channelId));
            goodsChannelBeanList.add(goodsChannelBean);
        }
        if(goods.getLocationId()==null){
            response.setMessage("请选择销售区域");
            return response;
        }
        GoodsLocationBean goodsLocationBean = new GoodsLocationBean();
        goodsLocationBean.setLocationId(goods.getLocationId());
        try {
            goodsService.saveGoods(goodsBean, goodsStockBean, goodsPlotBean, goodsImageBeanList, goodsChannelBeanList, goodsLocationBean);
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
     * 上架商品
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.up" })
    @RequestMapping(value = "/up/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSMANAGEMENT, operType = OperTypeEnum.UP, title = "上架商品", paramNames = {"id"})
    public Response<String> upGoods(@PathVariable Long id){
        Response<String> response = new Response<String>();
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, id);
        if (goodsBean==null) {
            response.setMessage("商品不存在");
            return response;
        }
        try {
            LogContextUtil.setOperContent("上架商品["+goodsBean.getName()+"]");
            goodsService.upGoods(id);
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
     * 下架商品
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.down" })
    @RequestMapping(value = "/down/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSMANAGEMENT, operType = OperTypeEnum.DOWN, title = "下架商品", paramNames = {"id"})
    public Response<String> downGoods(@PathVariable Long id){
        Response<String> response = new Response<String>();
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, id);
        if (goodsBean==null) {
            response.setMessage("商品不存在");
            return response;
        }
        try {
            LogContextUtil.setOperContent("下架商品["+goodsBean.getName()+"]");
            goodsService.downGoods(id);
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
     * 删除商品
     * @param id
     * @return
     */
    @ResponseBody
    @RequiresPermissions(value = { "goods.delete" })
    @RequestMapping(value = "/delete/{id:\\d+}", method = RequestMethod.POST)
    @OperationLog(module = ModuleEnum.GOODSMANAGEMENT, operType = OperTypeEnum.DELETE, title = "删除商品", paramNames = {"id"})
    public Response<String> deleteGoods(@PathVariable Long id){
        Response<String> response = new Response<String>();
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, id);
        if (goodsBean==null) {
            response.setMessage("商品不存在");
            return response;
        }
        GoodsStatusEnum goodsStatusEnum = GoodsStatusEnum.getGoodsStatusEnumByCode(goodsBean.getStatus());
        if(GoodsStatusEnum.ON.equals(goodsStatusEnum)){
            response.setMessage("请先下架商品");
            return response;
        }
        try {
            LogContextUtil.setOperContent("删除商品["+goodsBean.getName()+"]");
            goodsService.deleteGoods(id);
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
     * 分页查询商品
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public Page<GoodsListResult> queryGoodsPage(@ModelAttribute GoodsQueryForm form){
        UserBean userBean = SecurityContextUtil.getCurrentUser();
        if(userBean==null){
        	Page<GoodsListResult> page = new Page<GoodsListResult>();
            page.setMessage("没有查询到相关商品数据");
            return page;
        }
        UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(userBean.getUserType());
        if(UserTypeEnum.SELLER.equals(userTypeEnum)){
            form.setSellerId(userBean.getId());
        }
        return goodsService.queryGoodsPage(form);
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
    public Response<GoodsResult> queryGoods(@PathVariable Long id){
        Response<GoodsResult> response = new Response<GoodsResult>();
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, id);
        if(goodsBean==null){
            response.setMessage("商品不存在");
            return response;
        }
        GoodsResult goodsResult = new GoodsResult();
        goodsResult.setId(goodsBean.getId());
        goodsResult.setCode(goodsBean.getCode());
        goodsResult.setName(goodsBean.getName());
        goodsResult.setPrice(StringTools.toRmbYuan(StringTools.toString(goodsBean.getPrice())));
        goodsResult.setStatus(goodsBean.getStatus());
        goodsResult.setCreateTime(goodsBean.getCreateTime());
        List<GoodsStockBean> goodsStockBeanList = GoodsStockBean.findAllByParams(GoodsStockBean.class, "goodsId", goodsBean.getId());
        if(!ListTools.isEmptyOrNull(goodsStockBeanList)){
            GoodsStockBean goodsStockBean = goodsStockBeanList.get(0);
            goodsResult.setStock(goodsStockBean.getStock()-goodsStockBean.getLockStock());
        }
        goodsResult.setGoodsCategoryBean(GoodsCategoryBean.get(GoodsCategoryBean.class, goodsBean.getCategoryId()));
        goodsResult.setImageList(GoodsImageBean.findAllByParams(GoodsImageBean.class, "goodsId", goodsBean.getId()));
        List<GoodsChannelBean> goodsChannelBeanList = GoodsChannelBean.findAllByParams(GoodsChannelBean.class, "goodsId", goodsBean.getId());
        if(!ListTools.isEmptyOrNull(goodsChannelBeanList)){
            goodsResult.setChannelList(goodsChannelBeanList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
        }
        List<GoodsPlotBean> goodsPlotBeanList = GoodsPlotBean.findAllByParams(GoodsPlotBean.class, "goodsId", goodsBean.getId());
        if(!ListTools.isEmptyOrNull(goodsPlotBeanList)){
            goodsResult.setDescription(new String(goodsPlotBeanList.get(0).getDescription()));
            byte[] plot = goodsPlotBeanList.get(0).getPlot();
			if(plot!=null){
				goodsResult.setPlot(new String(plot));
			}
        }
        List<GoodsShopBean> goodsShopBeanList = GoodsShopBean.findAllByParams(GoodsShopBean.class, "goodsId", goodsBean.getId());
        goodsResult.setGoodsShopBeanList(goodsShopBeanList);
        goodsResult.setUserBean(UserBean.get(UserBean.class, goodsBean.getSellerId()));
        List<GoodsLocationBean> goodsLocationBeanList = GoodsLocationBean.findAllByParams(GoodsLocationBean.class, "goodsId", goodsBean.getId());
        if(!ListTools.isEmptyOrNull(goodsLocationBeanList)){
            GoodsLocationBean goodsLocationBean = goodsLocationBeanList.get(0);
            if(goodsLocationBean!=null){
                goodsResult.setLocationBean(LocationBean.get(LocationBean.class, goodsLocationBean.getLocationId()));
            }
        }
        response.setSuccess(Boolean.TRUE);
        response.setData(goodsResult);
        return response;
    }

    /**
     * 调整商品库存
     * @param stockMap
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stock/adjust", method = RequestMethod.GET)
    @OperationLog(module = ModuleEnum.STOCKMANAGEMENT, operType = OperTypeEnum.ADJUST, title = "调整商品库存商品")
    public Response<String> adjustStock(@RequestBody Map<String, Object> stockMap){
        Response<String> response = new Response<String>();
        Object goodsId = stockMap.get("goodsId");
        Object stock = stockMap.get("stock");
        if(goodsId == null){
            response.setMessage("请选择商品");
            return response;
        }
        if(stock==null){
            response.setMessage("请输入新增商品库存");
            return response;
        }
        if(Integer.valueOf(StringTools.toString(stock)) == 0){
            response.setMessage("调整商品库存值不能为0");
            return response;
        }
        GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, Long.valueOf(StringTools.toString(goodsId)));
        if(goodsBean == null){
            response.setMessage("所选商品不存在");
            return response;
        }
        try {
            goodsService.minusGoodsStock(-Integer.valueOf(StringTools.toString(stock)), Long.valueOf(StringTools.toString(goodsId)));
            response.setSuccess(Boolean.TRUE);
        } catch (LogicException e){
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setMessage("系统内部错误");
            e.printStackTrace();
        }
        return response;
    }
}
