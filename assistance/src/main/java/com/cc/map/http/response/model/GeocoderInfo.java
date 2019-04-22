/**
 * 
 */
package com.cc.map.http.response.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class GeocoderInfo {

	/**
	 * 经纬度坐标
	 */
	private Location location;
	
	/**
	 * 结构化地址信息
	 */
	@JsonProperty(value="formatted_address")
	private String formattedAddress;
	
	/**
	 * 坐标所在商圈信息，如 "人民大学,中关村,苏州街"。最多返回3个
	 */
	private String business;
	
	/**
	 * 
	 */
	private AddressComponent addressComponent;
	
	/**
	 * 周边poi
	 */
	private List<GeocoderPoi> pois;
	
	/**
	 * 
	 */
	private List<PoiRegion> poiRegions;
	
	/**
	 * 周围最近的道路数据
	 */
	private List<GeocoderRoad> roads;
	 
	/**
	 * 当前位置结合POI的语义化结果描述
	 */
	@JsonProperty(value="sematic_description")
	private String sematicDescription;
	
	/**
	 * 百度定义的城市id（正常更新与维护，但建议使用adcode）
	 */
	private Integer cityCode;

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
	 * @return the formattedAddress
	 */
	public String getFormattedAddress() {
		return formattedAddress;
	}

	/**
	 * @param formattedAddress the formattedAddress to set
	 */
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
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
	 * @return the addressComponent
	 */
	public AddressComponent getAddressComponent() {
		return addressComponent;
	}

	/**
	 * @param addressComponent the addressComponent to set
	 */
	public void setAddressComponent(AddressComponent addressComponent) {
		this.addressComponent = addressComponent;
	}

	/**
	 * @return the pois
	 */
	public List<GeocoderPoi> getPois() {
		return pois;
	}

	/**
	 * @param pois the pois to set
	 */
	public void setPois(List<GeocoderPoi> pois) {
		this.pois = pois;
	}

	/**
	 * @return the poiRegions
	 */
	public List<PoiRegion> getPoiRegions() {
		return poiRegions;
	}

	/**
	 * @param poiRegions the poiRegions to set
	 */
	public void setPoiRegions(List<PoiRegion> poiRegions) {
		this.poiRegions = poiRegions;
	}

	/**
	 * @return the roads
	 */
	public List<GeocoderRoad> getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(List<GeocoderRoad> roads) {
		this.roads = roads;
	}

	/**
	 * @return the sematicDescription
	 */
	public String getSematicDescription() {
		return sematicDescription;
	}

	/**
	 * @param sematicDescription the sematicDescription to set
	 */
	public void setSematicDescription(String sematicDescription) {
		this.sematicDescription = sematicDescription;
	}

	/**
	 * @return the cityCode
	 */
	public Integer getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode the cityCode to set
	 */
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
}
