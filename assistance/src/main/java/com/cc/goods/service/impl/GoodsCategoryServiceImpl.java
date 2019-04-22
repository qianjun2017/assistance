package com.cc.goods.service.impl;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Tree;
import com.cc.goods.bean.GoodsCategoryBean;
import com.cc.goods.service.GoodsCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void saveGoodsCategory(GoodsCategoryBean goodsCategoryBean) {
        int row = goodsCategoryBean.save();
        if(row!=1){
            throw new LogicException("E001", "保存商品品类失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deleteGoodsCategory(Long id) {
        GoodsCategoryBean goodsCategoryBean = new GoodsCategoryBean();
        goodsCategoryBean.setId(id);
        int row = goodsCategoryBean.delete();
        if(row!=1){
            throw new LogicException("E001", "删除商品品类失败");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
    public void deleteGoodsCategoryCascade(Long id) {
        List<GoodsCategoryBean> subGoodsCategoryBeanList = GoodsCategoryBean.findAllByParams(GoodsCategoryBean.class, "parentId", id);
        for(GoodsCategoryBean subGoodsCategoryBean: subGoodsCategoryBeanList){
            deleteGoodsCategoryCascade(subGoodsCategoryBean.getId());
        }
        deleteGoodsCategory(id);
    }

    @Override
    public Tree<Map<String, Object>> queryGoodsCategoryTree() {
        Tree<Map<String, Object>> tree = new Tree<Map<String,Object>>();
        List<GoodsCategoryBean> goodsCategoryBeanList = GoodsCategoryBean.findAllByParams(GoodsCategoryBean.class);
        if (ListTools.isEmptyOrNull(goodsCategoryBeanList)) {
            tree.setMessage("没有商品品类数据");
            return tree;
        }
        List<GoodsCategoryBean> topNodeList = goodsCategoryBeanList.stream().filter(goodsCategoryBean->(goodsCategoryBean.getParentId()==null)).collect(Collectors.toList());
        if (ListTools.isEmptyOrNull(topNodeList)) {
            tree.setMessage("没有找到顶级商品品类数据");
            return tree;
        }
        List<Map<String, Object>> topGoodsCategoryMapList = new ArrayList<Map<String,Object>>();
        for (GoodsCategoryBean goodsCategoryBean : topNodeList) {
            topGoodsCategoryMapList.add(addSubGoodsCategoryBean(goodsCategoryBean, goodsCategoryBeanList));
        }
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("id", -1);
        root.put("name", "商品品类树");
        root.put("subGoodsCategoryList", topGoodsCategoryMapList);
        tree.setRoot(root);
        int level = -1;
        for (GoodsCategoryBean goodsCategoryBean : goodsCategoryBeanList) {
            if (level<goodsCategoryBean.getLevel()) {
                level = goodsCategoryBean.getLevel();
            }
        }
        tree.setLevel(level);
        tree.setSuccess(Boolean.TRUE);
        return tree;
    }

    /**
     * 添加子商品品类
     * @param goodsCategoryBean
     * @param goodsCategoryBeanList
     * @return
     */
    private Map<String, Object> addSubGoodsCategoryBean(GoodsCategoryBean goodsCategoryBean, List<GoodsCategoryBean> goodsCategoryBeanList) {
        Map<String, Object> goodsCategoryMap = new HashMap<String, Object>();
        goodsCategoryMap.put("id", goodsCategoryBean.getId());
        goodsCategoryMap.put("name", goodsCategoryBean.getName());
        goodsCategoryMap.put("level", goodsCategoryBean.getLevel());
        if(!ListTools.isEmptyOrNull(goodsCategoryBeanList)){
            List<GoodsCategoryBean> subGoodsCategoryBeanList = goodsCategoryBeanList.stream().filter(subGoodsCategoryBean->goodsCategoryBean.getId().equals(subGoodsCategoryBean.getParentId())).collect(Collectors.toList());
            if (!ListTools.isEmptyOrNull(subGoodsCategoryBeanList)) {
                List<Map<String, Object>> subGoodsCategoryMapList = new ArrayList<Map<String,Object>>();
                for (GoodsCategoryBean subGoodsCategoryBean : subGoodsCategoryBeanList) {
                    subGoodsCategoryMapList.add(addSubGoodsCategoryBean(subGoodsCategoryBean, goodsCategoryBeanList));
                }
                goodsCategoryMap.put("subGoodsCategoryList", subGoodsCategoryMapList);
            }
        }
        return goodsCategoryMap;
    }
}
