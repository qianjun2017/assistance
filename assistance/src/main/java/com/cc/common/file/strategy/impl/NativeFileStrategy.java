/**
 * 
 */
package com.cc.common.file.strategy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import com.cc.common.exception.LogicException;
import com.cc.common.file.strategy.FileStrategy;
import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public class NativeFileStrategy extends FileStrategy {

	@Override
	public String uploadFile(InputStream inputStream, String path, String fileName) {
		File dir = new File(path);
		if (!dir.exists() && !dir.mkdirs()) {
			throw new LogicException("E001", "创建文件目录失败");
		}
		try {
			OutputStream outputStream = new FileOutputStream(new File(path, fileName));
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buffer)) > 0){
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.getAbsolutePath(null,fileName)+"/"+fileName;
	}

	@Override
	public void downloadFile(OutputStream outputStream, String path, String fileName) {
		File file = new File(path, fileName);
		if (!file.exists()) {
			throw new LogicException("E001", "文件不存在或已被删除");
		}
		try {
			InputStream inputStream = new FileInputStream(file);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buffer)) > 0){
				outputStream.write(buffer, 0, len);
			}
			outputStream.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.cc.common.file.strategy.FileStrategy#getAbsolutePath(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public String getAbsolutePath(HttpServletRequest request, String fileName) {
		String path = super.getAbsolutePath(request, fileName);
		if (StringTools.isNullOrNone(path)) {
			throw new LogicException("E001", "请指定文件存放目录");
		}
		return request.getSession().getServletContext().getRealPath(path);
	}

}
