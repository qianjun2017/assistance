/**
 * 
 */
package com.cc.system.log.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 操作模块
 * @author Administrator
 *
 */
public enum ModuleEnum {

	USERMANAGEMENT("user", "用户管理"),
	ROLEMANAGEMENT("role", "角色管理"),
	AUTHMANAGEMENT("auth", "权限管理"),
	LOGINMANAGEMENT("shrio", "登陆管理"),
	LOCATIONMANAGEMENT("location", "地区管理"),
	CAROUSELMANAGEMENT("carousel", "轮播图管理"),
	CUSTOMERMANAGEMENT("customer", "客户管理");
	
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
	private ModuleEnum(String code, String name) {
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
	 * 获取所有的日志模块
	 * @return
	 */
	public static Map<String, String> getModuleMap(){
		Map<String, String> moduleMap = new HashMap<String,String>();
		ModuleEnum[] moduleEnums = ModuleEnum.values();
		if (ArrayTools.isEmptyOrNull(moduleEnums)) {
			return moduleMap;
		}
		for (ModuleEnum moduleEnum : moduleEnums) {
			moduleMap.put(moduleEnum.getCode(), moduleEnum.getName());
		}
		return moduleMap;
	}
	
	/**
	 * 获取日志模块
	 * @param code
	 * @return
	 */
	public static ModuleEnum getModuleEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ModuleEnum[] moduleEnums = ModuleEnum.values();
		List<ModuleEnum> moduleEnumList = ArrayTools.toList(moduleEnums).stream().filter(moduleEnum->code.equals(moduleEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(moduleEnumList)){
			return null;
		}
		if (moduleEnumList.size()>1) {
			throw new LogicException("E001", "操作日志模块不唯一,操作日志模块编码["+code+"]");
		}
		return moduleEnumList.get(0);
	}
	
	/**
	 * 获取操作模块说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ModuleEnum moduleEnum = ModuleEnum.getModuleEnumByCode(code);
		if (moduleEnum==null) {
			return null;
		}
		return moduleEnum.getName();
	}
}
