/**
 * 
 */
package com.cc.wx.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cc.common.tools.AESTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.MD5Tools;
import com.cc.common.tools.SHA1Tools;

import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.service.HttpService;
import com.cc.common.tools.StringTools;
import com.cc.common.tools.XmlTools;
import com.cc.interfaces.enums.InterfaceEnum;
import com.cc.interfaces.service.InterfaceService;
import com.cc.wx.http.request.AccessTokenRequest;
import com.cc.wx.http.request.AddTemplateRequest;
import com.cc.wx.http.request.CloseOrderRequest;
import com.cc.wx.http.request.DeleteTemplateRequest;
import com.cc.wx.http.request.MiniOpenidRequest;
import com.cc.wx.http.request.OpenidRequest;
import com.cc.wx.http.request.QueryOrderRequest;
import com.cc.wx.http.request.QueryRefundRequest;
import com.cc.wx.http.request.RefundRequest;
import com.cc.wx.http.request.TemplateLibraryListRequest;
import com.cc.wx.http.request.TemplateLibraryRequest;
import com.cc.wx.http.request.TemplateListRequest;
import com.cc.wx.http.request.TemplateMessageRequest;
import com.cc.wx.http.request.UnifiedOrderRequest;
import com.cc.wx.http.request.UserInfoRequest;
import com.cc.wx.http.request.WXACodeRequest;
import com.cc.wx.http.response.AccessTokenResponse;
import com.cc.wx.http.response.AddTemplateResponse;
import com.cc.wx.http.response.CloseOrderResponse;
import com.cc.wx.http.response.DeleteTemplateResponse;
import com.cc.wx.http.response.MiniOpenidResponse;
import com.cc.wx.http.response.OpenidResponse;
import com.cc.wx.http.response.QueryOrderResponse;
import com.cc.wx.http.response.QueryRefundResponse;
import com.cc.wx.http.response.RefundResponse;
import com.cc.wx.http.response.TemplateLibraryListResponse;
import com.cc.wx.http.response.TemplateLibraryResponse;
import com.cc.wx.http.response.TemplateListResponse;
import com.cc.wx.http.response.TemplateMessageResponse;
import com.cc.wx.http.response.UnifiedOrderResponse;
import com.cc.wx.http.response.UserInfoResponse;
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
	
	@Autowired
    private InterfaceService interfaceService;
	
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
	
	/* (non-Javadoc)
	 * @see com.cc.leaguer.http.service.WeiXinService#queryOpenid(com.cc.leaguer.http.request.OpenidRequest)
	 */
	@Override
	public OpenidResponse queryOpenid(OpenidRequest request) {
		OpenidResponse response = new OpenidResponse();
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
		Map<String, String> map = JsonTools.toObject(httpResponse, Map.class);
		if(map.containsKey("openid")){
			response.setSuccess(Boolean.TRUE);
			response.setOpenid(map.get("openid"));
			response.setAccessToken(map.get("access_token"));
			response.setRefreshToken(map.get("refresh_token"));
		}else{
			response.setMessage(map.get("errmsg"));
		}
		return response;
	}

	@Override
	public UserInfoResponse queryUserInfo(UserInfoRequest request) {
		UserInfoResponse response = new UserInfoResponse();
		if(StringTools.isNullOrNone(request.getAccessToken())){
			response.setMessage("请输入授权凭证");
			return response;
		}
		if(StringTools.isNullOrNone(request.getOpenid())){
			response.setMessage("请输入openid");
			return response;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("access_token", request.getAccessToken());
		paramMap.put("openid", request.getOpenid());
		paramMap.put("lang", request.getLang());
		String httpResponse = httpService.get(request.getUrl(), paramMap, "UTF-8");
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		Map<String, String> map = JsonTools.toObject(httpResponse, Map.class);
		if(map.containsKey("openid")){
			response.setSuccess(Boolean.TRUE);
			response.setOpenid(map.get("openid"));
			response.setNickname(map.get("nickname"));
			response.setSex(map.get("sex"));
			response.setProvince(map.get("province"));
			response.setCity(map.get("city"));
			response.setCountry(map.get("country"));
			response.setHeadimgurl(map.get("headimgurl"));
		}else{
			response.setMessage(map.get("errmsg"));
		}
		return response;
	}

	@Override
	public Boolean checkSignature(String token, String timestamp, String nonce, String signature) {
		if(StringTools.isAnyNullOrNone(new String[]{token, timestamp, nonce, signature})){
			return Boolean.FALSE;
		}
		String[] array = new String[]{token, timestamp, nonce};
		Arrays.sort(array);
		String str = String.join("", Arrays.asList(array));
		if(SHA1Tools.encrypt(str).equalsIgnoreCase(signature)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public UnifiedOrderResponse unifiedOrder(UnifiedOrderRequest request) {
		UnifiedOrderResponse response = new UnifiedOrderResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getMchId())) {
			response.setMessage("请输入商户号mchId");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNonceStr())) {
			request.setNonceStr(StringTools.getRandomCode(20));
		}
		if(!(StringTools.isNullOrNone(request.getSignType()) || request.getSignType().equalsIgnoreCase("MD5") || request.getSignType().equalsIgnoreCase("HMAC-SHA256"))) {
			response.setMessage("签名类型错误");
			return response;
		}
		if(StringTools.isNullOrNone(request.getBody())) {
			response.setMessage("请输入商品描述");
			return response;
		}
		if(StringTools.isNullOrNone(request.getOutTradeNo())) {
			response.setMessage("请输入商户订单号");
			return response;
		}
		if(request.getTotalFee()==null) {
			response.setMessage("请输入标价金额");
			return response;
		}
		if(StringTools.isNullOrNone(request.getSpbillCreateIp())) {
			response.setMessage("请输入终端IP");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNotifyUrl())) {
			response.setMessage("请输入回调地址");
			return response;
		}
		if(StringTools.isNullOrNone(request.getTradeType())) {
			response.setMessage("请输入交易类型");
			return response;
		}
		if("JSAPI".equals(request.getTradeType()) && StringTools.isNullOrNone(request.getOpenid())) {
			response.setMessage("请输入用户标识openid");
			return response;
		}
		if(StringTools.isNullOrNone(request.getKey())) {
			response.setMessage("请输入商户平台密钥");
			return response;
		}
		request.setSign(sign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class)));
		String requestBody = XmlTools.toXml(UnifiedOrderRequest.class, request);
		String httpResponse = httpService.postSoap(request.getUrl(), requestBody, "UTF-8");
		try{
			interfaceService.saveInterface(InterfaceEnum.WXUNIFIEDORDER, requestBody, httpResponse);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = XmlTools.toObject(httpResponse, UnifiedOrderResponse.class);
		if(response==null){
			response = new UnifiedOrderResponse();
			response.setMessage("http返回值为空");
		}else if(!"SUCCESS".equals(response.getReturnCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getReturnMsg());
		}else if(!checkSign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(response), HashMap.class), response.getSign())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage("签名校验失败");
		}else if(!"SUCCESS".equals(response.getResultCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getErrCodeDes());
		}else{
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}
	
	@Override
	public String sign(String signType, String key, Map<String, Object> param){
		List<String> paramList = new ArrayList<String>();
		Iterator<Entry<String, Object>> iterator = param.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> entry = iterator.next();
			if(!StringTools.isNullOrNone(StringTools.toString(entry.getValue()))){
				paramList.add(entry.getKey());
			}
		}
		Collections.sort(paramList);
		StringBuffer buffer = new StringBuffer();
		for(String p: paramList){
			buffer.append("&"+p+"="+param.get(p));
		}
		buffer.append("&key="+key);
		if(StringTools.isNullOrNone(signType) || "MD5".equalsIgnoreCase(signType)){
			return MD5Tools.encrypt(buffer.substring(1)).toUpperCase();
		}else{
			return HmacUtils.hmacSha256Hex(StringTools.toString(key), buffer.substring(1)).toUpperCase();
		}
	}

	@Override
	public Boolean checkSign(String signType, String key, Map<String, Object> param, String sign) {
		return sign(signType, key, param).equals(sign);
	}

	@Override
	public RefundResponse refund(RefundRequest request) {
		RefundResponse response = new RefundResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getMchId())) {
			response.setMessage("请输入商户号mchId");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNonceStr())) {
			request.setNonceStr(StringTools.getRandomCode(20));
		}
		if(!(StringTools.isNullOrNone(request.getSignType()) || request.getSignType().equalsIgnoreCase("MD5") || request.getSignType().equalsIgnoreCase("HMAC-SHA256"))) {
			response.setMessage("签名类型错误");
			return response;
		}
		if(StringTools.isAllNullOrNone(new String[]{request.getOutTradeNo(), request.getTransactionId()})){
			response.setMessage("请输入商户订单号或者微信订单号");
			return response;
		}
		if(StringTools.isNullOrNone(request.getOutRefundNo())) {
			response.setMessage("请输入商户退款单号");
			return response;
		}
		if(request.getTotalFee()==null) {
			response.setMessage("请输入订单金额");
			return response;
		}
		if(request.getRefundFee()==null) {
			response.setMessage("请输入退款金额");
			return response;
		}
		request.setSign(sign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class)));
		String requestBody = XmlTools.toXml(RefundRequest.class, request);
		String httpResponse = httpService.postSoap(request.getUrl(), requestBody, "UTF-8");
		try{
			interfaceService.saveInterface(InterfaceEnum.WXREFUNDORDER, requestBody, httpResponse);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = XmlTools.toObject(httpResponse, RefundResponse.class);
		if(response==null){
			response = new RefundResponse();
			response.setMessage("http返回值为空");
		}else if(!"SUCCESS".equals(response.getReturnCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getReturnMsg());
		}else if(!checkSign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(response), HashMap.class), response.getSign())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage("签名校验失败");
		}else if(!"SUCCESS".equals(response.getResultCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getErrCodeDes());
		}else{
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

	@Override
	public CloseOrderResponse closeOrder(CloseOrderRequest request) {
		CloseOrderResponse response = new CloseOrderResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getMchId())) {
			response.setMessage("请输入商户号mchId");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNonceStr())) {
			request.setNonceStr(StringTools.getRandomCode(20));
		}
		if(!(StringTools.isNullOrNone(request.getSignType()) || request.getSignType().equalsIgnoreCase("MD5") || request.getSignType().equalsIgnoreCase("HMAC-SHA256"))) {
			response.setMessage("签名类型错误");
			return response;
		}
		if(StringTools.isNullOrNone(request.getOutTradeNo())) {
			response.setMessage("请输入商户订单号");
			return response;
		}
		if(StringTools.isNullOrNone(request.getKey())) {
			response.setMessage("请输入商户平台密钥");
			return response;
		}
		request.setSign(sign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class)));
		String requestBody = XmlTools.toXml(UnifiedOrderRequest.class, request);
		String httpResponse = httpService.postSoap(request.getUrl(), requestBody, "UTF-8");
		try{
			interfaceService.saveInterface(InterfaceEnum.WXCLOSEORDER, requestBody, httpResponse);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = XmlTools.toObject(httpResponse, CloseOrderResponse.class);
		if(response==null){
			response = new CloseOrderResponse();
			response.setMessage("http返回值为空");
		}else if(!"SUCCESS".equals(response.getReturnCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getReturnMsg());
		}else if(!checkSign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(response), HashMap.class), response.getSign())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage("签名校验失败");
		}else if(!"SUCCESS".equals(response.getResultCode())){
			response.setSuccess(Boolean.FALSE);
			response.setMessage(response.getErrCodeDes());
		}else{
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

	@Override
	public String decrypt(String content, String key) {
		return AESTools.decrypt(content, MD5Tools.encrypt(key).toLowerCase());
	}

	@Override
	public QueryOrderResponse queryOrder(QueryOrderRequest request) {
		QueryOrderResponse response = new QueryOrderResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getMchId())) {
			response.setMessage("请输入商户号mchId");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNonceStr())) {
			request.setNonceStr(StringTools.getRandomCode(20));
		}
		if(!(StringTools.isNullOrNone(request.getSignType()) || request.getSignType().equalsIgnoreCase("MD5") || request.getSignType().equalsIgnoreCase("HMAC-SHA256"))) {
			response.setMessage("签名类型错误");
			return response;
		}
		if(StringTools.isAllNullOrNone(new String[]{request.getOutTradeNo(), request.getTransactionId()})) {
			response.setMessage("请输入微信订单号或者商户订单号");
			return response;
		}
		if(StringTools.isNullOrNone(request.getKey())) {
			response.setMessage("请输入商户平台密钥");
			return response;
		}
		request.setSign(sign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class)));
		String requestBody = XmlTools.toXml(QueryOrderRequest.class, request);
		String httpResponse = httpService.postSoap(request.getUrl(), requestBody, "UTF-8");
		try{
			interfaceService.saveInterface(InterfaceEnum.WXQUERYORDER, requestBody, httpResponse);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = XmlTools.toObject(httpResponse, QueryOrderResponse.class);
		if(response==null){
			response = new QueryOrderResponse();
			response.setMessage("http返回值为空");
		}else if(!"SUCCESS".equals(response.getReturnCode())){
			response.setMessage(response.getReturnMsg());
		}else if(!checkSign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(response), HashMap.class), response.getSign())){
			response.setMessage("签名校验失败");
		}else if(!"SUCCESS".equals(response.getResultCode())){
			response.setMessage(response.getErrCodeDes());
		}else{
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

	@Override
	public QueryRefundResponse queryRefund(QueryRefundRequest request) {
		QueryRefundResponse response = new QueryRefundResponse();
		if (StringTools.isNullOrNone(request.getAppid())) {
			response.setMessage("请输入应用appid");
			return response;
		}
		if (StringTools.isNullOrNone(request.getMchId())) {
			response.setMessage("请输入商户号mchId");
			return response;
		}
		if(StringTools.isNullOrNone(request.getNonceStr())) {
			request.setNonceStr(StringTools.getRandomCode(20));
		}
		if(!(StringTools.isNullOrNone(request.getSignType()) || request.getSignType().equalsIgnoreCase("MD5") || request.getSignType().equalsIgnoreCase("HMAC-SHA256"))) {
			response.setMessage("签名类型错误");
			return response;
		}
		if(StringTools.isAllNullOrNone(new String[]{request.getOutTradeNo(), request.getTransactionId(), request.getOutRefundNo(), request.getRefundId()})) {
			response.setMessage("请输入微信订单号、商户订单号、商户退款单号或者微信退款单号");
			return response;
		}
		if(StringTools.isNullOrNone(request.getKey())) {
			response.setMessage("请输入商户平台密钥");
			return response;
		}
		request.setSign(sign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(request), HashMap.class)));
		String requestBody = XmlTools.toXml(QueryRefundRequest.class, request);
		String httpResponse = httpService.postSoap(request.getUrl(), requestBody, "UTF-8");
		try{
			interfaceService.saveInterface(InterfaceEnum.WXQUERYREFUNDORDER, requestBody, httpResponse);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if (StringTools.isNullOrNone(httpResponse)) {
			response.setMessage("http返回值为空");
			return response;
		}
		response = XmlTools.toObject(httpResponse, QueryRefundResponse.class);
		if(response==null){
			response = new QueryRefundResponse();
			response.setMessage("http返回值为空");
		}else if(!"SUCCESS".equals(response.getReturnCode())){
			response.setMessage(response.getReturnMsg());
		}else if(!checkSign(request.getSignType(), request.getKey(), JsonTools.toObject(JsonTools.toJsonString(response), HashMap.class), response.getSign())){
			response.setMessage("签名校验失败");
		}else if(!"SUCCESS".equals(response.getResultCode())){
			response.setMessage(response.getErrCodeDes());
		}else{
			response.setSuccess(Boolean.TRUE);
		}
		return response;
	}

}
