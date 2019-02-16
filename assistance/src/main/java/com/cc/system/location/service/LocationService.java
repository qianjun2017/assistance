/**
 * 
 */
package com.cc.system.location.service;

import java.util.Map;

import com.cc.common.web.Tree;
import com.cc.system.location.bean.LocationBean;
import com.cc.system.location.result.LocationResult;

/**
 * @author Administrator
 *
 */
public interface LocationService {

	/**
	 * 保存地区
	 * @param locationBean
	 */
	void saveLocation(LocationBean locationBean);

	/**
	 * 删除地区
	 * @param id
	 */
	void deleteLocation(Long id);

	/**
	 * 查询地区树
	 * @return
	 */
	Tree<Map<String, Object>> queryLocationTree();
	
	/**
	 * 获取中文地区
	 * @param country 国家编码
	 * @param province 省市编码
	 * @param city 市区编码
	 * @return
	 */
	LocationResult queryLocation(String country, String province, String city);

	/**
	 * 获取中文地区
	 * @param locationId
	 * @return
	 */
	LocationResult queryLocation(Long locationId);
	
	/**
	 * 是否存在区县
	 * @param country 国家编码
	 * @param province 省编码
	 * @param city 市编码
	 * @param area 区县编码
	 * @return
	 */
	Boolean hasLocation(String country, String province, String city, String area);
	
	/**
	 * 是否存在市
	 * @param country 国家编码
	 * @param province 省编码
	 * @param city 市编码
	 * @return
	 */
	Boolean hasLocation(String country, String province, String city);
	
	/**
	 * 是否存在省
	 * @param country 国家编码
	 * @param province 省编码
	 * @return
	 */
	Boolean hasLocation(String country, String province);
	
	/**
	 * 是否存在国家
	 * @param country 国家编码
	 * @return
	 */
	Boolean hasLocation(String country);
	
	/**
	 * 保存区县
	 * @param country 国家编码
	 * @param province 省编码
	 * @param city 市编码
	 * @param area 区县编码
	 * @return
	 */
	Long saveLocation(String country, String province, String city, String area);
	
	/**
	 * 保存市
	 * @param country 国家编码
	 * @param province 省编码
	 * @param city 市编码
	 * @return
	 */
	Long saveLocation(String country, String province, String city);
	
	/**
	 * 保存省
	 * @param country 国家编码
	 * @param province 省编码
	 * @return
	 */
	Long saveLocation(String country, String province);
	
	/**
	 * 保存国家
	 * @param country 国家编码
	 * @return
	 */
	Long saveLocation(String country);

}
