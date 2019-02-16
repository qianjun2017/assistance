/**
 * 
 */
package com.cc.common.file.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.cc.common.exception.LogicException;
import com.cc.common.file.service.FileService;
import com.cc.common.file.strategy.FileStrategy;

/**
 * @author Administrator
 *
 */
@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileStrategy strategy;
	
	@Override
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> urls = new ArrayList<String>();
			List<MultipartFile> fileList = ((MultipartHttpServletRequest)request).getFiles("file");
			for (MultipartFile file : fileList) {
				String fileName = file.getOriginalFilename();
				int lastIndex = fileName.lastIndexOf("\\");
				if(lastIndex != -1) {
					fileName = fileName.substring(lastIndex + 1);
				}
				strategy.checkFileExt(fileName);
				fileName = strategy.makeFileName(fileName);
				urls.add(strategy.uploadFile(file.getInputStream(), strategy.getAbsolutePath(request, fileName), fileName));
			}
			request.setAttribute("urls", urls);
		} catch (IOException e) {
			e.printStackTrace();
			throw new LogicException("E004", "文件上传异常");
		}
	}

	@Override
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String fileName = request.getParameter("fileName");
		try {
			strategy.downloadFile(response.getOutputStream(), strategy.getAbsolutePath(request, fileName), fileName);
			fileName = strategy.getRealFileName(fileName);
			response.setHeader("content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new LogicException("E001", "文件下载异常");
		}
	}
}
