package com.cc.seller.form;

import com.cc.common.tools.StringTools;

/**
 * Created by yuanwenshu on 2018/10/23.
 */
public class SellerQueryForm {

    /**
     * 卖家名称/真是姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 卖家状态
     */
    private String status;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
}
