/**
 * 
 */
package com.cc.map.http.request;

import com.cc.map.enums.SearchTypeEnum;

/**
 * @author ws_yu
 *
 */
public class SearchRequest {
	
	/**
	 * 检索关键字
	 */
	private String query;
	
	/**
	 * 检索分类偏好，与q组合进行检索，多个分类以","分隔（POI分类），如果需要严格按分类检索，请通过query参数设置
	 */
	private String tag;
	
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
	 * poi的uid  地点详情检索服务
	 */
	private String uid;
	
	/**
	 * uid的集合，最多可以传入10个uid，多个uid之间用英文逗号分隔  地点详情检索服务
	 */
	private String uids;
	
	/**
	 * 检索矩形区域，多组坐标间以","分隔  38.76623,116.43213,39.54321,116.46773 lat,lng(左下角坐标),lat,lng(右上角坐标)
	 */
	private String bounds;
	
	/**
	 * 圆形区域检索中心点，不支持多个点
	 */
	private String location;
	
	/**
	 * 圆形区域检索半径，单位为米。(当半径过大，超过中心点所在城市边界时，会变为城市范围检索，检索范围为中心点所在城市）  1000
	 */
	private String radius;
	
	/**
	 * 是否严格限定召回结果在设置检索半径范围内。true（是），false（否）。设置为true时会影响返回结果中total准确性及每页召回poi数量，我们会逐步解决此类问题
	 */
	private Boolean radiusLimit = Boolean.TRUE;
	
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
	 * 检索结果详细程度。取值为1 或空，则返回基本信息；取值为2，返回检索POI详细信息
	 */
	private String scope = "1";
	
	/**
	 * 检索过滤条件。当scope取值为2时，可以设置filter进行排序
	 */
	private String filter;
	
	/**
	 * 单次召回POI数量，默认为10条记录，最大返回20条。多关键字检索时，返回的记录数为关键字个数*page_size
	 */
	private Integer pageSize;
	
	/**
	 * 分页页码，默认为0,0代表第一页，1代表第二页，以此类推。常与page_size搭配使用
	 */
	private Integer pageNum;

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
	 * 地点检索类型
	 */
	private String type;
	
	/**
	 * 请求地址
	 */
	private String url = "http://api.map.baidu.com/place/v2/";
	
	/**
	 * 请求接口
	 */
	private String interfaceUrl = "/place/v2/";

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
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.getSearchTypeEnumByCode(type);
		switch (searchTypeEnum) {
			case DETAIL:
				return url + "detail";
	
			default:
				return url + "search";
		}
	}

	/**
	 * @return the interfaceUrl
	 */
	public String getInterfaceUrl() {
		SearchTypeEnum searchTypeEnum = SearchTypeEnum.getSearchTypeEnumByCode(type);
		switch (searchTypeEnum) {
			case DETAIL:
				return interfaceUrl + "detail";
	
			default:
				return interfaceUrl + "search";
		}
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
	 * @return the uids
	 */
	public String getUids() {
		return uids;
	}

	/**
	 * @param uids the uids to set
	 */
	public void setUids(String uids) {
		this.uids = uids;
	}

	/**
	 * @return the bounds
	 */
	public String getBounds() {
		return bounds;
	}

	/**
	 * @param bounds the bounds to set
	 */
	public void setBounds(String bounds) {
		this.bounds = bounds;
	}

	/**
	 * @return the radius
	 */
	public String getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(String radius) {
		this.radius = radius;
	}

	/**
	 * @return the radiusLimit
	 */
	public Boolean getRadiusLimit() {
		return radiusLimit;
	}

	/**
	 * @param radiusLimit the radiusLimit to set
	 */
	public void setRadiusLimit(Boolean radiusLimit) {
		this.radiusLimit = radiusLimit;
	}

	/**
	 * @return the scope
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * @param scope the scope to set
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNum
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
}
