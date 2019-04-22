package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetailInfo {

	/**
	 * 距离中心点的距离，圆形区域检索时返回
	 */
	private Integer distance;
	
	/**
	 * 所属分类，如’hotel’、’cater’
	 */
	private String type;
	
	/**
	 * 标签
	 */
	private String tag;
	
	/**
	 * POI对应的导航引导点坐标。大型面状POI的导航引导点，一般为各类出入口，方便结合导航、路线规划等服务使用
	 */
	@JsonProperty(value="navi_location")
	private Location naviLocation;
	
	/**
	 * poi别名
	 */
	private String alias;
	
	/**
	 * poi的详情页
	 */
	@JsonProperty(value="detail_url")
	private String detailUrl;
	
	/**
	 * poi商户的价格
	 */
	private String price;
	
	/**
	 * 营业时间
	 */
	@JsonProperty(value="shop_hours")
	private String shopHours;
	
	/**
	 * 总体评分
	 */
	@JsonProperty(value="overall_rating")
	private String overallRating;
	
	/**
	 * 口味评分
	 */
	@JsonProperty(value="taste_rating")
	private String tasteRating;
	
	/**
	 * 服务评分
	 */
	@JsonProperty(value="service_rating")
	private String serviceRating;
	
	/**
	 * 环境评分
	 */
	@JsonProperty(value="environment_rating")
	private String environmentRating;
	
	/**
	 * 星级（设备）评分
	 */
	@JsonProperty(value="facility_rating")
	private String facilityRating;
	
	/**
	 * 卫生评分
	 */
	@JsonProperty(value="hygiene_rating")
	private String hygieneRating;
	
	/**
	 * 技术评分
	 */
	@JsonProperty(value="technology_rating")
	private String technologyRating;
	
	/**
	 * 图片数
	 */
	@JsonProperty(value="image_num")
	private String imageNum;
	
	/**
	 * 团购数
	 */
	@JsonProperty(value="groupon_num")
	private Integer grouponNum;
	
	/**
	 * 优惠数
	 */
	@JsonProperty(value="discount_num")
	private Integer discountNum;
	
	/**
	 * 评论数
	 */
	@JsonProperty(value="comment_num")
	private String commentNum;
	
	/**
	 * 收藏数
	 */
	@JsonProperty(value="favorite_num")
	private String favoriteNum;
	
	/**
	 * 签到数
	 */
	@JsonProperty(value="checkin_num")
	private String checkinNum;

	/**
	 * @return the distance
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
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
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the naviLocation
	 */
	public Location getNaviLocation() {
		return naviLocation;
	}

	/**
	 * @param naviLocation the naviLocation to set
	 */
	public void setNaviLocation(Location naviLocation) {
		this.naviLocation = naviLocation;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the detailUrl
	 */
	public String getDetailUrl() {
		return detailUrl;
	}

	/**
	 * @param detailUrl the detailUrl to set
	 */
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the shopHours
	 */
	public String getShopHours() {
		return shopHours;
	}

	/**
	 * @param shopHours the shopHours to set
	 */
	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}

	/**
	 * @return the overallRating
	 */
	public String getOverallRating() {
		return overallRating;
	}

	/**
	 * @param overallRating the overallRating to set
	 */
	public void setOverallRating(String overallRating) {
		this.overallRating = overallRating;
	}

	/**
	 * @return the tasteRating
	 */
	public String getTasteRating() {
		return tasteRating;
	}

	/**
	 * @param tasteRating the tasteRating to set
	 */
	public void setTasteRating(String tasteRating) {
		this.tasteRating = tasteRating;
	}

	/**
	 * @return the serviceRating
	 */
	public String getServiceRating() {
		return serviceRating;
	}

	/**
	 * @param serviceRating the serviceRating to set
	 */
	public void setServiceRating(String serviceRating) {
		this.serviceRating = serviceRating;
	}

	/**
	 * @return the environmentRating
	 */
	public String getEnvironmentRating() {
		return environmentRating;
	}

	/**
	 * @param environmentRating the environmentRating to set
	 */
	public void setEnvironmentRating(String environmentRating) {
		this.environmentRating = environmentRating;
	}

	/**
	 * @return the facilityRating
	 */
	public String getFacilityRating() {
		return facilityRating;
	}

	/**
	 * @param facilityRating the facilityRating to set
	 */
	public void setFacilityRating(String facilityRating) {
		this.facilityRating = facilityRating;
	}

	/**
	 * @return the hygieneRating
	 */
	public String getHygieneRating() {
		return hygieneRating;
	}

	/**
	 * @param hygieneRating the hygieneRating to set
	 */
	public void setHygieneRating(String hygieneRating) {
		this.hygieneRating = hygieneRating;
	}

	/**
	 * @return the technologyRating
	 */
	public String getTechnologyRating() {
		return technologyRating;
	}

	/**
	 * @param technologyRating the technologyRating to set
	 */
	public void setTechnologyRating(String technologyRating) {
		this.technologyRating = technologyRating;
	}

	/**
	 * @return the imageNum
	 */
	public String getImageNum() {
		return imageNum;
	}

	/**
	 * @param imageNum the imageNum to set
	 */
	public void setImageNum(String imageNum) {
		this.imageNum = imageNum;
	}

	/**
	 * @return the grouponNum
	 */
	public Integer getGrouponNum() {
		return grouponNum;
	}

	/**
	 * @param grouponNum the grouponNum to set
	 */
	public void setGrouponNum(Integer grouponNum) {
		this.grouponNum = grouponNum;
	}

	/**
	 * @return the discountNum
	 */
	public Integer getDiscountNum() {
		return discountNum;
	}

	/**
	 * @param discountNum the discountNum to set
	 */
	public void setDiscountNum(Integer discountNum) {
		this.discountNum = discountNum;
	}

	/**
	 * @return the commentNum
	 */
	public String getCommentNum() {
		return commentNum;
	}

	/**
	 * @param commentNum the commentNum to set
	 */
	public void setCommentNum(String commentNum) {
		this.commentNum = commentNum;
	}

	/**
	 * @return the favoriteNum
	 */
	public String getFavoriteNum() {
		return favoriteNum;
	}

	/**
	 * @param favoriteNum the favoriteNum to set
	 */
	public void setFavoriteNum(String favoriteNum) {
		this.favoriteNum = favoriteNum;
	}

	/**
	 * @return the checkinNum
	 */
	public String getCheckinNum() {
		return checkinNum;
	}

	/**
	 * @param checkinNum the checkinNum to set
	 */
	public void setCheckinNum(String checkinNum) {
		this.checkinNum = checkinNum;
	}
	
}
