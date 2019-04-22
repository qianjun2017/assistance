/**
 * 
 */
package com.cc.exchange.enums;

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
public enum ExchangeStatusEnum {

	SUCCESS("success", "成功"),
	PENDING("pending", "取消待审核"),
	REJECT("reject","已驳回"),
	CANCELLED("cancelled","已取消");
	
	/**
	 * @param code
	 * @param name
	 */
	private ExchangeStatusEnum(String code, String name) {
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
	 * 获取兑换状态
	 * @param code
	 * @return
	 */
	public static ExchangeStatusEnum getExchangeStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ExchangeStatusEnum[] exchangeStatusEnums = ExchangeStatusEnum.values();
		List<ExchangeStatusEnum> exchangeStatusEnumList = ArrayTools.toList(exchangeStatusEnums).stream().filter(exchangeStatusEnum->code.equals(exchangeStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(exchangeStatusEnumList)){
			return null;
		}
		if (exchangeStatusEnumList.size()>1) {
			throw new LogicException("E001", "兑换状态不唯一,兑换状态编码["+code+"]");
		}
		return exchangeStatusEnumList.get(0);
	}
	
	/**
	 * 获取兑换状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ExchangeStatusEnum exchangeStatusEnum = ExchangeStatusEnum.getExchangeStatusEnumByCode(code);
		if (exchangeStatusEnum==null) {
			return null;
		}
		return exchangeStatusEnum.getName();
	}

}
