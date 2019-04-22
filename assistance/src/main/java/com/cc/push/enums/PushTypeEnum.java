/**
 * 
 */
package com.cc.push.enums;

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
public enum PushTypeEnum {
	
	ALL("all", "全量推送"),
	ASSIGN("assign", "指定推送");

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
	private PushTypeEnum(String code, String name) {
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
	 * 获取推送类型
	 * @param code
	 * @return
	 */
	public static PushTypeEnum getPushTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		PushTypeEnum[] pushTypeEnums = PushTypeEnum.values();
		List<PushTypeEnum> pushTypeEnumList = ArrayTools.toList(pushTypeEnums).stream().filter(pushTypeEnum->code.equals(pushTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(pushTypeEnumList)){
			return null;
		}
		if (pushTypeEnumList.size()>1) {
			throw new LogicException("E001", "推送类型不唯一,推送类型编码["+code+"]");
		}
		return pushTypeEnumList.get(0);
	}
	
	/**
	 * 获取推送类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		PushTypeEnum pushTypeEnum = PushTypeEnum.getPushTypeEnumByCode(code);
		if (pushTypeEnum==null) {
			return null;
		}
		return pushTypeEnum.getName();
	}
}
