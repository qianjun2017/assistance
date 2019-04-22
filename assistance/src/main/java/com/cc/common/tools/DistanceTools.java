/**
 * 
 */
package com.cc.common.tools;

/**
 * @author Administrator
 *
 */
public class DistanceTools {
	
	private static Double EARTH_RADIUS = 6378.137;
	
	private static Double rad(Double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 获取两个点之间的距离
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 */
	public static Double getDistanceBetween(Double longitude1, Double latitude1, Double longitude2, Double latitude2){
		Double radLat1 = rad(latitude1);
		Double radLat2 = rad(latitude2);
		Double a = radLat1 - radLat2;
		Double b = rad(longitude1) - rad(longitude2);
		Double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}
}
