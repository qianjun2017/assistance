/**
 * 
 */
package com.cc.system.message.bean;

import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_system_message_read")
public class MessageReadBean extends BaseOrm<MessageReadBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6353675351030374465L;

	/**
	 * 查看人
	 */
	private Long userId;
	
	/**
	 * 消息
	 */
	private Long messageId;
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Object getId() {
		return null;
	}
	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * @return the messageId
	 */
	public Long getMessageId() {
		return messageId;
	}
	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

}
