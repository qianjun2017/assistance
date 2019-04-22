/**
 * 
 */
package com.cc.system.message.enums;

import java.util.List;
import java.util.stream.Collectors;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ArrayTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 消息状态
 * @author Administrator
 *
 */
public enum MessageStatusEnum {

	READ("read", "已读"),
	UNREAD("unread", "未读");
	
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
	private MessageStatusEnum(String code, String name) {
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
	 * 获取消息状态
	 * @param code
	 * @return
	 */
	public static MessageStatusEnum getMessageStatusEnumByCode(String code) {
		if (StringTools.isNullOrNone(code)) {
			return null;
		}
		MessageStatusEnum[] messageStatusEnums = MessageStatusEnum.values();
		List<MessageStatusEnum> messageStatusEnumList = ArrayTools.toList(messageStatusEnums).stream().filter(messageStatusEnum->code.equals(messageStatusEnum.getCode())).collect(Collectors.toList());
		if(ListTools.isEmptyOrNull(messageStatusEnumList)){
			return null;
		}
		if (messageStatusEnumList.size()>1) {
			throw new LogicException("E001", "消息状态不唯一,消息状态编码["+code+"]");
		}
		return messageStatusEnumList.get(0);
	}
	
	/**
	 * 获取消息状态说明
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code){
		MessageStatusEnum messageStatusEnum = MessageStatusEnum.getMessageStatusEnumByCode(code);
		if (messageStatusEnum==null) {
			return null;
		}
		return messageStatusEnum.getName();
	}
}
