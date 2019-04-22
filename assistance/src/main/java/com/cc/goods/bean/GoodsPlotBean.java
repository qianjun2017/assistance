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
@Table(name="t_goods_plot")
public class GoodsPlotBean extends BaseOrm<GoodsPlotBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2372946475533318258L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 商品详情
     */
    private byte[] description;
    
    /**
     * 商品简介
     */
    private byte[] plot;

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

    public byte[] getDescription() {
        return description;
    }

    public void setDescription(byte[] description) {
        this.description = description;
    }

	/**
	 * @return the plot
	 */
	public byte[] getPlot() {
		return plot;
	}

	/**
	 * @param plot the plot to set
	 */
	public void setPlot(byte[] plot) {
		this.plot = plot;
	}
}
