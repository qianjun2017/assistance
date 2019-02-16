/**
 * 
 */
package com.cc.common.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * excel操作
 * @author Administrator
 *
 */
public interface Excel {
	
	/**
	 * 文件名称
	 * @return
	 */
	String getName();

	/**
	 * 创建sheet
	 * @param sheetName
	 */
	void createSheet(String sheetName);
	
	/**
	 * 插入表列头  第一个sheet
	 * @param titleMapList  map{enName:orderTime,cnName:订单时间,width:600}
	 */
	void addHeadRow(List<Map<String, Object>> titleMapList);
	
	/**
	 * 指定sheet插入表列头
	 * @param sheetName
	 * @param titleMapList
	 */
	void addHeadRow(String sheetName, List<Map<String, Object>> titleMapList);
	
	/**
	 * 增加数据行   第一个sheet
	 * @param dataMap
	 */
	void addDataRow(Map<String, Object> dataMap);
	
	/**
	 * 指定sheet插入数据行
	 * @param sheetName
	 * @param dataMap
	 */
	void addDataRow(String sheetName, Map<String, Object> dataMap);
	
	/**
	 * 将excel写入到输出流
	 * @param os
	 */
	void write(OutputStream os);
}
