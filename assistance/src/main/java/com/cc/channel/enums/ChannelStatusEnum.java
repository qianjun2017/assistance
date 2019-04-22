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
public enum ChannelStatusEnum {

	DELETE("delete", "已删除"),
	NORMAL("normal", "正常"),
	CLOSE("close","已关闭");
	
	/**
	 * @param code
	 * @param name
	 */
	private ChannelStatusEnum(String code, String name) {
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
	 * 获取频道状态
	 * @param code
	 * @return
	 */
	public static ChannelStatusEnum getChannelStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ChannelStatusEnum[] channelStatusEnums = ChannelStatusEnum.values();
		List<ChannelStatusEnum> channelStatusEnumList = ArrayTools.toList(channelStatusEnums).stream().filter(channelStatusEnum->code.equals(channelStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(channelStatusEnumList)){
			return null;
		}
		if (channelStatusEnumList.size()>1) {
			throw new LogicException("E001", "频道状态不唯一,频道状态编码["+code+"]");
		}
		return channelStatusEnumList.get(0);
	}
	
	/**
	 * 获取频道状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ChannelStatusEnum channelStatusEnum = ChannelStatusEnum.getChannelStatusEnumByCode(code);
		if (channelStatusEnum==null) {
			return null;
		}
		return channelStatusEnum.getName();
	}

}
