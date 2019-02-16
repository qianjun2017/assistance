/**
 * 
 */
package com.cc.image.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 *
 */
public interface ImageService {

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 */
	void uploadImages(HttpServletRequest request, HttpServletResponse response);
}
