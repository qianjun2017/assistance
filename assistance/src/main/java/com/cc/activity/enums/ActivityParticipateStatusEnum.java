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
public enum ActivityParticipateStatusEnum {

	WAIT("wait", "待审核"),
	PENDING("pending", "待定"),
	SUCCESS("success", "成功"),
	REJECT("reject","拒绝"),
	FAKE("fake","欺诈");
	
	/**
	 * @param code
	 * @param name
	 */
	private ActivityParticipateStatusEnum(String code, String name) {
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
	 * 获取活动参与状态
	 * @param code
	 * @return
	 */
	public static ActivityParticipateStatusEnum getActivityParticipateStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ActivityParticipateStatusEnum[] activityParticipateStatusEnums = ActivityParticipateStatusEnum.values();
		List<ActivityParticipateStatusEnum> activityParticipateStatusEnumList = ArrayTools.toList(activityParticipateStatusEnums).stream().filter(activityParticipateStatusEnum->code.equals(activityParticipateStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(activityParticipateStatusEnumList)){
			return null;
		}
		if (activityParticipateStatusEnumList.size()>1) {
			throw new LogicException("E001", "活动参与状态不唯一,活动参与状态编码["+code+"]");
		}
		return activityParticipateStatusEnumList.get(0);
	}
	
	/**
	 * 获取活动参与状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ActivityParticipateStatusEnum activityParticipateStatusEnum = ActivityParticipateStatusEnum.getActivityParticipateStatusEnumByCode(code);
		if (activityParticipateStatusEnum==null) {
			return null;
		}
		return activityParticipateStatusEnum.getName();
	}

}
