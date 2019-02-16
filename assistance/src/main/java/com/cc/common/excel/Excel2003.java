/**
 * 
 */
package com.cc.common.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cc.common.exception.LogicException;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * 2003版excel
 * @author Administrator
 *
 */
public class Excel2003 implements Excel {
	
	private HSSFWorkbook excel;
	
	private Map<String, String[]> titlesMap;
	
	private String name;

	public Excel2003(String name){
		this.name = name;
		this.excel = new HSSFWorkbook();
		this.titlesMap = new HashMap<String, String[]>();
	}

	/* (non-Javadoc)
	 * @see com.cmcc.cq.base12582.common.excel.Excel#createSheet(java.lang.String)
	 */
	@Override
	public void createSheet(String sheetName) {
		this.excel.createSheet(sheetName);
	}

	@Override
	public void addHeadRow(List<Map<String, Object>> titleMapList) {
		String sheetName = this.excel.getSheetName(0);
		addHeadRow(sheetName, titleMapList);
	}

	@Override
	public void addHeadRow(String sheetName, List<Map<String, Object>> titleMapList) {
		HSSFSheet sheet = this.excel.getSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		if(ListTools.isEmptyOrNull(titleMapList)){
			return;
		}
		String[] titles = new String[titleMapList.size()];
		for (Map<String, Object> titleMap : titleMapList) {
			String enName = String.valueOf(titleMap.get("enName"));
			String cnName = String.valueOf(titleMap.get("cnName"));
			int width = 6000;
			Object widthObj = titleMap.get("width");
			if(widthObj!=null){
				String widthString = String.valueOf(widthObj);
				if(StringTools.isNullOrNone(widthString)){
					width = Integer.valueOf(widthString);
				}
			}
			int column = titleMapList.indexOf(titleMap);
			sheet.setColumnWidth(column, width);
			titles[column] = enName;
			HSSFCell cell = row.createCell(column);
			cell.setCellValue(cnName);
		}
		this.titlesMap.put(sheetName, titles);
	}

	@Override
	public void addDataRow(Map<String, Object> dataMap) {
		String sheetName = this.excel.getSheetName(0);
		addDataRow(sheetName, dataMap);
	}

	@Override
	public void addDataRow(String sheetName, Map<String, Object> dataMap) {
		String[] titles = titlesMap.get(sheetName);
		if (StringTools.isAnyNullOrNone(titles)) {
			return;
		}
		HSSFSheet sheet = this.excel.getSheet(sheetName);
		int lastRowNum = sheet.getLastRowNum();
		HSSFRow row = sheet.createRow(lastRowNum+1);
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			HSSFCell cell = row.createCell(i);
			if (dataMap.containsKey(title)) {
				Object value = dataMap.get(title);
				cell.setCellValue(String.valueOf(value));
			}
		}
	}

	@Override
	public void write(OutputStream os) {
		try {
			this.excel.write(os);
		} catch (IOException e) {
			throw new LogicException("E0001", "excel文件写入流异常");
		}
	}

	@Override
	public String getName() {
		return this.name;
	}

}
