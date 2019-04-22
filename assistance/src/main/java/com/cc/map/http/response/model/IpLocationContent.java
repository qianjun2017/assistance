package com.cc.map.http.response.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpLocationContent {

	/**
	 * 简要地址信息
	 */
	private String address;
	
	/**
	 * 地址详情
	 */
	@JsonProperty(value="address_detail")
	private IpLocationContentAddressDetail addressDetail;
	
	/**
	 * 当前城市中心点
	 */
	private IpLocationContentPoint point;

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
	 * @return the addressDetail
	 */
	public IpLocationContentAddressDetail getAddressDetail() {
		return addressDetail;
	}

	/**
	 * @param addressDetail the addressDetail to set
	 */
	public void setAddressDetail(IpLocationContentAddressDetail addressDetail) {
		this.addressDetail = addressDetail;
	}

	/**
	 * @return the point
	 */
	public IpLocationContentPoint getPoint() {
		return point;
	}

	/**
	 * @param point the point to set
	 */
	public void setPoint(IpLocationContentPoint point) {
		this.point = point;
	}
}
