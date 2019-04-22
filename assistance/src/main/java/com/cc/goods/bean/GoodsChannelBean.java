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
@Table(name="t_goods_channel")
public class GoodsChannelBean extends BaseOrm<GoodsChannelBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7978114295055261916L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 频道
     */
    private Long channelId;

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

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

}
