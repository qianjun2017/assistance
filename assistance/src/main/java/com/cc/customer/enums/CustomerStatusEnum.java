/**
 * 
 */
package com.cc.customer.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public enum CustomerStatusEnum {
	
	NORMAL("normal", "正常"),
	LOCKED("locked", "锁定");

	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 说明
	 */
	private String name;

	/**
	 * @param code
	 * @param name
	 */
	private CustomerStatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
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
	 * 获取客户状态
	 * @param code
	 * @return
	 */
	public static CustomerStatusEnum getCustomerStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		CustomerStatusEnum[] customerStatusEnums = CustomerStatusEnum.values();
		List<CustomerStatusEnum> customerStatusEnumList = ArrayTools.toList(customerStatusEnums).stream().filter(customerStatusEnum->code.equals(customerStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(customerStatusEnumList)){
			return null;
		}
		if (customerStatusEnumList.size()>1) {
			throw new LogicException("E001", "客户状态不唯一,客户状态编码["+code+"]");
		}
		return customerStatusEnumList.get(0);
	}
	
	/**
	 * 获取客户状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		CustomerStatusEnum customerStatusEnum = CustomerStatusEnum.getCustomerStatusEnumByCode(code);
		if (customerStatusEnum==null) {
			return null;
		}
		return customerStatusEnum.getName();
	}
}
