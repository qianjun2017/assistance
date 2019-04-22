package com.cc.goods.service;

import com.cc.common.web.Tree;
import com.cc.goods.bean.GoodsCategoryBean;

import java.util.Map;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
public interface GoodsCategoryService {
    /**
     * 保存商品品类
     * @param goodsCategoryBean
     */
    void saveGoodsCategory(GoodsCategoryBean goodsCategoryBean);

    /**
     * 删除商品品类
     * @param id
     */
    void deleteGoodsCategory(Long id);

    /**
     * 删除商品品类及子商品品类
     * @param id
     */
    void deleteGoodsCategoryCascade(Long id);

    /**
     * 获取商品品类树
     * @return
     */
    Tree<Map<String, Object>> queryGoodsCategoryTree();
}
