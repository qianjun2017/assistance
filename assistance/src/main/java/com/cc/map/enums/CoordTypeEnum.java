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
public enum CoordTypeEnum {

	WGS84LL("wgs84ll", "GPS经纬度"),
	GCJ02LL("gcj02ll", "国测局经纬度坐标"),
	BD09LL("bd09ll", "百度经纬度坐标"),
	BD09MC("bd09mc", "百度米制坐标");
	
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
	private CoordTypeEnum(String code, String name) {
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
	 * 获取地点坐标类型
	 * @param code
	 * @return
	 */
	public static CoordTypeEnum getCoordTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		CoordTypeEnum[] coordTypeEnums = CoordTypeEnum.values();
		List<CoordTypeEnum> coordTypeEnumList = ArrayTools.toList(coordTypeEnums).stream().filter(coordTypeEnum->code.equals(coordTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(coordTypeEnumList)){
			return null;
		}
		if (coordTypeEnumList.size()>1) {
			throw new LogicException("E001", "地点坐标类型不唯一,地点坐标类型编码["+code+"]");
		}
		return coordTypeEnumList.get(0);
	}
	
	/**
	 * 获取地点坐标类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		CoordTypeEnum coordTypeEnum = CoordTypeEnum.getCoordTypeEnumByCode(code);
		if (coordTypeEnum==null) {
			return null;
		}
		return coordTypeEnum.getName();
	}
	
}
