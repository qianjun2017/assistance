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
public enum OutputEnum {

	JSON("json", "json"),
	XML("xml", "xml");
	
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
	private OutputEnum(String code, String name) {
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
	 * 获取返回格式编码
	 * @param code
	 * @return
	 */
	public static OutputEnum getOutputEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		OutputEnum[] outputEnums = OutputEnum.values();
		List<OutputEnum> outputEnumList = ArrayTools.toList(outputEnums).stream().filter(outputEnum->code.equals(outputEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(outputEnumList)){
			return null;
		}
		if (outputEnumList.size()>1) {
			throw new LogicException("E001", "返回格式编码不唯一,返回格式编码["+code+"]");
		}
		return outputEnumList.get(0);
	}
	
	/**
	 * 获取返回格式说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		OutputEnum outputEnum = OutputEnum.getOutputEnumByCode(code);
		if (outputEnum==null) {
			return null;
		}
		return outputEnum.getName();
	}
	
}
