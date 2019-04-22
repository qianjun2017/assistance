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
@Table(name="t_goods_image")
public class GoodsImageBean extends BaseOrm<GoodsImageBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4538788810010979700L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 图片序号
     */
    private Integer imageNo;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getImageNo() {
        return imageNo;
    }

    public void setImageNo(Integer imageNo) {
        this.imageNo = imageNo;
    }
}
