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
 * 积分状态
 * @author Administrator
 *
 */
public enum IntegrationEventStatusEnum {
	
	ON("on","已启用"),
	OFF("off","已下线");

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
	private IntegrationEventStatusEnum(String code, String name) {
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
	 * 获取积分事件状态
	 * @param code
	 * @return
	 */
	public static IntegrationEventStatusEnum getIntegrationEventStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		IntegrationEventStatusEnum[] integrationEventStatusEnums = IntegrationEventStatusEnum.values();
		List<IntegrationEventStatusEnum> integrationEventStatusEnumList = ArrayTools.toList(integrationEventStatusEnums).stream().filter(integrationEventStatusEnum->code.equals(integrationEventStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(integrationEventStatusEnumList)){
			return null;
		}
		if (integrationEventStatusEnumList.size()>1) {
			throw new LogicException("E001", "积分事件状态不唯一,积分事件状态编码["+code+"]");
		}
		return integrationEventStatusEnumList.get(0);
	}
	
	/**
	 * 获取事件状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		IntegrationEventStatusEnum integrationEventStatusEnum = IntegrationEventStatusEnum.getIntegrationEventStatusEnumByCode(code);
		if (integrationEventStatusEnum==null) {
			return null;
		}
		return integrationEventStatusEnum.getName();
	}
	
	/**
	 * 获取所有的事件状态
	 * @return
	 */
	public static Map<String, String> getIntegrationEventStatusMap(){
		Map<String, String> integrationEventStatusMap = new HashMap<String,String>();
		IntegrationEventStatusEnum[] integrationEventStatusEnums = IntegrationEventStatusEnum.values();
		if (ArrayTools.isEmptyOrNull(integrationEventStatusEnums)) {
			return integrationEventStatusMap;
		}
		for (IntegrationEventStatusEnum integrationEventStatusEnum : integrationEventStatusEnums) {
			integrationEventStatusMap.put(integrationEventStatusEnum.getCode(), integrationEventStatusEnum.getName());
		}
		return integrationEventStatusMap;
	}
}
