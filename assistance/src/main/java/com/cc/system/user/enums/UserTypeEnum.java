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
public enum UserTypeEnum {

	LEAGUER("leaguer", "会员"),
	USER("user", "系统用户"),
	SELLER("seller", "商家");

	/**
	 * @param code
	 * @param name
	 */
	private UserTypeEnum(String code, String name) {
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
	 * 获取用户类型
	 * @param code
	 * @return
	 */
	public static UserTypeEnum getUserTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		UserTypeEnum[] userTypeEnums = UserTypeEnum.values();
		List<UserTypeEnum> userTypeEnumList = ArrayTools.toList(userTypeEnums).stream().filter(userTypeEnum->code.equals(userTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(userTypeEnumList)){
			return null;
		}
		if (userTypeEnumList.size()>1) {
			throw new LogicException("E001", "用户类型不唯一,用户类型编码["+code+"]");
		}
		return userTypeEnumList.get(0);
	}
	
	/**
	 * 获取用户类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		UserTypeEnum userTypeEnum = UserTypeEnum.getUserTypeEnumByCode(code);
		if (userTypeEnum==null) {
			return null;
		}
		return userTypeEnum.getName();
	}

}
