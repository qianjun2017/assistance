package com.cc.goods.dao;

import com.cc.common.orm.dao.CrudDao;
import com.cc.goods.form.GoodsQueryForm;
import com.cc.goods.result.GoodsListResult;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Mapper
public interface GoodsDao extends CrudDao {

    /**
     * 查询商品列表
     * @param form
     * @return
     */
    List<GoodsListResult> queryGoodsList(GoodsQueryForm form);

    /**
     * 扣减商品库存
     * @param stock
     * @param goodsId
     * @return
     */
    int minusGoodsStock(@Param(value = "stock") Integer stock, @Param(value = "goodsId") Long goodsId);
}
