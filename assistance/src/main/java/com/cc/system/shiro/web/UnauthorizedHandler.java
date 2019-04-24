package com.cc.system.shiro.web;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cc.common.web.Response;

@ControllerAdvice
public class UnauthorizedHandler {

	/**
	 * 无权限异常处理器
	 * @return
	 */
	@ExceptionHandler(value = UnauthorizedException.class)
	public Response<String> unauth(){
		Response<String> response = new Response<String>();
		response.setMessage("没有权限");
		return response;
	}
}
