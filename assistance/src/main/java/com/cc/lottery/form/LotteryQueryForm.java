/**
 * 
 */
package com.cc.lottery.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public class LotteryQueryForm {

	/**
	 * 所属会员
	 */
	private Long retailerId;
	
	/**
	 * 所属会员微信openid
	 */
	private String retailerOpenid;
	
	/**
	 * 所属会员微信昵称
	 */
	private String nickName;
	
	/**
	 * 抽奖会员微信opernid
	 */
	private String leaguerOpenid;
	
	/**
	 * 抽奖会员
	 */
	private Long leaguerId;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 开始时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeStart;
	
	/**
	 * 结束时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTimeEnd;
	
	/**
	 * 抽奖
	 */
	private Long lotteryId;
	
	/**
	 * 是否需要分享
	 */
	private Boolean share;
	
	/**
	 * 页码
	 */
	private String page = "1";
	
	/**
	 * 每页数量
	 */
	private String pageSize = "10";
	
	/**
	 * 排序字段
	 */
	private String sort;
	
	/**
	 * 排序方向
	 */
	private String order;

	/**
	 * @return the leaguerId
	 */
	public Long getLeaguerId() {
		return leaguerId;
	}

	/**
	 * @param leaguerId the leaguerId to set
	 */
	public void setLeaguerId(Long leaguerId) {
		this.leaguerId = leaguerId;
	}

	/**
	 * @return the retailerId
	 */
	public Long getRetailerId() {
		return retailerId;
	}

	/**
	 * @param retailerId the retailerId to set
	 */
	public void setRetailerId(Long retailerId) {
		this.retailerId = retailerId;
	}

	/**
	 * @return the retailerOpenid
	 */
	public String getRetailerOpenid() {
		return retailerOpenid;
	}

	/**
	 * @param retailerOpenid the retailerOpenid to set
	 */
	public void setRetailerOpenid(String retailerOpenid) {
		this.retailerOpenid = retailerOpenid;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the leaguerOpenid
	 */
	public String getLeaguerOpenid() {
		return leaguerOpenid;
	}

	/**
	 * @param leaguerOpenid the leaguerOpenid to set
	 */
	public void setLeaguerOpenid(String leaguerOpenid) {
		this.leaguerOpenid = leaguerOpenid;
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
	 * @return the createTimeStart
	 */
	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	/**
	 * @param createTimeStart the createTimeStart to set
	 */
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	/**
	 * @return the createTimeEnd
	 */
	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	/**
	 * @param createTimeEnd the createTimeEnd to set
	 */
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	/**
	 * @return the lotteryId
	 */
	public Long getLotteryId() {
		return lotteryId;
	}

	/**
	 * @param lotteryId the lotteryId to set
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	/**
	 * @return the share
	 */
	public Boolean getShare() {
		return share;
	}

	/**
	 * @param share the share to set
	 */
	public void setShare(Boolean share) {
		this.share = share;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		if(!StringTools.isNullOrNone(this.page) && StringTools.isNumber(this.page)){
			return Integer.parseInt(this.page);
		}
		return 1;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		if(!StringTools.isNullOrNone(this.pageSize) && StringTools.isNumber(this.pageSize)){
			return Integer.parseInt(this.pageSize);
		}
		return 10;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the sort
	 */
	public String getSort() {
		if(StringTools.isNullOrNone(this.sort)){
			return "createTime";
		}
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		if (StringTools.isNullOrNone(this.order)) {
			return "desc";
		}
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
}
