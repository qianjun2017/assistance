/**
 * 
 */
package com.cc.goods.enums;

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
public enum GoodsStatusEnum {

	DRAFT("draft", "未上架"),
	DOWN("down", "已下架"),
	ON("on","上架中"),
	OOS("oos","缺货中");
	
	/**
	 * @param code
	 * @param name
	 */
	private GoodsStatusEnum(String code, String name) {
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
	 * 获取商品状态
	 * @param code
	 * @return
	 */
	public static GoodsStatusEnum getGoodsStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		GoodsStatusEnum[] goodsStatusEnums = GoodsStatusEnum.values();
		List<GoodsStatusEnum> goodsStatusEnumList = ArrayTools.toList(goodsStatusEnums).stream().filter(goodsStatusEnum->code.equals(goodsStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(goodsStatusEnumList)){
			return null;
		}
		if (goodsStatusEnumList.size()>1) {
			throw new LogicException("E001", "商品状态不唯一,商品状态编码["+code+"]");
		}
		return goodsStatusEnumList.get(0);
	}
	
	/**
	 * 获取商品状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		GoodsStatusEnum goodsStatusEnum = GoodsStatusEnum.getGoodsStatusEnumByCode(code);
		if (goodsStatusEnum==null) {
			return null;
		}
		return goodsStatusEnum.getName();
	}

}
