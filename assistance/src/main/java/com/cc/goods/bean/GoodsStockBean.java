package com.cc.goods.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Table(name="t_goods_stock")
public class GoodsStockBean extends BaseOrm<GoodsStockBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -5211871659941601300L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 锁定库存
     */
    private Long lockStock;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getLockStock() {
        return lockStock;
    }

    public void setLockStock(Long lockStock) {
        this.lockStock = lockStock;
    }
}
