/**
 * 
 */
package com.cc.interfaces.enums;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 *
 */
public enum InterfaceEnum {

	WXUNIFIEDORDER("wxUnifiedOrder", "发送", "微信统一下单接口"),
	WXUNIFIEDORDERNOTIFY("wxUnifiedOrderNotify", "接收", "微信支付结果通知"),
	WXREFUNDORDER("wxRefundOrder", "发送", "微信申请退款接口"),
	WXREFUNDORDERNOTIFY("wxRefundOrderNotify", "接收", "微信退款结果通知"),
	WXQUERYORDER("wxQueryOrder", "发送", "微信查询订单接口"),
	WXCLOSEORDER("wxCloseOrder", "发送", "微信关闭订单接口"),
	WXQUERYREFUNDORDER("wxQueryRefundOrder", "发送", "微信查询退款接口");

	/**
	 * 编码
	 */
	private String code;

	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 说明
	 */
	private String name;

	/**
	 * @param code
	 * @param type
	 * @param name
	 */
	private InterfaceEnum(String code, String type, String name) {
		this.code = code;
		this.type = type;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取接口
	 * @param code
	 * @return
	 */
	public static InterfaceEnum getInterfaceEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		InterfaceEnum[] interfaceEnums = InterfaceEnum.values();
		List<InterfaceEnum> interfaceEnumList = ArrayTools.toList(interfaceEnums).stream().filter(interfaceEnum->code.equals(interfaceEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(interfaceEnumList)){
			return null;
		}
		if (interfaceEnumList.size()>1) {
			throw new LogicException("E001", "接口不唯一,接口编码["+code+"]");
		}
		return interfaceEnumList.get(0);
	}
	
	/**
	 * 获取接口说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		InterfaceEnum interfaceEnum = InterfaceEnum.getInterfaceEnumByCode(code);
		if (interfaceEnum==null) {
			return null;
		}
		return interfaceEnum.getName();
	}
	
	/**
	 * 获取接口类型
	 * @param code
	 * @return
	 */
	public static String getTypeByCode(String code){
		InterfaceEnum interfaceEnum = InterfaceEnum.getInterfaceEnumByCode(code);
		if (interfaceEnum==null) {
			return null;
		}
		return interfaceEnum.getType();
	}
	
	/**
	 * 查询接口数据
	 * @return
	 */
	public static Map<String, String> getInterfaceMap(){
		Map<String, String> interfaceMap = new HashMap<String,String>();
		InterfaceEnum[] interfaceEnums = InterfaceEnum.values();
		if (ArrayTools.isEmptyOrNull(interfaceEnums)) {
			return interfaceMap;
		}
		for (InterfaceEnum interfaceEnum : interfaceEnums) {
			interfaceMap.put(interfaceEnum.getCode(), interfaceEnum.getName());
		}
		return interfaceMap;
	}
}
