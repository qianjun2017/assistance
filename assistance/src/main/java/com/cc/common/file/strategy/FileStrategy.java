/**
 * 
 */
package com.cc.common.file.strategy;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import com.cc.common.exception.LogicException;
import com.cc.common.file.config.FileConfig;
import com.cc.common.tools.StringTools;
import com.cc.common.utils.UUIDUtils;

/**
 * @author Administrator
 *
 */
public abstract class FileStrategy {
	
	/**
	 * 文件配置
	 */
	private FileConfig fileConfig;

	/**
	 * @return the fileConfig
	 */
	public FileConfig getFileConfig() {
		return fileConfig;
	}

	/**
	 * @param fileConfig the fileConfig to set
	 */
	public void setFileConfig(FileConfig fileConfig) {
		this.fileConfig = fileConfig;
	}

	/**
	 * 上传文件
	 * @param inputStream
	 * @param path 绝对目录
	 * @param fileName
	 * @return
	 */
	public abstract String uploadFile(InputStream inputStream, String path, String fileName);
	
	/**
	 * 下载文件
	 * @param outputStream
	 * @param path
	 * @param fileName
	 */
	public abstract void downloadFile(OutputStream outputStream, String path, String fileName);
	
	/**
	 * 检查文件扩展名
	 * @param fileName
	 */
	public void checkFileExt(String fileName){
		if (StringTools.isAllNullOrNone(new String[]{fileConfig.getAllowedExt(),fileConfig.getDeniedExt()})) {
			return;
		}
		int lastIndexOf = fileName.lastIndexOf(".");
		String ext = null;
		if (lastIndexOf!=-1) {
			ext = fileName.substring(lastIndexOf+1);
		}
		if (StringTools.isNullOrNone(ext)) {
			throw new LogicException("E001", "无法判断文件类型");
		}
		if (!StringTools.isNullOrNone(fileConfig.getAllowedExt())) {
			String[] allowedExts = fileConfig.getAllowedExt().split(",");
			boolean allowed = false;
			for (String allowedExt : allowedExts) {
				if (ext.equalsIgnoreCase(allowedExt)) {
					allowed = true;
				}
			}
			if(!allowed){
				throw new LogicException("E002", "文件类型不正确");
			}
		}
		if (!StringTools.isNullOrNone(fileConfig.getDeniedExt())) {
			String[] deniedExts = fileConfig.getDeniedExt().split(",");
			boolean denied = false;
			for (String deniedExt : deniedExts) {
				if (ext.equalsIgnoreCase(deniedExt)) {
					denied = true;
				}
			}
			if(denied){
				throw new LogicException("E002", "文件类型不正确");
			}
		}
	}
	
	/**
	 * 获取保存文件的文件夹
	 * @param request
	 * @param fileName
	 * @return
	 */
	public String getAbsolutePath(HttpServletRequest request, String fileName){
		String path = fileConfig.getPath();
		if (fileConfig.isDisperse()) {
			path = path + disperseFile(fileName);
		}
		return path;
	}
	
	/**
	 * 打散文件存放目录，防止单个文件夹存放过多文件
	 * @param fileName
	 * @return
	 */
	public String disperseFile(String fileName){
		int hashCode = fileName.hashCode();
		String dir1 = Integer.toHexString(hashCode & 0xF);
		String dir2 = Integer.toHexString(hashCode >>> 4 & 0xF);
		return "/"+dir1+"/"+dir2;
	}
	
	/**
	 * 重命名文件，防止文件重名
	 * @param fileName
	 * @return
	 */
	public String makeFileName(String fileName){
		return UUIDUtils.getUuid()+"_"+fileName;
	}
	
	/**
	 * 获取文件的真实名称
	 * @param fileName
	 * @return
	 */
	public String getRealFileName(String fileName){
		if (fileName.contains("_")) {
			return fileName.substring(fileName.indexOf("_")+1);
		}
		return fileName;
	}
}
