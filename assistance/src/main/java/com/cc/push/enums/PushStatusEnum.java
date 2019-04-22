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
public enum PushStatusEnum {
	
	UNDO("undo", "未推送"),
	DOING("doing", "推送中"),
	DONE("done", "已推送");

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
	private PushStatusEnum(String code, String name) {
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
	 * 获取推送状态
	 * @param code
	 * @return
	 */
	public static PushStatusEnum getPushStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		PushStatusEnum[] pushStatusEnums = PushStatusEnum.values();
		List<PushStatusEnum> pushStatusEnumList = ArrayTools.toList(pushStatusEnums).stream().filter(pushStatusEnum->code.equals(pushStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(pushStatusEnumList)){
			return null;
		}
		if (pushStatusEnumList.size()>1) {
			throw new LogicException("E001", "推送状态不唯一,推送状态编码["+code+"]");
		}
		return pushStatusEnumList.get(0);
	}
	
	/**
	 * 获取推送状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		PushStatusEnum pushStatusEnum = PushStatusEnum.getPushStatusEnumByCode(code);
		if (pushStatusEnum==null) {
			return null;
		}
		return pushStatusEnum.getName();
	}
}
