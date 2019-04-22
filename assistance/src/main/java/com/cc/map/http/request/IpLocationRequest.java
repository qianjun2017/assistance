package com.cc.map.http.request;

public class IpLocationRequest {

	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 开发者密钥，可在API控制台申请获得
	 */
	private String ak;
	
	/**
	 * 此ak对应的sk私钥
	 */
	private String sk;
	
	/**
	 * 若用户所用AK的校验方式为SN校验时该参数必填（什么是SN校验？）。其他AK校验方式的可不填写
	 */
	private String sn;
	
	/**
	 * 经纬度的坐标类型  
	 * coor不出现、或为空：百度墨卡托坐标，即百度米制坐标   
	 * coor = bd09ll：百度经纬度坐标，在国测局坐标基础之上二次加密而来
	 * coor = gcj02：国测局02坐标，在原始GPS坐标基础上，按照国家测绘行业统一要求，加密后的坐标
	 */
	private String coor;
	
	/**
	 * 请求地址
	 */
	private String url = "https://api.map.baidu.com/location/ip";
	
	/**
	 * 请求接口
	 */
	private String interfaceUrl = "/location/ip";

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the ak
	 */
	public String getAk() {
		return ak;
	}

	/**
	 * @param ak the ak to set
	 */
	public void setAk(String ak) {
		this.ak = ak;
	}

	/**
	 * @return the sk
	 */
	public String getSk() {
		return sk;
	}

	/**
	 * @param sk the sk to set
	 */
	public void setSk(String sk) {
		this.sk = sk;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the coor
	 */
	public String getCoor() {
		return coor;
	}

	/**
	 * @param coor the coor to set
	 */
	public void setCoor(String coor) {
		this.coor = coor;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the interfaceUrl
	 */
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
}
