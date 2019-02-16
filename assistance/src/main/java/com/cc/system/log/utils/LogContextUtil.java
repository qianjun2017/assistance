/**
 * 
 */
package com.cc.system.log.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class LogContextUtil {

	private static final ThreadLocal<Map<String, Object>> logHolder = new ThreadLocal<Map<String,Object>>();
	
	public static void setLogMap(Map<String, Object> logMap){
		logHolder.set(logMap);
	}
	
	/**
	 * 获取操作日志
	 * @return
	 */
	public static Map<String, Object> getLogMap() {
		Map<String, Object> logMap = logHolder.get();
		if(logMap==null){
			logMap = new HashMap<String, Object>();
		}
		return logMap;
	}
	
	/**
	 * 设置操作内容
	 * @param operContent
	 */
	public static void setOperContent(String operContent) {
		Map<String, Object> logMap = logHolder.get();
		if(logMap==null){
			logMap = new HashMap<String, Object>();
		}
		logMap.put("operContent", operContent);
		logHolder.set(logMap);
	}
	
	/**
	 * 获取操作内容
	 * @return
	 */
	public static String getOperContent() {
		Map<String, Object> logMap = logHolder.get();
		if(logMap!=null && !logMap.isEmpty()){
			Object object = logMap.get("operContent");
			if(object!=null){
				return String.valueOf(object);
			}
		}
		return null;
	}

	/**
	 * 是否保存日志记录
	 * @return
	 */
	public static Boolean recordLog() {
		Map<String, Object> logMap = logHolder.get();
		if(logMap!=null && !logMap.isEmpty()){
			Object object = logMap.get("noRecordLog");
			if(object!=null){
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * 是否记录日志
	 * @param recordLog
	 */
	public static void setRecordLog(Boolean recordLog){
		Map<String, Object> logMap = logHolder.get();
		if(logMap==null){
			logMap = new HashMap<String, Object>();
		}
		if (recordLog){
			logMap.remove("noRecordLog");
		}else{
			logMap.put("noRecordLog", new Object());
		}
		logHolder.set(logMap);
	}
}
