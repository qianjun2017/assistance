/**
 * 
 */
package com.cc.channel.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cc.common.orm.BaseOrm;
import com.cc.common.orm.entity.BaseEntity;

/**
 * @author Administrator
 *
 */
@Table(name="t_channel")
public class ChannelBean extends BaseOrm<ChannelBean> implements BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8946620818273349509L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 频道编码
	 */
	private String channelCode;
	
	/**
	 * 频道名称
	 */
	private String channelName;
	
	/**
	 * 频道图标
	 */
	private String channelIcon;
	
	/**
	 * 频道类型
	 */
	private String channelType;
	
	/**
	 * 频道状态
	 */
	private String status;
	
	/**
	 * 是否获取最热
	 */
	private Boolean hot;
	
	/**
	 * 是否获取最新
	 */
	private Boolean news;
	
	/**
	 * 是否获取推荐
	 */
	private Boolean recommend;
	
	/**
	 * 是否加载专题
	 */
	private Boolean subject;
	
	/**
	 * 频道专题加载是否启用地域限制
	 */
	private Boolean location;
	
	/**
	 * 综合频道专题加载是否启用普通频道限制
	 */
	private Boolean ordinary;
	
	/**
	 * 是否加载轮播
	 */
	private Boolean carousel;
	
	/**
	 * 是否可搜索
	 */
	private Boolean search;
	
	/**
	 * 是否生成首页
	 */
	private Boolean home;
	
	/**
	 * 是否加载菜单栏
	 */
	private Boolean menu;
	
	/**
	 * 模块间隔
	 */
	private Long padding;
	
	/**
	 * 轮播高度
	 */
	private Long height;
	
	/* (non-Javadoc)
	 * @see com.cc.common.orm.BaseOrm#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}
	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}
	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the channelIcon
	 */
	public String getChannelIcon() {
		return channelIcon;
	}
	/**
	 * @param channelIcon the channelIcon to set
	 */
	public void setChannelIcon(String channelIcon) {
		this.channelIcon = channelIcon;
	}
	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}
	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	/**
	 * @return the hot
	 */
	public Boolean getHot() {
		return hot;
	}
	/**
	 * @param hot the hot to set
	 */
	public void setHot(Boolean hot) {
		this.hot = hot;
	}
	/**
	 * @return the news
	 */
	public Boolean getNews() {
		return news;
	}
	/**
	 * @param news the news to set
	 */
	public void setNews(Boolean news) {
		this.news = news;
	}
	/**
	 * @return the recommend
	 */
	public Boolean getRecommend() {
		return recommend;
	}
	/**
	 * @param recommend the recommend to set
	 */
	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
	/**
	 * @return the subject
	 */
	public Boolean getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(Boolean subject) {
		this.subject = subject;
	}
	/**
	 * @return the location
	 */
	public Boolean getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(Boolean location) {
		this.location = location;
	}
	/**
	 * @return the ordinary
	 */
	public Boolean getOrdinary() {
		return ordinary;
	}
	/**
	 * @param ordinary the ordinary to set
	 */
	public void setOrdinary(Boolean ordinary) {
		this.ordinary = ordinary;
	}
	/**
	 * @return the carousel
	 */
	public Boolean getCarousel() {
		return carousel;
	}
	/**
	 * @param carousel the carousel to set
	 */
	public void setCarousel(Boolean carousel) {
		this.carousel = carousel;
	}
	/**
	 * @return the search
	 */
	public Boolean getSearch() {
		return search;
	}
	/**
	 * @param search the search to set
	 */
	public void setSearch(Boolean search) {
		this.search = search;
	}
	/**
	 * @return the home
	 */
	public Boolean getHome() {
		return home;
	}
	/**
	 * @param home the home to set
	 */
	public void setHome(Boolean home) {
		this.home = home;
	}
	/**
	 * @return the menu
	 */
	public Boolean getMenu() {
		return menu;
	}
	/**
	 * @param menu the menu to set
	 */
	public void setMenu(Boolean menu) {
		this.menu = menu;
	}
	/**
	 * @return the padding
	 */
	public Long getPadding() {
		return padding;
	}
	/**
	 * @param padding the padding to set
	 */
	public void setPadding(Long padding) {
		this.padding = padding;
	}
	/**
	 * @return the height
	 */
	public Long getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(Long height) {
		this.height = height;
	}

}
