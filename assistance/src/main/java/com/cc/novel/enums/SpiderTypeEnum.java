/**
 * 
 */
package com.cc.novel.enums;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
public enum SpiderTypeEnum {

	BASE("base", "小说基本信息"),
	CHAPTER("chapter", "小说章节信息");

	/**
	 * @param code
	 * @param name
	 */
	private SpiderTypeEnum(String code, String name) {
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
	 * 获取爬虫类型
	 * @param code
	 * @return
	 */
	public static SpiderTypeEnum getSpiderTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		SpiderTypeEnum[] spiderTypeEnums = SpiderTypeEnum.values();
		List<SpiderTypeEnum> spiderTypeEnumList = ArrayTools.toList(spiderTypeEnums).stream().filter(spiderTypeEnum->code.equals(spiderTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(spiderTypeEnumList)){
			return null;
		}
		if (spiderTypeEnumList.size()>1) {
			throw new LogicException("E001", "爬虫类型不唯一,爬虫类型编码["+code+"]");
		}
		return spiderTypeEnumList.get(0);
	}
	
	/**
	 * 获取爬虫类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		SpiderTypeEnum spiderTypeEnum = SpiderTypeEnum.getSpiderTypeEnumByCode(code);
		if (spiderTypeEnum==null) {
			return null;
		}
		return spiderTypeEnum.getName();
	}

	/**
	 * 获取所有的爬虫类型
	 * @return
	 */
	public static Map<String, String> getSpiderTypeMap(){
		Map<String, String> spiderTypeMap = new HashMap<String,String>();
		SpiderTypeEnum[] spiderTypeEnums = SpiderTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(spiderTypeEnums)) {
			return spiderTypeMap;
		}
		for (SpiderTypeEnum spiderTypeEnum : spiderTypeEnums) {
			spiderTypeMap.put(spiderTypeEnum.getCode(), spiderTypeEnum.getName());
		}
		return spiderTypeMap;
	}

}
