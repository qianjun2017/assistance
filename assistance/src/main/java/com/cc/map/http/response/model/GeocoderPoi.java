/**
 * 
 */
package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class GeocoderPoi {
	
	/**
	 * 地址信息
	 */
	private String addr;
	
	/**
	 * 数据来源（已废弃）
	 */
	private String cp;

	/**
	 * 和当前坐标点的方向
	 */
	private String direction;
	
	/**
	 * 离坐标点距离
	 */
	private Integer distance;
	
	/**
	 * poi名称
	 */
	private String name;
	
	/**
	 * poi类型，如’美食;中餐厅’。tag与poiType字段均为poi类型，建议使用tag字段，信息更详细。poi详细类别
	 */
	private String tag;
	
	/**
	 * 电话
	 */
	private Integer tel;
	
	/**
	 * poi的唯一标示
	 */
	private String uid;
	
	/**
	 * 邮编
	 */
	private Integer zip;
	
	/**
	 * poi对应的主点poi（如，海底捞的主点为上地华联，该字段则为上地华联的poi信息。如无，该字段为空），包含子字段和pois基础召回字段相同
	 */
	@JsonProperty(value="parent_poi")
	private GeocoderPoi parentPoi;

	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
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

	/**
	 * @return the tel
	 */
	public Integer getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(Integer tel) {
		this.tel = tel;
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
	 * @return the zip
	 */
	public Integer getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(Integer zip) {
		this.zip = zip;
	}

	/**
	 * @return the parentPoi
	 */
	public GeocoderPoi getParentPoi() {
		return parentPoi;
	}

	/**
	 * @param parentPoi the parentPoi to set
	 */
	public void setParentPoi(GeocoderPoi parentPoi) {
		this.parentPoi = parentPoi;
	}
}
