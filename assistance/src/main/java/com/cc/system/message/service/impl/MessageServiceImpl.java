/**
 * 
 */
package com.cc.system.message.service.impl;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.system.message.dao.MessageDao;
import com.cc.system.message.form.MessageQueryForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.StringTools;
import com.cc.system.message.bean.MessageBean;
import com.cc.system.message.bean.MessageReadBean;
import com.cc.system.message.enums.MessageStatusEnum;
import com.cc.system.message.enums.MessageTypeEnum;
import com.cc.system.message.service.MessageService;

import java.util.List;

/**
 * @author Administrator
 *
 */
@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messageDao;

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void saveMessage(MessageBean messageBean) {
		int row = messageBean.save();
		if(row!=1){
			throw new LogicException("E001", "保存消息失败");
		}
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void releaseSystemMessage(Long receiverId, String receiverAuth, String message, String path) {
		MessageBean messageBean = new MessageBean();
		messageBean.setType(MessageTypeEnum.SYSTEM.getCode());
		if(receiverId == null && StringTools.isNullOrNone(receiverAuth)){
			throw new LogicException("E002", "请设置接收人或接受人权限");
		}
		messageBean.setReceiverAuth(receiverAuth);
		messageBean.setReceiverId(receiverId);
		messageBean.setStatus(MessageStatusEnum.UNREAD.getCode());
		messageBean.setCreateTime(DateTools.now());
		messageBean.setMessage(message);
		messageBean.setPath(path);
		this.saveMessage(messageBean);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void releaseSystemMessage(Long receiverId, String message, String path) {
		this.releaseSystemMessage(receiverId, null, message, path);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void releaseSystemMessage(String receiverAuth, String message, String path) {
		this.releaseSystemMessage(null, receiverAuth, message, path);
	}
	
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void releaseSystemMessage(Long receiverId, String message) {
		this.releaseSystemMessage(receiverId, message, null);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void releaseSystemMessage(String receiverAuth, String message) {
		this.releaseSystemMessage(receiverAuth, message, null);
	}

	@Override
	public Page<MessageBean> queryMessagePage(MessageQueryForm form) {
		Page<MessageBean> page = new Page<MessageBean>();
		PageHelper.orderBy(String.format("m.%s %s", form.getSort(), form.getOrder()));
		PageHelper.startPage(form.getPage(), form.getPageSize());
		List<MessageBean> messageList = messageDao.queryMessageList(form);
		PageInfo<MessageBean> pageInfo = new PageInfo<MessageBean>(messageList);
		if (ListTools.isEmptyOrNull(messageList)) {
			page.setMessage("没有查询到相关消息数据");
			return page;
		}
		page.setPage(pageInfo.getPageNum());
		page.setPages(pageInfo.getPages());
		page.setPageSize(pageInfo.getPageSize());
		page.setTotal(pageInfo.getTotal());
		page.setData(messageList);
		page.setSuccess(Boolean.TRUE);
		return page;
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void readMessage(Long userId, Long messageId) {
		MessageBean messageBean = MessageBean.get(MessageBean.class, messageId);
		if(messageBean==null){
			throw new LogicException("E001", "消息不存在");
		}
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.getMessageTypeEnumByCode(messageBean.getType());
		if (MessageTypeEnum.BROADCAST.equals(messageTypeEnum)) {
			List<MessageReadBean> messageReadBeanList = MessageReadBean.findAllByParams(MessageReadBean.class, "userId", userId, "messageId", messageBean.getId());
			if(ListTools.isEmptyOrNull(messageReadBeanList)){
				MessageReadBean messageReadBean = new MessageReadBean();
				messageReadBean.setMessageId(messageBean.getId());
				messageReadBean.setUserId(userId);
				messageReadBean.insert();
			}
		}else{
			messageBean.setStatus(MessageStatusEnum.READ.getCode());
			messageBean.setReaderId(userId);
			messageBean.update();
		}
	}

	/* (non-Javadoc)
	 * @see com.cc.system.message.service.MessageService#clearReadMessage(java.lang.Long)
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void clearReadMessage(Long userId) {
		Example example = new Example(MessageBean.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("readerId", userId);
		criteria.andEqualTo("status", MessageStatusEnum.READ.getCode());
		MessageBean.deleteByExample(MessageBean.class, example);
	}

	@Override
	@Transactional(rollbackFor = {Exception.class}, propagation = Propagation.REQUIRED)
	public void readAllMessage(MessageQueryForm form) {
		List<MessageBean> messageList = messageDao.queryMessageList(form);
		for (MessageBean messageBean : messageList) {
			this.readMessage(form.getReceiverId(), messageBean.getId());
		}
	}

}
