package com.cc.api.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

public enum ApiVersionEnum {

	V1("v1", "不加密访问"),
	V2("v2", "MD5签名访问"),
	V3("v3", "SHA1签名访问"),
	V4("v4", "RSA加密访问"),
	V5("v5", "DES加密访问"),
	V6("v6", "AES加密访问"),
	V7("v7", "IP限制访问");
	
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
	private ApiVersionEnum(String code, String name) {
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
	 * 获取接口访问版本
	 * @param code
	 * @return
	 */
	public static ApiVersionEnum getApiVersionEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ApiVersionEnum[] apiVersionEnums = ApiVersionEnum.values();
		List<ApiVersionEnum> apiVersionEnumList = ArrayTools.toList(apiVersionEnums).stream().filter(apiVersionEnum->code.equals(apiVersionEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(apiVersionEnumList)){
			return null;
		}
		if (apiVersionEnumList.size()>1) {
			throw new LogicException("E001", "接口访问版本不唯一,接口访问版本编码["+code+"]");
		}
		return apiVersionEnumList.get(0);
	}
	
	/**
	 * 获取接口访问版本说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ApiVersionEnum apiVersionEnum = ApiVersionEnum.getApiVersionEnumByCode(code);
		if (apiVersionEnum==null) {
			return null;
		}
		return apiVersionEnum.getName();
	}
}
