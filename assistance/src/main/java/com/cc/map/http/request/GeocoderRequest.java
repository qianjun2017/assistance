/**
 * 
 */
package com.cc.map.http.request;

/**
 * @author ws_yu
 *
 */
public class GeocoderRequest {

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
	 * 是否召回传入坐标周边的poi，0为不召回，1为召回。当值为1时，默认显示周边1000米内的poi
	 */
	private Integer pois;
	
	/**
	 * poi召回半径，允许设置区间为0-1000米，超过1000米按1000米召回
	 */
	private Integer radius;
	
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
	 * 返回数据格式，可选json、xml两种
	 */
	private String output = "json";
	
	/**
	 * 将json格式的返回值通过callback函数返回以实现jsonp功能
	 */
	private String callback;
	
	/**
	 * 区别于pois参数，pois=0，不召回pois数据，但后端仍访问poi相应服务；extensions_poi=null时，后端不调用poi相关服务，可减少服务访问时延
	 */
	private String extensionsPoi;
	
	/**
	 * 当取值为true时，召回坐标周围最近的3条道路数据。区别于行政区划中的street参数
	 */
	private Boolean extensionsRoad = Boolean.TRUE;
	
	/**
	 * 当取值为true时，行政区划返回乡镇级数据
	 */
	private Boolean extensionsTown = Boolean.TRUE;
	
	/**
	 * 指定召回的新政区划语言类型。
	 * 召回行政区划语言list（全量支持的语言见示例）。
	 * 当language=local时，根据请求中坐标所对应国家的母语类型，自动选择对应语言类型的行政区划召回。
	 * 目前支持多语言的行政区划区划包含country、provence、city、district
	 */
	private String language;
	
	/**
	 * 是否自动填充行政区划
	 * 1填充，0不填充
	 * 填充：当服务按某种语言类别召回时，若某一行政区划层级数据未覆盖，则按照“英文→中文→本地语言”类别行政区划数据对该层级行政区划进行填充，保证行政区划数据召回完整性
	 */
	private Integer languageAuto;
	
	/**
	 * 是否访问最新版行政区划数据（仅对中国数据生效），1（访问），0（不访问）
	 * 老版本行政区划数据已不再维护，强烈建议latest_admin设置为1
	 */
	private Integer latestAdmin = 1;
	
	/**
	 * 请求地址
	 */
	private String url = "http://api.map.baidu.com/geocoder/v2/";
	
	/**
	 * 请求接口
	 */
	private String interfaceUrl = "/geocoder/v2/";

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
	 * @return the pois
	 */
	public Integer getPois() {
		return pois;
	}

	/**
	 * @param pois the pois to set
	 */
	public void setPois(Integer pois) {
		this.pois = pois;
	}

	/**
	 * @return the radius
	 */
	public Integer getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(Integer radius) {
		this.radius = radius;
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
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * @return the extensionsPoi
	 */
	public String getExtensionsPoi() {
		return extensionsPoi;
	}

	/**
	 * @param extensionsPoi the extensionsPoi to set
	 */
	public void setExtensionsPoi(String extensionsPoi) {
		this.extensionsPoi = extensionsPoi;
	}

	/**
	 * @return the extensionsRoad
	 */
	public Boolean getExtensionsRoad() {
		return extensionsRoad;
	}

	/**
	 * @param extensionsRoad the extensionsRoad to set
	 */
	public void setExtensionsRoad(Boolean extensionsRoad) {
		this.extensionsRoad = extensionsRoad;
	}

	/**
	 * @return the extensionsTown
	 */
	public Boolean getExtensionsTown() {
		return extensionsTown;
	}

	/**
	 * @param extensionsTown the extensionsTown to set
	 */
	public void setExtensionsTown(Boolean extensionsTown) {
		this.extensionsTown = extensionsTown;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the languageAuto
	 */
	public Integer getLanguageAuto() {
		return languageAuto;
	}

	/**
	 * @param languageAuto the languageAuto to set
	 */
	public void setLanguageAuto(Integer languageAuto) {
		this.languageAuto = languageAuto;
	}

	/**
	 * @return the latestAdmin
	 */
	public Integer getLatestAdmin() {
		return latestAdmin;
	}

	/**
	 * @param latestAdmin the latestAdmin to set
	 */
	public void setLatestAdmin(Integer latestAdmin) {
		this.latestAdmin = latestAdmin;
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
