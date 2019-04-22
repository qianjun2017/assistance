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
 * 积分值类型
 * @author Administrator
 *
 */
public enum IntegrationTypeEnum {
	
	FIXED("fixed","固定值"),
	EXTERFIXED("exter","外部参数指定"),
	RATIO("ratio","外部参数比值");

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
	private IntegrationTypeEnum(String code, String name) {
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
	 * 获取积分值类型
	 * @param code
	 * @return
	 */
	public static IntegrationTypeEnum getIntegrationTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		IntegrationTypeEnum[] integrationTypeEnums = IntegrationTypeEnum.values();
		List<IntegrationTypeEnum> integrationTypeEnumList = ArrayTools.toList(integrationTypeEnums).stream().filter(integrationTypeEnum->code.equals(integrationTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(integrationTypeEnumList)){
			return null;
		}
		if (integrationTypeEnumList.size()>1) {
			throw new LogicException("E001", "积分值类型不唯一,积分事件类型编码["+code+"]");
		}
		return integrationTypeEnumList.get(0);
	}
	
	/**
	 * 获取积分值类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		IntegrationTypeEnum integrationTypeEnum = IntegrationTypeEnum.getIntegrationTypeEnumByCode(code);
		if (integrationTypeEnum==null) {
			return null;
		}
		return integrationTypeEnum.getName();
	}
	
	/**
	 * 获取所有的积分值类型
	 * @return
	 */
	public static Map<String, String> getIntegrationTypeMap(){
		Map<String, String> integrationTypeMap = new HashMap<String,String>();
		IntegrationTypeEnum[] integrationTypeEnums = IntegrationTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(integrationTypeEnums)) {
			return integrationTypeMap;
		}
		for (IntegrationTypeEnum integrationTypeEnum : integrationTypeEnums) {
			integrationTypeMap.put(integrationTypeEnum.getCode(), integrationTypeEnum.getName());
		}
		return integrationTypeMap;
	}
}
