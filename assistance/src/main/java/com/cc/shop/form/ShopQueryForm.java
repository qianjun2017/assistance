package com.cc.shop.form;

import com.cc.common.tools.StringTools;

/**
 * Created by yuanwenshu on 2018/10/24.
 */
public class ShopQueryForm {

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺类型
     */
    private String type;

    /**
     * 店铺状态
     */
    private String status;

    /**
     * 卖家
     */
    private Long sellerId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
