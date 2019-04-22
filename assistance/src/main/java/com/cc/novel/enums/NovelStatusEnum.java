/**
 * 
 */
package com.cc.novel.enums;

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
public enum NovelStatusEnum {

	DRAFT("draft", "未上架"),
	DOWN("down", "已下架"),
	ON("on","上架中");
	
	/**
	 * @param code
	 * @param name
	 */
	private NovelStatusEnum(String code, String name) {
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
	 * 获取小说状态
	 * @param code
	 * @return
	 */
	public static NovelStatusEnum getNovelStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		NovelStatusEnum[] novelStatusEnums = NovelStatusEnum.values();
		List<NovelStatusEnum> novelStatusEnumList = ArrayTools.toList(novelStatusEnums).stream().filter(novelStatusEnum->code.equals(novelStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(novelStatusEnumList)){
			return null;
		}
		if (novelStatusEnumList.size()>1) {
			throw new LogicException("E001", "小说状态不唯一,小说状态编码["+code+"]");
		}
		return novelStatusEnumList.get(0);
	}
	
	/**
	 * 获取小说状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		NovelStatusEnum novelStatusEnum = NovelStatusEnum.getNovelStatusEnumByCode(code);
		if (novelStatusEnum==null) {
			return null;
		}
		return novelStatusEnum.getName();
	}

}
