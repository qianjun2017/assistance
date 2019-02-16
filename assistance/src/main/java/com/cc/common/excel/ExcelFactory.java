/**
 * 
 */
package com.cc.common.excel;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.StringTools;

/**
 * excel工厂
 * @author Administrator
 *
 */
public class ExcelFactory {

	public static Excel createExcel(String version, String name){
		if (StringTools.isAnyNullOrNone(new String[]{version,name})) {
			throw new LogicException("E0001","创建excel文件缺少参数");
		}
		if ("2003".equals(version)) {
			return new Excel2003(name);
		}
		return null;
	}
}
