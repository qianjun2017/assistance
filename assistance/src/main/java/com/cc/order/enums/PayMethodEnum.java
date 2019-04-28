/**
 * 
 */
package com.cc.order.enums;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
public enum PayMethodEnum {

	ALIPAY("alipay", "支付宝"),
	WECHAT("wechat", "微信"),
	UNIONPAY("unionpay", "银联"),
	CODPAY("codpay","货到付款");
	
	/**
	 * @param code
	 * @param name
	 */
	private PayMethodEnum(String code, String name) {
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
	 * 获取支付方式
	 * @param code
	 * @return
	 */
	public static PayMethodEnum getPayMethodEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		PayMethodEnum[] payMethodEnums = PayMethodEnum.values();
		List<PayMethodEnum> payMethodEnumList = ArrayTools.toList(payMethodEnums).stream().filter(payMethodEnum->code.equals(payMethodEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(payMethodEnumList)){
			return null;
		}
		if (payMethodEnumList.size()>1) {
			throw new LogicException("E001", "支付方式不唯一,支付方式编码["+code+"]");
		}
		return payMethodEnumList.get(0);
	}
	
	/**
	 * 获取支付方式说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		PayMethodEnum payMethodEnum = PayMethodEnum.getPayMethodEnumByCode(code);
		if (payMethodEnum==null) {
			return null;
		}
		return payMethodEnum.getName();
	}

}
