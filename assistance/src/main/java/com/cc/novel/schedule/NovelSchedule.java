/**
 * 
 */
package com.cc.novel.schedule;

import java.util.List;

import com.cc.novel.enums.SpiderTypeEnum;
import com.cc.system.config.bean.SystemConfigBean;
import com.cc.system.config.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cc.channel.schedule.ChannelSchedule;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.novel.bean.NovelBean;
import com.cc.novel.bean.NovelChapterBean;
import com.cc.novel.bean.NovelChapterUserBean;
import com.cc.novel.enums.NovelLoadingStatusEnum;
import com.cc.novel.http.response.data.NovelChapterData;
import com.cc.novel.http.response.data.NovelData;
import com.cc.novel.service.NovelService;
import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.http.response.DataPage;
import com.cc.spider.http.response.SpiderResponse;
import com.cc.spider.http.service.SpiderService;
import com.cc.system.message.service.MessageService;

/**
 * @author Administrator
 *
 */
@Component
public class NovelSchedule extends ChannelSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(NovelSchedule.class);
	
	@Autowired
	private SpiderService spiderService;
	
	@Autowired
	private NovelService novelService;
	
	@Autowired
	private MessageService messageService;

	@Autowired
	private SystemConfigService systemConfigService;

	/* (non-Javadoc)
	 * @see com.cc.channel.schedule.ChannelSchedule#querySpiderItems(com.cc.spider.http.request.SpiderRequest)
	 */
	@Override
	public Long querySpiderItems(SpiderRequest request) {
		Long count = 0L;
		Boolean more = Boolean.TRUE;
		String spiderNo = request.getSpiderNo();
		try {
			SystemConfigBean systemConfigBean = systemConfigService.querySystemConfigBean("parent." + spiderNo);
			spiderNo = systemConfigBean.getPropertyValue();
		} catch (Exception e){

		}
		while(more){
			SpiderResponse<DataPage> spiderResponse = spiderService.querySpiderItems(request);
			if (spiderResponse.isSuccess()) {
				DataPage dataPage = spiderResponse.getData();
				if (!dataPage.isSuccess()) {
					logger.error("频道[小说]获取数据失败,"+dataPage.getMessage());
					break;
				}
				List<String> dataList = dataPage.getData();
				if (ListTools.isEmptyOrNull(dataList)) {
					break;
				}
				for (String data : dataList) {
					SpiderTypeEnum spiderTypeEnum = SpiderTypeEnum.getSpiderTypeEnumByCode(request.getSpiderType());
					if (SpiderTypeEnum.BASE.equals(spiderTypeEnum)) {
						NovelData novelData = JsonTools.toObject(data, NovelData.class);
						novelData.setSpiderNo(spiderNo);
						if("http://www.biqugecom.com/files/article/images/nocover.jpg".equals(novelData.getImg())){
							novelData.setImg("/static/nocover.jpg");
						}
						try {
							novelService.saveNovelData(novelData);
							count++;
						} catch (Exception e) {
							String message = "保存小说[" + novelData.getName() + "("+novelData.getId()+")]异常";
							try{
								messageService.releaseSystemMessage("novel.download.now", message);
							} catch (LogicException e1) {
								logger.error(e1.getErrCode());
							} catch (Exception e1) {
								e1.printStackTrace();
								logger.error("发布系统消息错误: 系统内部错误");
							}
							logger.info(message);
							e.printStackTrace();
						} 
					}else if (SpiderTypeEnum.CHAPTER.equals(spiderTypeEnum)) {
						NovelChapterData novelChapterData = JsonTools.toObject(data, NovelChapterData.class);
						novelChapterData.setSpiderNo(spiderNo);
						try {
							novelService.saveNovelChapterData(novelChapterData);
							count++;
						} catch (Exception e) {
							logger.error("保存小说章节[" + novelChapterData.getName() + "("+novelChapterData.getId()+")]异常");
							e.printStackTrace();
						} 
					}
				}
				if (!dataPage.isMore()) {
					more = Boolean.FALSE;
				}
			}else{
				logger.error("频道[小说]更新数据失败,"+spiderResponse.getMessage());
				break;
			}
		}
		return count;
	}
	
	/**
	 * 每隔1小时编排一次小说章节顺序
	 */
	@Scheduled(fixedDelay=1000*60*60)
	public void orderNovelChapter(){
		List<NovelBean> loadingNovelBeanList = NovelBean.findAllByParams(NovelBean.class, "downloading", NovelLoadingStatusEnum.LOADING.getCode());
		for (NovelBean novelBean : loadingNovelBeanList) {
			List<NovelChapterBean> novelOriginChapterList = novelService.queryNovelOriginChapterList(novelBean.getId());
			if (ListTools.isEmptyOrNull(novelOriginChapterList)) {
				continue;
			}
			if (novelOriginChapterList.size()>1) {
				String message = "小说["+novelBean.getName()+"]章节可能存在缺失";
				try{
					messageService.releaseSystemMessage("novel.download.now", message, "/novel/"+novelBean.getId());
				} catch (LogicException e) {
					logger.error(e.getErrCode());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("发布系统消息错误: 系统内部错误");
				}
				logger.info(message);
			}
			NovelChapterBean novelMaxOrderedChapter = novelService.queryNovelMaxOrderedChapter(novelBean.getId());
			if(novelMaxOrderedChapter!=null){
				try{
					if(novelMaxOrderedChapter.getNextId()!=null){
						novelService.orderNovelChapter(novelMaxOrderedChapter.getId());
						logger.info("小说["+novelBean.getName()+"]编排章节顺序"+(novelOriginChapterList.size()>1?"部分":"")+"完成");
						novelMaxOrderedChapter = novelService.queryNovelMaxOrderedChapter(novelBean.getId());
						List<NovelChapterUserBean> novelChapterUserBeanList = NovelChapterUserBean.findAllByParams(NovelChapterUserBean.class, "novelId", novelBean.getId());
						for(NovelChapterUserBean novelChapterUserBean: novelChapterUserBeanList){
							if(!novelChapterUserBean.getChapterId().equals(novelMaxOrderedChapter.getId())){
								messageService.releaseSystemMessage(novelChapterUserBean.getUserId(), "您正在阅读的小说【"+novelBean.getName()+"】有新章节，点击进入", "/novel/"+novelBean.getId());
							}
						}
					}
				}catch (LogicException e) {
					logger.error("小说["+novelBean.getName()+"]编排章节顺序错误: "+e.getErrContent());
				}catch (Exception e) {
					e.printStackTrace();
					logger.error("小说["+novelBean.getName()+"]编排章节顺序错误: 系统内部错误");
				}
			}else{
				String message = "小说["+novelBean.getName()+"]请尽快选择起始章节进行编排顺序";
				try{
					messageService.releaseSystemMessage("novel.order", message, "/novel/"+novelBean.getId());
				} catch (LogicException e) {
					logger.error(e.getErrCode());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("发布系统消息错误: 系统内部错误");
				}
				logger.info(message);
			}
		}
	}
	
}
