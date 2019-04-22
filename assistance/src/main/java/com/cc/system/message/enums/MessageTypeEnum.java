/**
 * 
 */
package com.cc.system.message.enums;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 消息类型
 * @author Administrator
 *
 */
public enum MessageTypeEnum {

	SYSTEM("system", "系统消息"),
	BROADCAST("broadcast", "广播"),
	MESSAGE("message", "普通消息");
	
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
	private MessageTypeEnum(String code, String name) {
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
	 * 获取消息类型
	 * @param code
	 * @return
	 */
	public static MessageTypeEnum getMessageTypeEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		MessageTypeEnum[] messageTypeEnums = MessageTypeEnum.values();
		List<MessageTypeEnum> messageTypeEnumList = ArrayTools.toList(messageTypeEnums).stream().filter(messageTypeEnum->code.equals(messageTypeEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(messageTypeEnumList)){
			return null;
		}
		if (messageTypeEnumList.size()>1) {
			throw new LogicException("E001", "消息类型不唯一,消息类型编码["+code+"]");
		}
		return messageTypeEnumList.get(0);
	}
	
	/**
	 * 获取消息类型说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.getMessageTypeEnumByCode(code);
		if (messageTypeEnum==null) {
			return null;
		}
		return messageTypeEnum.getName();
	}

	/**
	 * 获取所有的消息类型
	 * @return
	 */
	public static Map<String, String> getMessageTypeMap(){
		Map<String, String> messageTypeMap = new HashMap<String,String>();
		MessageTypeEnum[] messageTypeEnums = MessageTypeEnum.values();
		if (ArrayTools.isEmptyOrNull(messageTypeEnums)) {
			return messageTypeMap;
		}
		for (MessageTypeEnum messageTypeEnum : messageTypeEnums) {
			messageTypeMap.put(messageTypeEnum.getCode(), messageTypeEnum.getName());
		}
		return messageTypeMap;
	}
}
