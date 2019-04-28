/**
 * 
 */
package com.cc.wx.http.request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author ws_yu
 *
 */
public class SceneInfo {

	@JsonProperty(value="store_info")
	private StoreInfo storeInfo;

	/**
	 * @return the storeInfo
	 */
	public StoreInfo getStoreInfo() {
		return storeInfo;
	}

	/**
	 * @param storeInfo the storeInfo to set
	 */
	public void setStoreInfo(StoreInfo storeInfo) {
		this.storeInfo = storeInfo;
	}
}
