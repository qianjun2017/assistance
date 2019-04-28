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
public enum TradeStateEnum {

	SUCCESS("SUCCESS", "支付成功"),
	REFUND("REFUND", "转入退款"),
	NOTPAY("NOTPAY", "未支付"),
	CLOSED("CLOSED", "已关闭"),
	REVOKED("REVOKED", "已撤销"),
	USERPAYING("USERPAYING", "用户支付中"),
	PAYERROR("PAYERROR", "支付失败");

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
	private TradeStateEnum(String code, String name) {
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
	 * 获取交易状态
	 * @param code
	 * @return
	 */
	public static TradeStateEnum getTradeStateEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		TradeStateEnum[] tradeStateEnums = TradeStateEnum.values();
		List<TradeStateEnum> tradeStateEnumList = ArrayTools.toList(tradeStateEnums).stream().filter(tradeStateEnum->code.equals(tradeStateEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(tradeStateEnumList)){
			return null;
		}
		if (tradeStateEnumList.size()>1) {
			throw new LogicException("E001", "交易状态不唯一,交易状态编码["+code+"]");
		}
		return tradeStateEnumList.get(0);
	}
	
	/**
	 * 获取交易状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		TradeStateEnum tradeStateEnum = TradeStateEnum.getTradeStateEnumByCode(code);
		if (tradeStateEnum==null) {
			return null;
		}
		return tradeStateEnum.getName();
	}
}
