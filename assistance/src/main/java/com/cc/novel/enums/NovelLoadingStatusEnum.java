/**
 * 
 */
package com.cc.novel.enums;

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
public enum NovelLoadingStatusEnum {

	UNLOAD("unload", "未下载"),
	LOADING("loading", "下载中"),
	LOADED("loaded","已完成");

	/**
	 * @param code
	 * @param name
	 */
	private NovelLoadingStatusEnum(String code, String name) {
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
	 * 获取小说下载状态
	 * @param code
	 * @return
	 */
	public static NovelLoadingStatusEnum getNovelLoadingStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		NovelLoadingStatusEnum[] novelLoadingStatusEnums = NovelLoadingStatusEnum.values();
		List<NovelLoadingStatusEnum> novelLoadingStatusEnumList = ArrayTools.toList(novelLoadingStatusEnums).stream().filter(novelLoadingStatusEnum->code.equals(novelLoadingStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(novelLoadingStatusEnumList)){
			return null;
		}
		if (novelLoadingStatusEnumList.size()>1) {
			throw new LogicException("E001", "小说下载状态不唯一,小说下载状态编码["+code+"]");
		}
		return novelLoadingStatusEnumList.get(0);
	}
	
	/**
	 * 获取小说下载状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		NovelLoadingStatusEnum novelLoadingStatusEnum = NovelLoadingStatusEnum.getNovelLoadingStatusEnumByCode(code);
		if (novelLoadingStatusEnum==null) {
			return null;
		}
		return novelLoadingStatusEnum.getName();
	}

}
