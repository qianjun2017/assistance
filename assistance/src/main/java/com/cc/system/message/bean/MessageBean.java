/**
 * 
 */
package com.cc.system.message.bean;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Administrator
 *
 */
@Table(name="t_system_message")
public class MessageBean extends BaseOrm<MessageBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6197023814240589098L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 消息类型
	 */
	private String type;
	
	/**
	 * 消息发送人
	 */
	private Long senderId;
	
	/**
	 * 消息接收人
	 */
	private Long receiverId;
	
	/**
	 * 消息阅读人
	 */
	private Long readerId;
	
	/**
	 * 消息接受权限
	 */
	private String receiverAuth;
	
	/**
	 * 消息内容
	 */
	private String message;
	
	/**
	 * 消息状态
	 */
	private String status;
	
	/**
	 * 频道
	 */
	private Long channelId;
	
	/**
	 * 链接
	 */
	private String path;
	
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the senderId
	 */
	public Long getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	/**
	 * @return the receiverId
	 */
	public Long getReceiverId() {
		return receiverId;
	}

	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	/**
	 * @return the receiverAuth
	 */
	public String getReceiverAuth() {
		return receiverAuth;
	}

	/**
	 * @param receiverAuth the receiverAuth to set
	 */
	public void setReceiverAuth(String receiverAuth) {
		this.receiverAuth = receiverAuth;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the channelId
	 */
	public Long getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the readerId
	 */
	public Long getReaderId() {
		return readerId;
	}

	/**
	 * @param readerId the readerId to set
	 */
	public void setReaderId(Long readerId) {
		this.readerId = readerId;
	}

}
