/**
 * 
 */
package com.cc.shop.enums;

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
public enum ShopStatusEnum {

	NORMAL("normal", "正常"),
	CLOSED("closed", "关闭"),
	PENDING("pending", "待审核"),
	FAILURE("failure", "审核失败");

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
	private ShopStatusEnum(String code, String name) {
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
	 * 获取店铺状态
	 * @param code
	 * @return
	 */
	public static ShopStatusEnum getShopStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ShopStatusEnum[] shopStatusEnums = ShopStatusEnum.values();
		List<ShopStatusEnum> shopStatusEnumList = ArrayTools.toList(shopStatusEnums).stream().filter(shopStatusEnum->code.equals(shopStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(shopStatusEnumList)){
			return null;
		}
		if (shopStatusEnumList.size()>1) {
			throw new LogicException("E001", "店铺状态不唯一,店铺状态编码["+code+"]");
		}
		return shopStatusEnumList.get(0);
	}
	
	/**
	 * 获取店铺状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ShopStatusEnum shopStatusEnum = ShopStatusEnum.getShopStatusEnumByCode(code);
		if (shopStatusEnum==null) {
			return null;
		}
		return shopStatusEnum.getName();
	}
}
