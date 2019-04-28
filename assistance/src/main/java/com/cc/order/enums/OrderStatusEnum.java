/**
 * 
 */
package com.cc.order.enums;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
public enum OrderStatusEnum {

	TOBEPAY("toBePay", "待付款"),
	TOBESHIPPED("toBeShipped", "待发货"),
	OUTOFSTOCK("outOfStock","出库中"),
	DISTRIBUTION("distribution","配送中"),
	TOBERECEIVED("toBeReceived","待收货"),
	RECEIVED("received","已收货"),
	TOBEPAID("toBePaid","待收款"),
	COMPLETED("completed","已完成"),
	CLOSED("closed","已关闭"),
	RETURN("return","退货中"),
	REFUND("refund","退款中"),
	TRADE("trade","进行中"),
	OVER("over","已结束");
	
	/**
	 * @param code
	 * @param name
	 */
	private OrderStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 说明
	 */
	private String name;
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取订单状态
	 * @param code
	 * @return
	 */
	public static OrderStatusEnum getOrderStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		OrderStatusEnum[] orderStatusEnums = OrderStatusEnum.values();
		List<OrderStatusEnum> orderStatusEnumList = ArrayTools.toList(orderStatusEnums).stream().filter(orderStatusEnum->code.equals(orderStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(orderStatusEnumList)){
			return null;
		}
		if (orderStatusEnumList.size()>1) {
			throw new LogicException("E001", "订单状态不唯一,订单状态编码["+code+"]");
		}
		return orderStatusEnumList.get(0);
	}
	
	/**
	 * 获取订单状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		OrderStatusEnum orderStatusEnum = OrderStatusEnum.getOrderStatusEnumByCode(code);
		if (orderStatusEnum==null) {
			return null;
		}
		return orderStatusEnum.getName();
	}

}
