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
 * 专题状态
 * @author Administrator
 *
 */
public enum ChannelSubjectStatusEnum {

	DRAFT("draft", "未上架"),
	DOWN("down", "已下架"),
	ON("on","上架中");
	
	/**
	 * @param code
	 * @param name
	 */
	private ChannelSubjectStatusEnum(String code, String name) {
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
	 * 获取专题状态
	 * @param code
	 * @return
	 */
	public static ChannelSubjectStatusEnum getChannelSubjectStatusEnumByCode(String code){
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		ChannelSubjectStatusEnum[] channelSubjectStatusEnums = ChannelSubjectStatusEnum.values();
		List<ChannelSubjectStatusEnum> channelSubjectStatusEnumList = ArrayTools.toList(channelSubjectStatusEnums).stream().filter(channelSubjectStatusEnum->code.equals(channelSubjectStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(channelSubjectStatusEnumList)){
			return null;
		}
		if (channelSubjectStatusEnumList.size()>1) {
			throw new LogicException("E001", "专题状态不唯一,专题状态编码["+code+"]");
		}
		return channelSubjectStatusEnumList.get(0);
	}
	
	/**
	 * 获取专题状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		ChannelSubjectStatusEnum channelSubjectStatusEnum = ChannelSubjectStatusEnum.getChannelSubjectStatusEnumByCode(code);
		if (channelSubjectStatusEnum==null) {
			return null;
		}
		return channelSubjectStatusEnum.getName();
	}

}
