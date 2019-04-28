/**
 * 
 */
package com.cc.system.user.enums;

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
public enum UserStatusEnum {

	NORMAL("normal", "正常"),
	LOCKED("locked", "锁定");

	/**
	 * @param code
	 * @param name
	 */
	private UserStatusEnum(String code, String name) {
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
	 * 获取用户状态
	 * @param code
	 * @return
	 */
	public static UserStatusEnum getUserStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		UserStatusEnum[] userStatusEnums = UserStatusEnum.values();
		List<UserStatusEnum> userStatusEnumList = ArrayTools.toList(userStatusEnums).stream().filter(userStatusEnum->code.equals(userStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(userStatusEnumList)){
			return null;
		}
		if (userStatusEnumList.size()>1) {
			throw new LogicException("E001", "用户状态不唯一,用户状态编码["+code+"]");
		}
		return userStatusEnumList.get(0);
	}
	
	/**
	 * 获取用户状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		UserStatusEnum userStatusEnum = UserStatusEnum.getUserStatusEnumByCode(code);
		if (userStatusEnum==null) {
			return null;
		}
		return userStatusEnum.getName();
	}

}
