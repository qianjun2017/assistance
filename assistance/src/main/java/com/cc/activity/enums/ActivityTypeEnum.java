/**
 * 
 */
package com.cc.activity.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public enum ActivityTypeEnum {

	SCAN("scan", "扫一扫"),
	APP("app", "APP推广");
	
	/**
	 * @param code
	 * @param name
	 */
	private ActivityTypeEnum(String code, String name) {
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
	 * 获取活动类型
	 * @param code
	 * @return
	 */
	public static ActivityTypeEnum getActivityTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ActivityTypeEnum[] activityTypeEnums = ActivityTypeEnum.values();
		List<ActivityTypeEnum> activityTypeEnumList = ArrayTools.toList(activityTypeEnums).stream().filter(activityTypeEnum->code.equals(activityTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(activityTypeEnumList)){
			return null;
		}
		if (activityTypeEnumList.size()>1) {
			throw new LogicException("E001", "活动类型不唯一,活动类型编码["+code+"]");
		}
		return activityTypeEnumList.get(0);
	}
	
	/**
	 * 获取活动类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ActivityTypeEnum activityTypeEnum = ActivityTypeEnum.getActivityTypeEnumByCode(code);
		if (activityTypeEnum==null) {
			return null;
		}
		return activityTypeEnum.getName();
	}

	/**
	 * 获取所有的活动类型
	 * @return
	 */
	public static Map<String, String> getActivityTypeMap(){
		Map<String, String> typeMap = new HashMap<String,String>();
		ActivityTypeEnum[] activityTypeEnums = ActivityTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(activityTypeEnums)) {
			return typeMap;
		}
		for (ActivityTypeEnum activityTypeEnum : activityTypeEnums) {
			typeMap.put(activityTypeEnum.getCode(), activityTypeEnum.getName());
		}
		return typeMap;
	}
}
