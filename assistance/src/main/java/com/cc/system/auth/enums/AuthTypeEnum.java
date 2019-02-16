/**
 * 
 */
package com.cc.system.auth.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 权限类型
 * @author Administrator
 *
 */
public enum AuthTypeEnum {

	MENU("menu", "菜单权限"),
	OPERATION("oper", "功能权限");
	
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
	private AuthTypeEnum(String code, String name) {
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
	 * 获取权限类型
	 * @param code
	 * @return
	 */
	public static AuthTypeEnum getAuthTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		AuthTypeEnum[] authTypeEnums = AuthTypeEnum.values();
		List<AuthTypeEnum> authTypeEnumList = ArrayTools.toList(authTypeEnums).stream().filter(authTypeEnum->code.equals(authTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(authTypeEnumList)){
			return null;
		}
		if (authTypeEnumList.size()>1) {
			throw new LogicException("E001", "权限类型不唯一,权限类型编码["+code+"]");
		}
		return authTypeEnumList.get(0);
	}
	
	/**
	 * 获取权限类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		AuthTypeEnum authTypeEnum = AuthTypeEnum.getAuthTypeEnumByCode(code);
		if (authTypeEnum==null) {
			return null;
		}
		return authTypeEnum.getName();
	}
}
