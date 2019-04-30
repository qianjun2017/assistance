package com.cc.sms.tx.service;

import com.cc.sms.tx.http.request.TenXunSmsSendRequest;
import com.cc.sms.tx.http.response.TenXunSmsSendResponse;

public interface TenXunSmsService {

	/**
	 * 调用腾讯云短信服务  指定模板单发短信
	 * @param request
	 * @return
	 */
	TenXunSmsSendResponse smsSend(TenXunSmsSendRequest request);
}
