package com.cc.goods.form;

import com.cc.common.tools.StringTools;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by yuanwenshu on 2018/10/25.
 */
public class GoodsQueryForm {

    /**
     * 卖家
     */
    private Long sellerId;

    /**
     * 卖家名称
     */
    private String sellerName;

    /**
     * 店铺
     */
    private Long shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品品类
     */
    private Long categoryId;

    /**
     * 销售区域
     */
    private Long locationId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品状态
     */
    private String status;
    
    /**
     * 是否卡片查询
     */
    private Boolean card = Boolean.FALSE;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    /**
     * 页码
     */
    private String page = "1";

    /**
     * 每页数量
     */
    private String pageSize = "10";

    /**
     * 排序字段
     */
    private String sort;

    /**
     * 排序方向
     */
    private String order;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    /**
     * @return the page
     */
    public int getPage() {
        if(!StringTools.isNullOrNone(this.page) && StringTools.isNumber(this.page)){
            return Integer.parseInt(this.page);
        }
        return 1;
    }

    /**
     * @param page the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        if(!StringTools.isNullOrNone(this.pageSize) && StringTools.isNumber(this.pageSize)){
            return Integer.parseInt(this.pageSize);
        }
        return 10;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        if(StringTools.isNullOrNone(this.sort)){
            return "createTime";
        }
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /**
     * @return the order
     */
    public String getOrder() {
        if (StringTools.isNullOrNone(this.order)) {
            return "desc";
        }
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

	/**
	 * @return the card
	 */
	public Boolean getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Boolean card) {
		this.card = card;
	}
}
