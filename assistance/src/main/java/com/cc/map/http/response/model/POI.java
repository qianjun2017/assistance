/**
 * 
 */
package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class POI {

	/**
	 * poi名称
	 */
	private String name;
	
	/**
	 * poi经纬度坐标
	 */
	private Location location;
	
	/**
	 * poi地址信息
	 */
	private String address;
	
	/**
	 * 所属省份
	 */
	private String province;
	
	/**
	 * 所属城市
	 */
	private String city;
	
	/**
	 * 所属区县
	 */
	private String area;
	
	/**
	 * poi电话信息
	 */
	private String telephone;
	
	/**
	 * poi的唯一标示
	 */
	private String uid;
	
	/**
	 * 街景图id
	 */
	@JsonProperty(value="street_id")
	private String streetId;
	
	/**
	 * 是否有详情页：1有，0没有
	 */
	private String detail;
	
	/**
	 * poi的扩展信息，仅当scope=2时，显示该字段，不同的poi类型，显示的detail_info字段不同
	 */
	@JsonProperty(value="detail_info")
	private DetailInfo detailInfo;

	/**
	 * poi对应的品牌（如加油站中的『中石油』、『中石化』）
	 */
	private String brand;
	
	/**
	 * poi标签信息
	 */
	@JsonProperty(value="content_tag")
	private String contentTag;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the streetId
	 */
	public String getStreetId() {
		return streetId;
	}

	/**
	 * @param streetId the streetId to set
	 */
	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the detailInfo
	 */
	public DetailInfo getDetailInfo() {
		return detailInfo;
	}

	/**
	 * @param detailInfo the detailInfo to set
	 */
	public void setDetailInfo(DetailInfo detailInfo) {
		this.detailInfo = detailInfo;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the contentTag
	 */
	public String getContentTag() {
		return contentTag;
	}

	/**
	 * @param contentTag the contentTag to set
	 */
	public void setContentTag(String contentTag) {
		this.contentTag = contentTag;
	}
}
