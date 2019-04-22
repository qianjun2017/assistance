/**
 * 
 */
package com.cc.map.http.response.model;

/**
 * @author ws_yu
 *
 */
public class Location {

	/**
	 * 纬度值
	 */
	private Float lat;
	
	/**
	 * 经度值
	 */
	private Float lng;

	/**
	 * @return the lat
	 */
	public Float getLat() {
		return lat;
	}

	/**
	 * @param lat the lat to set
	 */
	public void setLat(Float lat) {
		this.lat = lat;
	}

	/**
	 * @return the lng
	 */
	public Float getLng() {
		return lng;
	}

	/**
	 * @param lng the lng to set
	 */
	public void setLng(Float lng) {
		this.lng = lng;
	}
}
