/**
 * 
 */
package com.cc.lottery.enums;

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
public enum LotteryLeaguerStatusEnum {
	
	TOBEEXCHANGE("toBeExchange", "未兑奖"),
	EXCHANGED("exchanged", "已兑奖"),
	EXPIRED("expired", "已过期");

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
	private LotteryLeaguerStatusEnum(String code, String name) {
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
	 * 获取兑奖状态
	 * @param code
	 * @return
	 */
	public static LotteryLeaguerStatusEnum getLotteryLeaguerStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		LotteryLeaguerStatusEnum[] lotteryLeaguerStatusEnums = LotteryLeaguerStatusEnum.values();
		List<LotteryLeaguerStatusEnum> lotteryLeaguerStatusEnumList = ArrayTools.toList(lotteryLeaguerStatusEnums).stream().filter(lotteryLeaguerStatusEnum->code.equals(lotteryLeaguerStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(lotteryLeaguerStatusEnumList)){
			return null;
		}
		if (lotteryLeaguerStatusEnumList.size()>1) {
			throw new LogicException("E001", "兑奖状态不唯一,兑奖状态编码["+code+"]");
		}
		return lotteryLeaguerStatusEnumList.get(0);
	}
	
	/**
	 * 获取兑奖状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		LotteryLeaguerStatusEnum lotteryLeaguerStatusEnum = LotteryLeaguerStatusEnum.getLotteryLeaguerStatusEnumByCode(code);
		if (lotteryLeaguerStatusEnum==null) {
			return null;
		}
		return lotteryLeaguerStatusEnum.getName();
	}
}
