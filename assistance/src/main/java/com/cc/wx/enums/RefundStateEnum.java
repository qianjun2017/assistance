/**
 * 
 */
package com.cc.wx.enums;

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
public enum RefundStateEnum {

	SUCCESS("SUCCESS", "退款成功"),
	REFUNDCLOSE("REFUNDCLOSE", "退款关闭"),
	PROCESSING("PROCESSING", "退款处理中"),
	CHANGE("CHANGE", "退款异常");

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
	private RefundStateEnum(String code, String name) {
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
	 * 获取退款状态
	 * @param code
	 * @return
	 */
	public static RefundStateEnum getRefundStateEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		RefundStateEnum[] refundStateEnums = RefundStateEnum.values();
		List<RefundStateEnum> refundStateEnumList = ArrayTools.toList(refundStateEnums).stream().filter(refundStateEnum->code.equals(refundStateEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(refundStateEnumList)){
			return null;
		}
		if (refundStateEnumList.size()>1) {
			throw new LogicException("E001", "退款状态不唯一,退款状态编码["+code+"]");
		}
		return refundStateEnumList.get(0);
	}
	
	/**
	 * 获取退款状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		RefundStateEnum refundStateEnum = RefundStateEnum.getRefundStateEnumByCode(code);
		if (refundStateEnum==null) {
			return null;
		}
		return refundStateEnum.getName();
	}
}
