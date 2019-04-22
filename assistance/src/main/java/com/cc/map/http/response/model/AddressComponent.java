/**
 * 
 */
package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class AddressComponent {

	/**
	 * 国家
	 */
	private String country;
	
	/**
	 * 省名
	 */
	private String province;
	
	/**
	 * 城市名
	 */
	private String city;
	
	/**
	 * 城市级别
	 */
	@JsonProperty(value="city_level")
	private String cityLevel;
	
	/**
	 * 区县名
	 */
	private String district;
	
	/**
	 * 乡镇名
	 */
	private String town;
	
	/**
	 * 街道名（行政区划中的街道层级）
	 */
	private String street;
	
	@JsonProperty(value="street_number")
	private String streetNumber;
	
	/**
	 * 行政区划代码 adcode映射表
	 */
	private String adcode;
	
	/**
	 * 国家代码
	 */
	@JsonProperty(value="country_code")
	private Integer countryCode;
	
	/**
	 * 国家代码
	 */
	@JsonProperty(value="country_code_iso")
	private Integer countryCodeIso;
	
	/**
	 * 国家代码
	 */
	@JsonProperty(value="country_code_iso2")
	private Integer countryCodeIso2;
	
	/**
	 * 相对当前坐标点的方向，当有门牌号的时候返回数据
	 */
	private String direction;
	
	/**
	 * 相对当前坐标点的距离，当有门牌号的时候返回数据
	 */
	private String distance;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
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
	 * @return the town
	 */
	public String getTown() {
		return town;
	}

	/**
	 * @param town the town to set
	 */
	public void setTown(String town) {
		this.town = town;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the streetNumber
	 */
	public String getStreetNumber() {
		return streetNumber;
	}

	/**
	 * @param streetNumber the streetNumber to set
	 */
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	/**
	 * @return the adcode
	 */
	public String getAdcode() {
		return adcode;
	}

	/**
	 * @param adcode the adcode to set
	 */
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	/**
	 * @return the countryCode
	 */
	public Integer getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}

	/**
	 * @return the cityLevel
	 */
	public String getCityLevel() {
		return cityLevel;
	}

	/**
	 * @param cityLevel the cityLevel to set
	 */
	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}

	/**
	 * @return the countryCodeIso
	 */
	public Integer getCountryCodeIso() {
		return countryCodeIso;
	}

	/**
	 * @param countryCodeIso the countryCodeIso to set
	 */
	public void setCountryCodeIso(Integer countryCodeIso) {
		this.countryCodeIso = countryCodeIso;
	}

	/**
	 * @return the countryCodeIso2
	 */
	public Integer getCountryCodeIso2() {
		return countryCodeIso2;
	}

	/**
	 * @param countryCodeIso2 the countryCodeIso2 to set
	 */
	public void setCountryCodeIso2(Integer countryCodeIso2) {
		this.countryCodeIso2 = countryCodeIso2;
	}
}
