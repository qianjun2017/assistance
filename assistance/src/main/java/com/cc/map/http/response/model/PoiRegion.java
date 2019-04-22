/**
 * 
 */
package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class PoiRegion {

	/**
	 * 请求中的坐标与所归属区域面的相对位置关系
	 */
	@JsonProperty(value="direction_desc")
	private String directionDesc;
	
	/**
	 * 归属区域面名称
	 */
	private String name;
	
	/**
	 * 归属区域面类型
	 */
	private String tag;

	/**
	 * @return the directionDesc
	 */
	public String getDirectionDesc() {
		return directionDesc;
	}

	/**
	 * @param directionDesc the directionDesc to set
	 */
	public void setDirectionDesc(String directionDesc) {
		this.directionDesc = directionDesc;
	}

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
