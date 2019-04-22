/**
 * 
 */
package com.cc.seller.enums;

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
public enum SellerStatusEnum {

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
	private SellerStatusEnum(String code, String name) {
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
	 * 获取卖家状态
	 * @param code
	 * @return
	 */
	public static SellerStatusEnum getSellerStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		SellerStatusEnum[] sellerStatusEnums = SellerStatusEnum.values();
		List<SellerStatusEnum> sellerStatusEnumList = ArrayTools.toList(sellerStatusEnums).stream().filter(sellerStatusEnum->code.equals(sellerStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(sellerStatusEnumList)){
			return null;
		}
		if (sellerStatusEnumList.size()>1) {
			throw new LogicException("E001", "卖家状态不唯一,卖家状态编码["+code+"]");
		}
		return sellerStatusEnumList.get(0);
	}
	
	/**
	 * 获取卖家状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		SellerStatusEnum sellerStatusEnum = SellerStatusEnum.getSellerStatusEnumByCode(code);
		if (sellerStatusEnum==null) {
			return null;
		}
		return sellerStatusEnum.getName();
	}
}
