/**
 * 
 */
package com.cc.film.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.channel.schedule.ChannelSchedule;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;
import com.cc.film.http.response.data.FilmData;
import com.cc.film.service.FilmService;
import com.cc.spider.http.request.SpiderRequest;
import com.cc.spider.http.response.DataPage;
import com.cc.spider.http.response.SpiderResponse;
import com.cc.spider.http.service.SpiderService;

/**
 * 影片更新任务
 * @author Administrator
 *
 */
@Component
public class FilmSchedule extends ChannelSchedule {
	
	private static final Logger logger = LoggerFactory.getLogger(FilmSchedule.class);
	
	@Autowired
	private SpiderService spiderService;
	
	@Autowired
	private FilmService filmService;

	/* (non-Javadoc)
	 * @see com.cc.channel.schedule.ChannelSchedule#querySpiderItems(com.cc.spider.http.request.SpiderRequest)
	 */
	@Override
	public Long querySpiderItems(SpiderRequest request) {
		Long count = 0L;
		Boolean more = Boolean.TRUE;
		while(more){
			SpiderResponse<DataPage> spiderResponse = spiderService.querySpiderItems(request);
			if (spiderResponse.isSuccess()) {
				DataPage dataPage = spiderResponse.getData();
				if (!dataPage.isSuccess()) {
					logger.error("频道[电影]获取数据失败,"+dataPage.getMessage());
					break;
				}
				List<String> dataList = dataPage.getData();
				if (ListTools.isEmptyOrNull(dataList)) {
					break;
				}
				for (String data : dataList) {
					FilmData filmData = JsonTools.toObject(data, FilmData.class);
					filmData.setSpiderNo(request.getSpiderNo());
					try {
						if (StringTools.isNullOrNone(filmData.getUrl()) && ListTools.isEmptyOrNull(filmData.getUrls())) {
							logger.warn("频道[电影]丢弃影片["+filmData.getName()+"],丢弃原因[没有播放地址]");
							continue;
						}
						filmService.saveFilmData(filmData);
						count++;
					} catch (Exception e) {
						logger.error("保存影片["+filmData.getName()+"]异常");
						e.printStackTrace();
					}
				}
				if (!dataPage.isMore()) {
					more = Boolean.FALSE;
				}
			}else{
				logger.error("频道[电影]更新数据失败,"+spiderResponse.getMessage());
				break;
			}
		}
		return count;
	}

}
