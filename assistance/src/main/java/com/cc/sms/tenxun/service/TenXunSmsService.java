package com.cc.sms.tenxun.service;

import com.cc.sms.tenxun.http.request.TenXunSmsSendRequest;
import com.cc.sms.tenxun.http.response.TenXunSmsSendResponse;

public interface TenXunSmsService {

	/**
	 * 调用腾讯云短信服务  指定模板单发短信
	 * @param request
	 * @return
	 */
	TenXunSmsSendResponse smsSend(TenXunSmsSendRequest request);
}
