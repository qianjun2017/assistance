/**
 * 
 */
package com.cc.activity.enums;

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
public enum ActivityStatusEnum {

	DRAFT("draft", "未上架"),
	DOWN("down", "已下架"),
	ON("on","上架中");
	
	/**
	 * @param code
	 * @param name
	 */
	private ActivityStatusEnum(String code, String name) {
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
	 * 获取活动状态
	 * @param code
	 * @return
	 */
	public static ActivityStatusEnum getActivityStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ActivityStatusEnum[] activityStatusEnums = ActivityStatusEnum.values();
		List<ActivityStatusEnum> activityStatusEnumList = ArrayTools.toList(activityStatusEnums).stream().filter(activityStatusEnum->code.equals(activityStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(activityStatusEnumList)){
			return null;
		}
		if (activityStatusEnumList.size()>1) {
			throw new LogicException("E001", "活动状态不唯一,活动状态编码["+code+"]");
		}
		return activityStatusEnumList.get(0);
	}
	
	/**
	 * 获取活动状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ActivityStatusEnum activityStatusEnum = ActivityStatusEnum.getActivityStatusEnumByCode(code);
		if (activityStatusEnum==null) {
			return null;
		}
		return activityStatusEnum.getName();
	}

}
