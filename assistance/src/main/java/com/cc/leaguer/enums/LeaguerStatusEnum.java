/**
 * 
 */
package com.cc.leaguer.enums;

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
public enum LeaguerStatusEnum {
	
	NORMAL("normal", "正常"),
	LOCKED("locked", "锁定");

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
	private LeaguerStatusEnum(String code, String name) {
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
	 * 获取会员状态
	 * @param code
	 * @return
	 */
	public static LeaguerStatusEnum getLeaguerStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		LeaguerStatusEnum[] leaguerStatusEnums = LeaguerStatusEnum.values();
		List<LeaguerStatusEnum> leaguerStatusEnumList = ArrayTools.toList(leaguerStatusEnums).stream().filter(leaguerStatusEnum->code.equals(leaguerStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(leaguerStatusEnumList)){
			return null;
		}
		if (leaguerStatusEnumList.size()>1) {
			throw new LogicException("E001", "会员状态不唯一,会员状态编码["+code+"]");
		}
		return leaguerStatusEnumList.get(0);
	}
	
	/**
	 * 获取会员状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		LeaguerStatusEnum leaguerStatusEnum = LeaguerStatusEnum.getLeaguerStatusEnumByCode(code);
		if (leaguerStatusEnum==null) {
			return null;
		}
		return leaguerStatusEnum.getName();
	}
}
