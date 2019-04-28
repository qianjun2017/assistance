package com.cc.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.channel.bean.ChannelBean;
import com.cc.common.tools.ListTools;
import com.cc.common.web.Response;
import com.cc.leaguer.bean.LeaguerChannelBean;
import com.cc.leaguer.service.LeaguerChannelService;

@Controller
@RequestMapping("/api/channel")
public class ApiChannelController {
	
	@Autowired
	private LeaguerChannelService leaguerChannelService;

	/**
	 * 频道详情
	 * @param id
	 * @param leaguerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get/{id:\\d+}", method = RequestMethod.GET)
	public Response<ChannelBean> queryChannel(@PathVariable Long id, @RequestParam Long leaguerId){
		Response<ChannelBean> response = new Response<ChannelBean>();
		ChannelBean channelBean = ChannelBean.get(ChannelBean.class, id);
		if (channelBean==null) {
			response.setMessage("频道不存在或已删除");
			return response;
		}
		List<LeaguerChannelBean> leaguerChannelBeanList = leaguerChannelService.queryLeaguerChannelBeanList(leaguerId);
		if(ListTools.isEmptyOrNull(leaguerChannelBeanList)){
			response.setMessage("频道不存在或已删除");
			return response;
		}
		for(LeaguerChannelBean leaguerChannelBean: leaguerChannelBeanList){
			if(leaguerChannelBean.getChannelId().equals(channelBean.getId())){
				response.setData(channelBean);
				response.setSuccess(Boolean.TRUE);
				return response;
			}
		}
		response.setMessage("频道不存在或已删除");
		return response;
	}
}
