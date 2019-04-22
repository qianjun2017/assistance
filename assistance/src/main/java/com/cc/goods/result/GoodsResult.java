package com.cc.goods.result;

import com.cc.goods.bean.GoodsCategoryBean;
import com.cc.goods.bean.GoodsImageBean;
import com.cc.goods.bean.GoodsShopBean;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.user.bean.UserBean;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
public class GoodsResult {

    private Long id;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 卖家
     */
    private UserBean userBean;

    /**
     * 店铺
     */
    private List<GoodsShopBean> goodsShopBeanList;

    /**
     * 商品品类
     */
    private GoodsCategoryBean goodsCategoryBean;

    /**
     * 商品状态
     */
    private String status;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 商品价格（元）
     */
    private String price;

    /**
     * 商品图片
     */
    private List<GoodsImageBean> imageList;

    /**
     * 发布频道
     */
    private List<Long> channelList;

    /**
     * 商品简介
     */
    private String plot;
    
    /**
     * 商品详情
     */
    private String description;

    /**
     * 商品库存
     */
    private Long stock;

    /**
     * 销售区域
     */
    private LocationBean locationBean;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public GoodsCategoryBean getGoodsCategoryBean() {
        return goodsCategoryBean;
    }

    public void setGoodsCategoryBean(GoodsCategoryBean goodsCategoryBean) {
        this.goodsCategoryBean = goodsCategoryBean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<GoodsImageBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<GoodsImageBean> imageList) {
        this.imageList = imageList;
    }

    public List<Long> getChannelList() {
        return channelList;
    }

    public void setChannelList(List<Long> channelList) {
        this.channelList = channelList;
    }

    public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public LocationBean getLocationBean() {
        return locationBean;
    }

    public void setLocationBean(LocationBean locationBean) {
        this.locationBean = locationBean;
    }

	/**
	 * @return the goodsShopBeanList
	 */
	public List<GoodsShopBean> getGoodsShopBeanList() {
		return goodsShopBeanList;
	}

	/**
	 * @param goodsShopBeanList the goodsShopBeanList to set
	 */
	public void setGoodsShopBeanList(List<GoodsShopBean> goodsShopBeanList) {
		this.goodsShopBeanList = goodsShopBeanList;
	}
}
