/**
 * 
 */
package com.cc.wx.http.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class StoreInfo {
	
	/**
	 * 门店编号，由商户自定义
	 */
	private String id;
	
	/**
	 * 门店名称 ，由商户自定义
	 */
	private String name;
	
	/**
	 * 门店所在地行政区划码，详细见《最新县及县以上行政区划代码》 详见微信支付文档
	 */
	@JsonProperty(value="area_code")
	private String areaCode;
	
	/**
	 * 门店详细地址 ，由商户自定义
	 */
	private String address;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
}
