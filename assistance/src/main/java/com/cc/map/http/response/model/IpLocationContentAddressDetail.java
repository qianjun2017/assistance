package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpLocationContentAddressDetail {

	/**
	 * 城市
	 */
	private String city;
	
	/**
	 * 百度城市代码
	 */
	@JsonProperty(value="city_code")
	private String cityCode;
	
	/**
	 * 区县
	 */
	private String district;
	
	/**
	 * 省份
	 */
	private String province;
	
	/**
	 * 街道
	 */
	private String street;
	
	/**
	 * 门牌号
	 */
	@JsonProperty(value="street_number")
	private String streetNumber;

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
	 * @return the cityCode
	 */
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
}
