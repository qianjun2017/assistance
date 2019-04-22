/**
 * 
 */
package com.cc.map.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * @author ws_yu
 *
 */
public enum SearchTypeEnum {

	REGION("region", "行政区划区域检索"),
	RADIUS("radius", "圆形区域检索"),
	BOUNDS("bounds", "矩形区域检索"),
	DETAIL("detail", "地点详情检索");
	
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
	private SearchTypeEnum(String code, String name) {
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
	 * 获取地点检索类型
	 * @param code
	 * @return
	 */
	public static SearchTypeEnum getSearchTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		SearchTypeEnum[] searchTypeEnums = SearchTypeEnum.values();
		List<SearchTypeEnum> searchTypeEnumList = ArrayTools.toList(searchTypeEnums).stream().filter(searchTypeEnum->code.equals(searchTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(searchTypeEnumList)){
			return null;
		}
		if (searchTypeEnumList.size()>1) {
			throw new LogicException("E001", "地点检索类型不唯一,地点检索类型编码["+code+"]");
		}
		return searchTypeEnumList.get(0);
	}
	
	/**
	 * 获取地点检索类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.getSearchTypeEnumByCode(code);
		if (searchTypeEnum==null) {
			return null;
		}
		return searchTypeEnum.getName();
	}
	
}
