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
public enum PayStatusEnum {

	PAY("pay", "未支付"),
	PAYING("paying", "支付中"),
	PAYED("payed", "已支付"),
	NOPAY("nopay","支付失败"),
	FUND("fund","未退款"),
	FUNDING("funding","退款中"),
	FUNDED("funded","已退款"),
	NOFUND("nofund","退款失败");
	
	/**
	 * @param code
	 * @param name
	 */
	private PayStatusEnum(String code, String name) {
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
	 * 获取支付状态
	 * @param code
	 * @return
	 */
	public static PayStatusEnum getPayStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		PayStatusEnum[] payStatusEnums = PayStatusEnum.values();
		List<PayStatusEnum> payStatusEnumList = ArrayTools.toList(payStatusEnums).stream().filter(payStatusEnum->code.equals(payStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(payStatusEnumList)){
			return null;
		}
		if (payStatusEnumList.size()>1) {
			throw new LogicException("E001", "支付状态不唯一,支付状态编码["+code+"]");
		}
		return payStatusEnumList.get(0);
	}
	
	/**
	 * 获取支付状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		PayStatusEnum payStatusEnum = PayStatusEnum.getPayStatusEnumByCode(code);
		if (payStatusEnum==null) {
			return null;
		}
		return payStatusEnum.getName();
	}

}
