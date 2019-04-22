/**
 * 
 */
package com.cc.map.http.response.model;

/**
 * @author ws_yu
 *
 */
public class SuggestionPOI {

	/**
	 * poi名称
	 */
	private String name;
	
	/**
	 * poi经纬度坐标
	 */
	private Location location;
	
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
	private String district;
	
	/**
	 * poi的唯一标示
	 */
	private String uid;
	
	/**
	 * poi地址信息
	 */
	private String address;
	
	/**
	 * poi 商圈信息
	 */
	private String business;
	
	/**
	 * 所属城市编码
	 */
	private String cityid;
	
	/**
	 * poi分类
	 */
	private String tag;

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
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
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
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * @return the cityid
	 */
	public String getCityid() {
		return cityid;
	}

	/**
	 * @param cityid the cityid to set
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
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
}
