/**
 * 
 */
package com.cc.channel.enums;

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
public enum ChannelTypeEnum {

	ORDINARY("ordinary", "普通"),
	COMPREHENSIVE("comprehensive","综合");
	
	/**
	 * @param code
	 * @param name
	 */
	private ChannelTypeEnum(String code, String name) {
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
	 * 获取频道类型
	 * @param code
	 * @return
	 */
	public static ChannelTypeEnum getChannelTypeEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ChannelTypeEnum[] channelTypeEnums = ChannelTypeEnum.values();
		List<ChannelTypeEnum> channelTypeEnumList = ArrayTools.toList(channelTypeEnums).stream().filter(channelTypeEnum->code.equals(channelTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(channelTypeEnumList)){
			return null;
		}
		if (channelTypeEnumList.size()>1) {
			throw new LogicException("E001", "频道类型不唯一,频道类型编码["+code+"]");
		}
		return channelTypeEnumList.get(0);
	}
	
	/**
	 * 获取频道类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ChannelTypeEnum channelTypeEnum = ChannelTypeEnum.getChannelTypeEnumByCode(code);
		if (channelTypeEnum==null) {
			return null;
		}
		return channelTypeEnum.getName();
	}

}
