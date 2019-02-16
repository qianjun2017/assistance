/**
 * 
 */
package com.cc.image.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.file.service.FileService;
import com.cc.image.service.ImageService;

/**
 * @author Administrator
 *
 */
@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
	private FileService fileService;

	/* (non-Javadoc)
	 * @see com.cc.image.service.ImageService#uploadImages(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void uploadImages(HttpServletRequest request, HttpServletResponse response) {
		fileService.uploadFile(request, response);
	}

}
