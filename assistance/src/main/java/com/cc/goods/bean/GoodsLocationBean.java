package com.cc.goods.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuanwenshu on 2018/11/26.
 */
@Table(name="t_goods_location")
public class GoodsLocationBean extends BaseOrm<GoodsLocationBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 465784402786889482L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 销售区域
     */
    private Long locationId;

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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
