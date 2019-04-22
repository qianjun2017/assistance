/**
 * 
 */
package com.cc.channel.schedule.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cc.channel.bean.ChannelBean;
import com.cc.channel.enums.ChannelStatusEnum;
import com.cc.channel.enums.ChannelTypeEnum;
import com.cc.channel.form.ChannelQueryForm;
import com.cc.channel.schedule.ChannelSchedule;
import com.cc.channel.service.ChannelService;
import com.cc.common.spring.SpringContextUtil;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.spider.bean.ChannelSpiderBean;
import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.service.ChannelSpiderService;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;

/**
 * @author Administrator
 *
 */
@Component
public class ChannelScheduleTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ChannelScheduleTask.class);

	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private ChannelSpiderService channelSpiderService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 每隔15分钟更新一次频道数据
	 */
	@Scheduled(fixedDelay=1000*60*15)
	public void fetchChannelData(){
		SpiderRequest request = new SpiderRequest();
		List<SystemConfigBean> systemConfigBeanList = systemConfigService.querySystemConfigBeanList("spider");
		if (!ListTools.isEmptyOrNull(systemConfigBeanList)) {
			for (SystemConfigBean systemConfigBean : systemConfigBeanList) {
				if ("spider.userName".equals(systemConfigBean.getPropertyName())) {
					request.setUserName(systemConfigBean.getPropertyValue());
				}
				if ("spider.pwd".equals(systemConfigBean.getPropertyName())) {
					request.setPwd(systemConfigBean.getPropertyValue());
				}
				if ("spider.url".equals(systemConfigBean.getPropertyName())) {
					request.setUrl(systemConfigBean.getPropertyValue()+"/items");
				}
			}
		}
		if (StringTools.isAnyNullOrNone(new String[] {request.getUserName(),request.getPwd(),request.getUrl()})) {
			logger.warn("爬虫系统参数设置不完整");
			return;
		}
		ChannelQueryForm form = new ChannelQueryForm();
		form.setStatus(ChannelStatusEnum.NORMAL.getCode());
		form.setChannelType(ChannelTypeEnum.ORDINARY.getCode());
		List<ChannelBean> channelBeanList = channelService.queryChannelBeanList(form);
		if (ListTools.isEmptyOrNull(channelBeanList)) {
			return;
		}
		for (ChannelBean channelBean : channelBeanList) {
			String channelCode = channelBean.getChannelCode();
			logger.info("更新频道["+channelBean.getChannelName()+"]数据");
			Long count = 0L;
			Object object = SpringContextUtil.getBean(channelCode+"Schedule");
			if (object==null) {
				logger.info("频道["+channelBean.getChannelName()+"]尚未配置更新任务");
				continue;
			}
			if (!(object instanceof ChannelSchedule)) {
				logger.error("频道["+channelBean.getChannelName()+"]更新任务配置错误");
				continue;
			}
			List<ChannelSpiderBean> channelSpiderBeanList = channelSpiderService.queryChannelSpiderBeanList(channelBean.getId());
			if (ListTools.isEmptyOrNull(channelSpiderBeanList)) {
				logger.info("频道["+channelBean.getChannelName()+"]尚未配置爬虫");
				continue;
			}
			ChannelSchedule channelSchedule = (ChannelSchedule) object;
			for (ChannelSpiderBean channelSpiderBean : channelSpiderBeanList) {
				String spiderNo = channelSpiderBean.getSpiderNo();
				request.setSpiderNo(spiderNo);
				request.setSpiderType(channelSpiderBean.getSpiderType());
				Long spiderCount = channelSchedule.querySpiderItems(request);
				logger.info("爬虫["+spiderNo+"]共更新"+spiderCount+"条数据");
				count += spiderCount.longValue();
			}
			logger.info("频道["+channelBean.getChannelName()+"]共更新"+count+"条数据");
		}
	}
}
