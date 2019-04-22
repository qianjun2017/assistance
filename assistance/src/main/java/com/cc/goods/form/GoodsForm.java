package com.cc.goods.form;

import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
public class GoodsForm {

    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品品类
     */
    private Long categoryId;

    /**
     * 商品价格（元）
     */
    private String price;

    /**
     * 商品详情
     */
    private String description;
    
    /**
     * 商品简介
     */
    private String plot;

    /**
     * 商品图片
     */
    private List<String> imageList;

    /**
     * 频道
     */
    private List<Integer> channelList;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 销售区域
     */
    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    public List<Integer> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Integer> channelList) {
        this.channelList = channelList;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
