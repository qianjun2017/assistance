package com.cc.sms.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

public enum SmsProviderEnum {

	TENXUN("tenxun", "腾讯云短信服务");
	
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
	private SmsProviderEnum(String code, String name) {
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
	 * 获取短信服务供应商
	 * @param code
	 * @return
	 */
	public static SmsProviderEnum getSmsProviderEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		SmsProviderEnum[] smsProviderEnums = SmsProviderEnum.values();
		List<SmsProviderEnum> smsProviderEnumList = ArrayTools.toList(smsProviderEnums).stream().filter(smsProviderEnum->code.equals(smsProviderEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(smsProviderEnumList)){
			return null;
		}
		if (smsProviderEnumList.size()>1) {
			throw new LogicException("E001", "短信服务供应商不唯一,短信服务供应商编码["+code+"]");
		}
		return smsProviderEnumList.get(0);
	}
	
	/**
	 * 获取短信服务供应商说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		SmsProviderEnum smsProviderEnum = SmsProviderEnum.getSmsProviderEnumByCode(code);
		if (smsProviderEnum==null) {
			return null;
		}
		return smsProviderEnum.getName();
	}
}
