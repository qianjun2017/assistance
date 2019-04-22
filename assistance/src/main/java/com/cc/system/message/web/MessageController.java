/**
 * 
 */
package com.cc.system.message.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cc.common.tools.ListTools;
import com.cc.common.web.Page;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.service.LeaguerChannelService;
import com.cc.system.message.form.MessageQueryForm;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.enums.ChannelStatusEnum;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.Response;
import com.cc.system.log.annotation.OperationLog;
import com.cc.system.log.enums.ModuleEnum;
import com.cc.system.log.enums.OperTypeEnum;
import com.cc.system.message.bean.MessageBean;
import com.cc.system.message.enums.MessageStatusEnum;
import com.cc.system.message.enums.MessageTypeEnum;
import com.cc.system.message.service.MessageService;
import com.cc.system.shiro.SecurityContextUtil;
import com.cc.user.bean.TUserBean;
import com.cc.user.enums.UserTypeEnum;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system/message")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@Autowired
	private LeaguerChannelService leaguerChannelService;

	/**
	 * 查询消息类别
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public Response<Map<String, String>> queryMessageType(){
		Response<Map<String, String>> response = new Response<Map<String,String>>();
		Map<String, String> typeMap = MessageTypeEnum.getMessageTypeMap();
		if (typeMap==null || typeMap.isEmpty()) {
			response.setMessage("暂无消息类型数据");
			return response;
		}
		response.setData(typeMap);
		response.setSuccess(Boolean.TRUE);
		return response;
	}
	
	/**
	 * 新增消息
	 * @param messageMap
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "system.message.release" })
	@RequestMapping(value = "/release", method = RequestMethod.POST)
	@OperationLog(module = ModuleEnum.MESSAGEMANAGEMENT, operType = OperTypeEnum.RELEASE, title = "发布消息")
	public Response<String> addMessage(@RequestBody Map<String, String> messageMap){
		Response<String> response = new Response<String>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		if (!UserTypeEnum.USER.getCode().equals(user.getUserType())) {
			response.setMessage("对不起,您无权操作");
			return response;
		}
		MessageBean messageBean = new MessageBean();
		messageBean.setSenderId(user.getId());
		String type = messageMap.get("type");
		if (StringTools.isNullOrNone(type)) {
			response.setMessage("请选择消息类型");
			return response;
		}
		MessageTypeEnum messageTypeEnum = MessageTypeEnum.getMessageTypeEnumByCode(type);
		if(messageTypeEnum == null){
			response.setMessage("消息类型不支持，请重新选择");
			return response;
		}
		if(MessageTypeEnum.SYSTEM.equals(messageTypeEnum)){
			response.setMessage("不能发布系统消息");
			return response;
		}
		messageBean.setType(type);
		String receiverId = messageMap.get("receiverId");
		if(MessageTypeEnum.MESSAGE.equals(messageTypeEnum) && StringTools.isNullOrNone(receiverId)){
			response.setMessage("普通消息必须选择接收人");
			return response;
		}
		if(!StringTools.isNullOrNone(receiverId)){
			messageBean.setReceiverId(Long.valueOf(receiverId));
		}
		if(!StringTools.isNullOrNone(messageMap.get("channelId"))){
			Long channelId = Long.valueOf(messageMap.get("channelId"));
			ChannelBean channelBean = ChannelBean.get(ChannelBean.class, channelId);
			if(channelBean == null || !ChannelStatusEnum.NORMAL.equals(ChannelStatusEnum.getChannelStatusEnumByCode(channelBean.getStatus()))){
				response.setMessage("频道不支持，请重新选择");
				return response;
			}
			messageBean.setChannelId(channelId);
		}
		messageBean.setPath(messageMap.get("path"));
		String message = messageMap.get("message");
		if (StringTools.isNullOrNone(message)) {
			response.setMessage("请输入消息内容");
			return response;
		}
		messageBean.setMessage(message);
		messageBean.setStatus(MessageStatusEnum.UNREAD.getCode());
		messageBean.setCreateTime(DateTools.now());
		try {
			messageService.saveMessage(messageBean);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 分页查询消息
	 * @param form
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public Page<MessageBean> queryMessagePage(@ModelAttribute MessageQueryForm form){
		Page<MessageBean> page = new Page<MessageBean>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			page.setMessage("请先登录");
			return page;
		}
		form.setReceiverId(user.getId());
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size() > 1) {
				page.setMessage("您还未注册");
				return page;
			}
			List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerBeanList.get(0).getId());
			if(!ListTools.isEmptyOrNull(leaguerChannelBeanList)){
				form.setChannelList(leaguerChannelBeanList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
			}
		}
		page = messageService.queryMessagePage(form);
		return page;
	}
	
	/**
	 * 阅读消息
	 * @param messageId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/read/{messageId:\\d+}", method = RequestMethod.POST)
	public Response<String> readMessage(@PathVariable Long messageId){
		Response<String> response = new Response<String>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		try {
			messageService.readMessage(user.getId(), messageId);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 清空已阅读消息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/clear", method = RequestMethod.POST)
	public Response<String> clearMessage(){
		Response<String> response = new Response<String>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		try {
			messageService.clearReadMessage(user.getId());
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * 全部标记为已读
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/readAll", method = RequestMethod.POST)
	public Response<String> readAllMessage(){
		Response<String> response = new Response<String>();
		TUserBean user = SecurityContextUtil.getCurrentUser();
		if (user==null) {
			response.setMessage("请先登录");
			return response;
		}
		MessageQueryForm form = new MessageQueryForm();
		form.setReceiverId(user.getId());
		if (UserTypeEnum.LEAGUER.getCode().equals(user.getUserType())) {
			List<LeaguerBean> leaguerBeanList = LeaguerBean.findAllByParams(LeaguerBean.class, "uid", user.getId());
			if (ListTools.isEmptyOrNull(leaguerBeanList) || leaguerBeanList.size() > 1) {
				response.setMessage("您还未注册");
				return response;
			}
			List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerBeanList.get(0).getId());
			if(!ListTools.isEmptyOrNull(leaguerChannelBeanList)){
				form.setChannelList(leaguerChannelBeanList.stream().map(channel->channel.getChannelId()).collect(Collectors.toList()));
			}
		}
		try {
			messageService.readAllMessage(form);
			response.setSuccess(Boolean.TRUE);
		} catch (LogicException e) {
			response.setMessage(e.getErrContent());
		} catch (Exception e) {
			response.setMessage("系统内部错误");
			e.printStackTrace();
		}
		return response;
	}
	
}
