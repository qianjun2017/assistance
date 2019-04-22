package com.cc.goods.service.impl;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Page;
import com.cc.goods.bean.*;
import com.cc.goods.dao.GoodsDao;
import com.cc.goods.enums.GoodsStatusEnum;
import com.cc.goods.form.GoodsQueryForm;
import com.cc.goods.result.GoodsListResult;
import com.cc.goods.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void saveGoods(GoodsBean goodsBean, GoodsStockBean goodsStockBean, GoodsPlotBean goodsPlotBean, List<GoodsImageBean> goodsImageBeanList, List<GoodsChannelBean> goodsChannelBeanList, GoodsLocationBean goodsLocationBean) {
        int row = goodsBean.save();
        if(row!=1){
            throw new LogicException("E001", "保存商品失败");
        }
        List<GoodsStockBean> goodsStockBeanList = GoodsStockBean.findAllByParams(GoodsStockBean.class, "goodsId", goodsBean.getId());
        if(ListTools.isEmptyOrNull(goodsStockBeanList)){
            goodsStockBean.setGoodsId(goodsBean.getId());
            row = goodsStockBean.save();
        }else{
            GoodsStockBean _goodsStockBean = goodsStockBeanList.get(0);
            _goodsStockBean.setStock(goodsStockBean.getStock());
            row = _goodsStockBean.updateForce();
        }
        if(row!=1){
            throw new LogicException("E002", "保存商品库存失败");
        }
        List<GoodsPlotBean> goodsPlotBeanList = GoodsPlotBean.findAllByParams(GoodsPlotBean.class, "goodsId", goodsBean.getId());
        if(ListTools.isEmptyOrNull(goodsPlotBeanList)){
            if(goodsPlotBean.getDescription()!=null || goodsPlotBean.getPlot()!=null) {
            	goodsPlotBean.setGoodsId(goodsBean.getId());
                row = goodsPlotBean.save();
                if(row!=1){
                    throw new LogicException("E003", "保存商品详情失败");
                }
            }
        }else{
            GoodsPlotBean _goodsPlotBean = goodsPlotBeanList.get(0);
            if(goodsPlotBean.getDescription()!=null || goodsPlotBean.getPlot()!=null) {
            	_goodsPlotBean.setDescription(goodsPlotBean.getDescription());
            	_goodsPlotBean.setPlot(goodsPlotBean.getPlot());
                row = _goodsPlotBean.updateForce();
            }else{
                row = _goodsPlotBean.delete();
            }
            if(row!=1){
                throw new LogicException("E004", "保存商品详情失败");
            }
        }
        Example goodsImageExample = new Example(GoodsImageBean.class);
        Example.Criteria goodsImageCriteria = goodsImageExample.createCriteria();
        goodsImageCriteria.andEqualTo("goodsId", goodsBean.getId());
        GoodsImageBean.deleteByExample(GoodsImageBean.class, goodsImageExample);
        if(!ListTools.isEmptyOrNull(goodsImageBeanList)){
            for(GoodsImageBean goodsImageBean: goodsImageBeanList){
                goodsImageBean.setGoodsId(goodsBean.getId());
                row = goodsImageBean.insert();
                if(row!=1){
                    throw new LogicException("E005", "保存商品图片失败");
                }
            }
        }
        Example goodsChannelExample = new Example(GoodsChannelBean.class);
        Example.Criteria goodsChannelCriteria = goodsChannelExample.createCriteria();
        goodsChannelCriteria.andEqualTo("goodsId", goodsBean.getId());
        GoodsChannelBean.deleteByExample(GoodsChannelBean.class, goodsChannelExample);
        if(!ListTools.isEmptyOrNull(goodsChannelBeanList)){
            for(GoodsChannelBean goodsChannelBean: goodsChannelBeanList){
            	goodsChannelBean.setGoodsId(goodsBean.getId());
                row = goodsChannelBean.insert();
                if(row!=1){
                    throw new LogicException("E006", "保存商品发布频道失败");
                }
            }
        }
        List<GoodsLocationBean> goodsLocationBeanList = GoodsLocationBean.findAllByParams(GoodsLocationBean.class, "goodsId", goodsBean.getId());
        if(!ListTools.isEmptyOrNull(goodsLocationBeanList)){
            GoodsLocationBean goodsLocation = goodsLocationBeanList.get(0);
            goodsLocation.setLocationId(goodsLocationBean.getLocationId());
            goodsLocation.update();
        }else{
            GoodsLocationBean newGoodsLocationBean = new GoodsLocationBean();
            newGoodsLocationBean.setLocationId(goodsLocationBean.getLocationId());
            newGoodsLocationBean.setGoodsId(goodsBean.getId());
            newGoodsLocationBean.insert();
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void upGoods(Long id) {
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setStatus(GoodsStatusEnum.ON.getCode());
        goodsBean.setId(id);
        int row = goodsBean.update();
        if(row!=1){
            throw new LogicException("E001", "商品上架失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void downGoods(Long id) {
        GoodsBean goodsBean = new GoodsBean();
        goodsBean.setStatus(GoodsStatusEnum.DOWN.getCode());
        goodsBean.setId(id);
        int row = goodsBean.update();
        if(row!=1){
            throw new LogicException("E001", "商品下架失败");
        }
    }

    @Override
    public Page<GoodsListResult> queryGoodsPage(GoodsQueryForm form) {
        Page<GoodsListResult> page = new Page<GoodsListResult>();
        PageHelper.orderBy(String.format("g.%s %s", form.getSort(), form.getOrder()));
        PageHelper.startPage(form.getPage(), form.getPageSize());
        List<GoodsListResult> goodsList = goodsDao.queryGoodsList(form);
        PageInfo<GoodsListResult> pageInfo = new PageInfo<GoodsListResult>(goodsList);
        if (ListTools.isEmptyOrNull(goodsList)) {
            page.setMessage("没有查询到相关商品数据");
            return page;
        }
        for(GoodsListResult goodsListResult: goodsList){
        	goodsListResult.setPrice(StringTools.toRmbYuan(goodsListResult.getPrice()));
        	if(form.getCard()){
	        	List<GoodsImageBean> goodsImageBeanList = GoodsImageBean.findAllByParams(GoodsImageBean.class, "goodsId", goodsListResult.getId());
	        	if(!ListTools.isEmptyOrNull(goodsImageBeanList)){
	        		goodsListResult.setImageUrl(goodsImageBeanList.get(0).getImageUrl());
	        	}
        	}
        }
        page.setPage(pageInfo.getPageNum());
        page.setPages(pageInfo.getPages());
        page.setPageSize(pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        page.setData(goodsList);
        page.setSuccess(Boolean.TRUE);
        return page;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deleteGoods(Long id) {
        Example example = new Example(GoodsBean.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", id);
        int row = GoodsBean.deleteByExample(GoodsBean.class, example);
        if(row!=1){
            throw new LogicException("E001", "删除商品失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void minusGoodsStock(Integer stock, Long goodsId) {
        if(stock != 0){
            int row = goodsDao.minusGoodsStock(stock, goodsId);
            if(row!=1){
                throw new LogicException("E001", "变更库存失败");
            }
            List<GoodsStockBean> goodsStockBeanList = GoodsStockBean.findAllByParams(GoodsStockBean.class, "goodsId", goodsId);
            GoodsStockBean goodsStockBean = goodsStockBeanList.get(0);
            GoodsBean goodsBean = GoodsBean.get(GoodsBean.class, goodsId);
            GoodsStatusEnum goodsStatusEnum = GoodsStatusEnum.getGoodsStatusEnumByCode(goodsBean.getStatus());
            if(GoodsStatusEnum.OOS.equals(goodsStatusEnum) && (goodsStockBean.getStock()-goodsStockBean.getLockStock()>0L)){
            	GoodsBean goodsBean2 = new GoodsBean();
            	goodsBean2.setId(goodsId);
            	goodsBean2.setStatus(GoodsStatusEnum.ON.getCode());
            	row = goodsBean2.update();
            	if(row!=1){
                    throw new LogicException("E002", "商品重新上架失败");
                }
            }else if(GoodsStatusEnum.ON.equals(goodsStatusEnum) && (goodsStockBean.getStock()-goodsStockBean.getLockStock()<=0L)){
            	GoodsBean goodsBean2 = new GoodsBean();
            	goodsBean2.setId(goodsId);
            	goodsBean2.setStatus(GoodsStatusEnum.OOS.getCode());
            	row = goodsBean2.update();
            	if(row!=1){
                    throw new LogicException("E003", "商品缺货变更失败");
                }
            }
        }
    }
}
