package com.cc.goods.service;

import com.cc.common.web.Page;
import com.cc.goods.bean.*;
import com.cc.goods.form.GoodsQueryForm;
import com.cc.goods.result.GoodsListResult;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
public interface GoodsService {

    /**
     * 保存商品
     * @param goodsBean
     * @param goodsStockBean
     * @param goodsPlotBean
     * @param goodsImageBeanList
     * @param goodsChannelBeanList
     * @param goodsLocationBean
     */
    void saveGoods(GoodsBean goodsBean, GoodsStockBean goodsStockBean, GoodsPlotBean goodsPlotBean, List<GoodsImageBean> goodsImageBeanList, List<GoodsChannelBean> goodsChannelBeanList, GoodsLocationBean goodsLocationBean);

    /**
     * 上架商品
     * @param id
     */
    void upGoods(Long id);

    /**
     * 下架商品
     * @param id
     */
    void downGoods(Long id);

    /**
     * 分页查询商品
     * @param form
     * @return
     */
    Page<GoodsListResult> queryGoodsPage(GoodsQueryForm form);

    /**
     * 删除商品
     * @param id
     */
    void deleteGoods(Long id);

    /**
     * 扣减商品库存
     * @param stock
     * @param goodsId
     */
    void minusGoodsStock(Integer stock, Long goodsId);
}
