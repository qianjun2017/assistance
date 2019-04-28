package com.cc.order.result;

import java.util.List;

import com.cc.order.bean.AddressBean;
import com.cc.order.bean.OrderBean;

/**
 * 预订单
 * Created by yuanwenshu on 2018/10/30.
 */
public class PreOrderResult {

    /**
     * 订单收货地址
     */
    private AddressBean address;

    /**
     * 订单信息
     */
    private OrderBean order;

    /**
     * 子订单列表
     */
    private List<OrderSubResult> orderSubList;

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

	/**
	 * @return the order
	 */
	public OrderBean getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(OrderBean order) {
		this.order = order;
	}

	/**
	 * @return the orderSubList
	 */
	public List<OrderSubResult> getOrderSubList() {
		return orderSubList;
	}

	/**
	 * @param orderSubList the orderSubList to set
	 */
	public void setOrderSubList(List<OrderSubResult> orderSubList) {
		this.orderSubList = orderSubList;
	}

}
