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
public enum ShopTypeEnum {

	TAOBAO("taobao", "淘宝");

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
	private ShopTypeEnum(String code, String name) {
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
	 * 获取店铺类型
	 * @param code
	 * @return
	 */
	public static ShopTypeEnum getShopTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ShopTypeEnum[] shopTypeEnums = ShopTypeEnum.values();
		List<ShopTypeEnum> shopTypeEnumList = ArrayTools.toList(shopTypeEnums).stream().filter(shopTypeEnum->code.equals(shopTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(shopTypeEnumList)){
			return null;
		}
		if (shopTypeEnumList.size()>1) {
			throw new LogicException("E001", "店铺类型不唯一,店铺类型编码["+code+"]");
		}
		return shopTypeEnumList.get(0);
	}
	
	/**
	 * 获取店铺类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ShopTypeEnum shopTypeEnum = ShopTypeEnum.getShopTypeEnumByCode(code);
		if (shopTypeEnum==null) {
			return null;
		}
		return shopTypeEnum.getName();
	}
}
