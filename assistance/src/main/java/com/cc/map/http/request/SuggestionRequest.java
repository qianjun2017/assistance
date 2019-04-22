/**
 * 
 */
package com.cc.map.http.request;

/**
 * @author ws_yu
 *
 */
public class SuggestionRequest {

	/**
	 * 建议关键字
	 */
	private String query;
	
	/**
	 * 指定城市检索  
	 * 支持全国、省、城市及对应百度编码
	 */
	private String region;
	
	/**
	 * 取值为"true"，仅返回region中指定城市检索结果
	 */
	private Boolean cityLimit = Boolean.TRUE;
	
	/**
	 * 传入location参数后，返回结果将以距离进行排序
	 */
	private String location;
	
	/**
	 * 坐标类型       可选参数，用于标注请求中「location」参数使用的坐标类型
	 * 1（WGS84ll即GPS经纬度）
	 * 2（GCJ02ll即国测局经纬度坐标）
	 * 3（BD09ll即百度经纬度坐标）
	 * 4（BD09mc即百度米制坐标）
	 */
	private Integer coordType;
	
	/**
	 * 可选参数，添加后POI返回国测局经纬度坐标
	 * 若不传该参数，返回结果默认bd09ll（百度经纬度）
	 */
	private String retCoordtype;
	
	/**
	 * 返回数据格式，可选json、xml两种
	 */
	private String output = "json";
	
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
	 * 设置sn后该值必选
	 */
	private Long timestamp;
	
	/**
	 * 请求地址
	 */
	private String url = "http://api.map.baidu.com/place/v2/suggestion";
	
	/**
	 * 请求接口
	 */
	private String interfaceUrl = "/place/v2/suggestion";

	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the cityLimit
	 */
	public Boolean getCityLimit() {
		return cityLimit;
	}

	/**
	 * @param cityLimit the cityLimit to set
	 */
	public void setCityLimit(Boolean cityLimit) {
		this.cityLimit = cityLimit;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the coordType
	 */
	public Integer getCoordType() {
		return coordType;
	}

	/**
	 * @param coordType the coordType to set
	 */
	public void setCoordType(Integer coordType) {
		this.coordType = coordType;
	}

	/**
	 * @return the retCoordtype
	 */
	public String getRetCoordtype() {
		return retCoordtype;
	}

	/**
	 * @param retCoordtype the retCoordtype to set
	 */
	public void setRetCoordtype(String retCoordtype) {
		this.retCoordtype = retCoordtype;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
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
	 * @return the timestamp
	 */
	public Long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
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
