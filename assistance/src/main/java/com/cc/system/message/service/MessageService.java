/**
 * 
 */
package com.cc.system.message.service;

import com.cc.common.web.Page;
import com.cc.system.message.bean.MessageBean;
import com.cc.system.message.form.MessageQueryForm;

/**
 * @author Administrator
 *
 */
public interface MessageService {

	/**
	 * 保存消息
	 * @param messageBean
	 */
	void saveMessage(MessageBean messageBean);
	
	/**
	 * 发布系统消息
	 * @param receiverId
	 * @param receiverAuth
	 * @param message
	 * @param path
	 */
	void releaseSystemMessage(Long receiverId, String receiverAuth, String message, String path);
	
	/**
	 * 发布系统消息
	 * @param receiverId
	 * @param message
	 * @param path
	 */
	void releaseSystemMessage(Long receiverId, String message, String path);
	
	/**
	 * 发布系统消息
	 * @param receiverAuth
	 * @param message
	 * @param path
	 */
	void releaseSystemMessage(String receiverAuth, String message, String path);
	
	/**
	 * 发布系统消息
	 * @param receiverAuth
	 * @param message
	 */
	void releaseSystemMessage(String receiverAuth, String message);
	
	/**
	 * 发布系统消息
	 * @param receiverId
	 * @param message
	 */
	void releaseSystemMessage(Long receiverId, String message);

	/**
	 * 分页查询消息
	 * @param form
	 * @return
	 */
	Page<MessageBean> queryMessagePage(MessageQueryForm form);
	
	/**
	 * 阅读消息
	 * @param userId
	 * @param messageId
	 */
	void readMessage(Long userId, Long messageId);
	
	/**
	 * 清空已读消息
	 * @param userId
	 */
	void clearReadMessage(Long userId);

	/**
	 * 全部标记为已读
	 * @param form
	 */
	void readAllMessage(MessageQueryForm form);
}
