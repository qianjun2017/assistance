package com.cc.order.bean;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by yuanwenshu on 2018/10/29.
 */
@Table(name="t_order_sub_goods")
public class OrderSubGoodsBean extends BaseOrm<OrderSubGoodsBean> implements BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7046871632535907374L;

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单
     */
    private Long orderSubId;

    /**
     * 商品
     */
    private Long goodsId;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 购买单价
     */
    private Long price;

    /**
     * 购买数量
     */
    private Integer quantity;

    /**
     * 商品总价
     */
    private Long total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderSubId() {
        return orderSubId;
    }

    public void setOrderSubId(Long orderSubId) {
        this.orderSubId = orderSubId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
