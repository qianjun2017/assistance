/**
 * 
 */
package com.cc.integration.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 积分事件类型
 * @author Administrator
 *
 */
public enum IntegrationEventTypeEnum {
	
	LOGIN("login","登录"),
	CHECKIN("checkin","签到"),
	REGISTER("register","注册"),
	EXCHANGE("exchange","兑换"),
	ACTIVITY("activity","参与活动"),
	DEPOSIT("deposit","充值"),
	BUY("buy","购买"),
	PARTBUY("partBuy","部分支付");

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
	private IntegrationEventTypeEnum(String code, String name) {
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
	 * 获取积分事件类型
	 * @param code
	 * @return
	 */
	public static IntegrationEventTypeEnum getIntegrationEventTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		IntegrationEventTypeEnum[] integrationEventTypeEnums = IntegrationEventTypeEnum.values();
		List<IntegrationEventTypeEnum> integrationEventTypeEnumList = ArrayTools.toList(integrationEventTypeEnums).stream().filter(integrationEventTypeEnum->code.equals(integrationEventTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(integrationEventTypeEnumList)){
			return null;
		}
		if (integrationEventTypeEnumList.size()>1) {
			throw new LogicException("E001", "积分事件类型不唯一,积分事件类型编码["+code+"]");
		}
		return integrationEventTypeEnumList.get(0);
	}
	
	/**
	 * 获取事件类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		IntegrationEventTypeEnum integrationEventTypeEnum = IntegrationEventTypeEnum.getIntegrationEventTypeEnumByCode(code);
		if (integrationEventTypeEnum==null) {
			return null;
		}
		return integrationEventTypeEnum.getName();
	}
	
	/**
	 * 获取所有的事件类型
	 * @return
	 */
	public static Map<String, String> getIntegrationEventTypeMap(){
		Map<String, String> integrationEventTypeMap = new HashMap<String,String>();
		IntegrationEventTypeEnum[] integrationEventTypeEnums = IntegrationEventTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(integrationEventTypeEnums)) {
			return integrationEventTypeMap;
		}
		for (IntegrationEventTypeEnum integrationEventTypeEnum : integrationEventTypeEnums) {
			integrationEventTypeMap.put(integrationEventTypeEnum.getCode(), integrationEventTypeEnum.getName());
		}
		return integrationEventTypeMap;
	}
}
