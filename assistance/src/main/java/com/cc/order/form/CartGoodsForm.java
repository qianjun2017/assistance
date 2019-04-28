package com.cc.order.form;

import com.cc.goods.bean.GoodsBean;

/**
 * Created by yuanwenshu on 2018/10/30.
 */
public class CartGoodsForm {

    private GoodsBean goods;

    private Integer quantity;

    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
