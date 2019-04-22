/**
 * 
 */
package com.cc.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.cc.common.tools.JsonTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.StringTools;
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
import com.cc.wx.service.WeiXinService;

/**
 * @author Administrator
 *
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
	
	@Autowired
	private HttpService httpService;
	
	@Override
	public MiniOpenidResponse queryMiniOpenid(MiniOpenidRequest request) {
		MiniOpenidResponse response = new MiniOpenidResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getCode())) {
			response.setMessage("请输入登录code");
			return response;
		}
		if (StringTools.isNullOrNone(request.getSecret())) {
			response.setMessage("请输入应用secret");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", request.getAppid());
		paramMap.put("secret", request.getSecret());
		paramMap.put("js_code", request.getCode());
		paramMap.put("grant_type", request.getGrantType());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, MiniOpenidResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public AccessTokenResponse queryAccessToken(AccessTokenRequest request) {
		AccessTokenResponse response = new AccessTokenResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getSecret())) {
			response.setMessage("请输入应用secret");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", request.getAppid());
		paramMap.put("secret", request.getSecret());
		paramMap.put("grant_type", request.getGrantType());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, AccessTokenResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public WXACodeResponse createWXACode(WXACodeRequest request) {
		WXACodeResponse response = new WXACodeResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getScene())) {
			response.setMessage("请输入小程序码参数");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("scene", request.getScene());
		if(!StringTools.isNullOrNone(request.getPage())){
			paramMap.put("page", request.getPage());
		}
		if(request.getWidth()!=null){
			paramMap.put("width", request.getWidth());
		}
		if(request.getAutoColor()!=null){
			paramMap.put("auto_color", request.getAutoColor());
		}
		if(request.getIsHyaline()!=null){
			paramMap.put("is_hyaline", request.getIsHyaline());
		}
		if(request.getLineColor()!=null){
			paramMap.put("line_color", request.getLineColor());
		}
		byte[] httpResponse = httpService.postForBytes(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (httpResponse==null) {
			response.setMessage("http返回值为空");
			return response;
		}
		try{
			response = JsonTools.toObject(new String(httpResponse), WXACodeResponse.class);
			response.setMessage(response.getErrmsg());
			response.setSuccess(Boolean.FALSE);
		} catch (Exception e) {
			response.setAcode(httpResponse);
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

	@Override
	public TemplateMessageResponse sendTemplateMessage(TemplateMessageRequest request) {
		TemplateMessageResponse response = new TemplateMessageResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getToUser())) {
			response.setMessage("请选择接收用户");
			return response;
		}
		if (StringTools.isNullOrNone(request.getTemplateId())) {
			response.setMessage("请选择下发消息模板");
			return response;
		}
		if (StringTools.isNullOrNone(request.getFormId())) {
			response.setMessage("请输入表单formId或者支付prepayId");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TemplateMessageResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public TemplateListResponse queryTemplateList(TemplateListRequest request) {
		TemplateListResponse response = new TemplateListResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (request.getOffset()==null) {
			response.setMessage("请输入查询偏移量");
			return response;
		}
		if (request.getCount()==null) {
			response.setMessage("请输入查询数量");
			return response;
		}
		if(request.getCount()>20){
			response.setMessage("每次获取数量不能超过20条");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TemplateListResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public DeleteTemplateResponse deleteTemplate(DeleteTemplateRequest request) {
		DeleteTemplateResponse response = new DeleteTemplateResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getTemplateId())) {
			response.setMessage("请输入要删除的模板id");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, DeleteTemplateResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public TemplateLibraryListResponse queryTemplateLibraryList(TemplateLibraryListRequest request) {
		TemplateLibraryListResponse response = new TemplateLibraryListResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (request.getOffset()==null) {
			response.setMessage("请输入查询偏移量");
			return response;
		}
		if (request.getCount()==null) {
			response.setMessage("请输入查询数量");
			return response;
		}
		if(request.getCount()>20){
			response.setMessage("每次获取数量不能超过20条");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TemplateLibraryListResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public TemplateLibraryResponse queryTemplateLibrary(TemplateLibraryRequest request) {
		TemplateLibraryResponse response = new TemplateLibraryResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getId())) {
			response.setMessage("请输入模板标题id");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, TemplateLibraryResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

	@Override
	public AddTemplateResponse addTemplate(AddTemplateRequest request) {
		AddTemplateResponse response = new AddTemplateResponse();
		if (StringTools.isNullOrNone(request.getAccessToken())) {
			response.setMessage("请输入小程序调用凭证");
			return response;
		}
		if (StringTools.isNullOrNone(request.getId())) {
			response.setMessage("请选择模板");
			return response;
		}
		if(request.getKeywordIdList()==null || request.getKeywordIdList().length==0){
			response.setMessage("请选择模板关键词");
			return response;
		}
		if(request.getKeywordIdList().length>10){
			response.setMessage("模板关键词不能多于10个");
			return response;
		}
		Map<String, Object> paramMap = JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class);
		String httpResponse = httpService.post(request.getUrl()+"?access_token="+request.getAccessToken(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = JsonTools.toObject(httpResponse, AddTemplateResponse.class);
		if(response.getErrcode()==null || response.getErrcode()==0){
			response.setSuccess(Boolean.TRUE);
		}else{
			response.setMessage(response.getErrmsg());
		}
		return response;
	}

}
