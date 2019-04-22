/**
 * 
 */
package com.cc.map.http.response.model;

/**
 * @author ws_yu
 *
 */
public class GeocoderRoad {

	/**
	 * 道路名称
	 */
	private String name;
	
	/**
	 * 离坐标点距离
	 */
	private String distance;

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
}
