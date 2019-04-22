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
public enum NovelFinishStatusEnum {

	CLOSED("closed", "已完结"),
	SERIALIZING("serializing","连载中"),
	ABANDONED("abandoned","烂尾文");
	
	/**
	 * @param code
	 * @param name
	 */
	private NovelFinishStatusEnum(String code, String name) {
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
	 * 获取小说完结状态
	 * @param code
	 * @return
	 */
	public static NovelFinishStatusEnum getNovelFinishStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		NovelFinishStatusEnum[] novelFinishStatusEnums = NovelFinishStatusEnum.values();
		List<NovelFinishStatusEnum> novelFinishStatusEnumList = ArrayTools.toList(novelFinishStatusEnums).stream().filter(novelFinishStatusEnum->code.equals(novelFinishStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(novelFinishStatusEnumList)){
			return null;
		}
		if (novelFinishStatusEnumList.size()>1) {
			throw new LogicException("E001", "小说完结状态不唯一,小说完结状态编码["+code+"]");
		}
		return novelFinishStatusEnumList.get(0);
	}
	
	/**
	 * 获取小说完结状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		NovelFinishStatusEnum novelFinishStatusEnum = NovelFinishStatusEnum.getNovelFinishStatusEnumByCode(code);
		if (novelFinishStatusEnum==null) {
			return null;
		}
		return novelFinishStatusEnum.getName();
	}

}
