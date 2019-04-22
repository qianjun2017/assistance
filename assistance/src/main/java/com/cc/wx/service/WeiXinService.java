/**
 * 
 */
package com.cc.wx.service;

import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.request.AddTemplateRequest;
import com.cc.wx.http.request.DeleteTemplateRequest;
import com.cc.wx.http.request.MiniOpenidRequest;
import com.cc.wx.http.request.TemplateLibraryListRequest;
import com.cc.wx.http.request.TemplateLibraryRequest;
import com.cc.wx.http.request.TemplateListRequest;
import com.cc.wx.http.request.TemplateMessageRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.http.response.AddTemplateResponse;
import com.cc.wx.http.response.DeleteTemplateResponse;
import com.cc.wx.http.response.MiniOpenidResponse;
import com.cc.wx.http.response.TemplateLibraryListResponse;
import com.cc.wx.http.response.TemplateLibraryResponse;
import com.cc.wx.http.response.TemplateListResponse;
import com.cc.wx.http.response.TemplateMessageResponse;
import com.cc.wx.http.response.WXACodeResponse;

/**
 * @author Administrator
 *
 */
public interface WeiXinService {

	/**
	 * 获取用户对应的openid
	 * @param request
	 * @return
	 */
	MiniOpenidResponse queryMiniOpenid(MiniOpenidRequest request);
	
	/**
	 * 获取小程序调用凭证
	 * @param request
	 * @return
	 */
	AccessTokenResponse queryAccessToken(AccessTokenRequest request);
	
	/**
	 * 创建小程序二维码
	 * @param request
	 * @return
	 */
	WXACodeResponse createWXACode(WXACodeRequest request);
	
	/**
	 * 发送模板消息
	 * @param request
	 * @return
	 */
	TemplateMessageResponse sendTemplateMessage(TemplateMessageRequest request);
	
	/**
	 * 获取帐号下已存在的模板列表
	 * @param request
	 * @return
	 */
	TemplateListResponse queryTemplateList(TemplateListRequest request);
	
	/**
	 * 删除帐号下的某个模板
	 * @param request
	 * @return
	 */
	DeleteTemplateResponse deleteTemplate(DeleteTemplateRequest request);
	
	/**
	 * 获取小程序模板库标题列表
	 * @param request
	 * @return
	 */
	TemplateLibraryListResponse queryTemplateLibraryList(TemplateLibraryListRequest request);
	
	/**
	 * 获取模板库某个模板标题下关键词库
	 * @param request
	 * @return
	 */
	TemplateLibraryResponse queryTemplateLibrary(TemplateLibraryRequest request);
	
	/**
	 * 组合模板并添加至帐号下的个人模板库
	 * @param request
	 * @return
	 */
	AddTemplateResponse addTemplate(AddTemplateRequest request);
	
}
