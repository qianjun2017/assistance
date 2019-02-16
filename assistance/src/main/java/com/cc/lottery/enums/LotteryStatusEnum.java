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
public enum LotteryStatusEnum {
	
	NORMAL("normal", "正常"),
	OVER("over", "结束");

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
	private LotteryStatusEnum(String code, String name) {
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
	 * 获取抽奖状态
	 * @param code
	 * @return
	 */
	public static LotteryStatusEnum getLotteryStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		LotteryStatusEnum[] lotteryStatusEnums = LotteryStatusEnum.values();
		List<LotteryStatusEnum> lotteryStatusEnumList = ArrayTools.toList(lotteryStatusEnums).stream().filter(lotteryStatusEnum->code.equals(lotteryStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(lotteryStatusEnumList)){
			return null;
		}
		if (lotteryStatusEnumList.size()>1) {
			throw new LogicException("E001", "抽奖状态不唯一,抽奖状态编码["+code+"]");
		}
		return lotteryStatusEnumList.get(0);
	}
	
	/**
	 * 获取抽奖状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		LotteryStatusEnum lotteryStatusEnum = LotteryStatusEnum.getLotteryStatusEnumByCode(code);
		if (lotteryStatusEnum==null) {
			return null;
		}
		return lotteryStatusEnum.getName();
	}
}
