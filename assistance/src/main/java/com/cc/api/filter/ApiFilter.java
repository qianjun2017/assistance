/**
 * 
 */
package com.cc.api.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cc.api.bean.RequestBean;
import com.cc.api.enums.ApiVersionEnum;
import com.cc.common.exception.LogicException;
import com.cc.common.tools.AESTools;
import com.cc.common.tools.DESTools;
import com.cc.common.tools.DateTools;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.JwtTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.MD5Tools;
import com.cc.common.tools.RSATools;
import com.cc.common.tools.SHA1Tools;
import com.cc.common.tools.StringTools;
import com.cc.common.web.RequestContextUtil;
import com.cc.common.web.RequestWrapper;
import com.cc.common.web.Response;
import com.cc.common.web.ResponseWrapper;
import com.cc.leaguer.bean.LeaguerBean;
import com.cc.push.bean.FormBean;
import com.cc.system.config.bean.SystemConfigBean;

/**
 * @author Administrator
 *
 */
@WebFilter(urlPatterns="/api/*")
public class ApiFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Response<Object> result = new Response<Object>();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
		String body = requestWrapper.getBody();
		RequestBean requestBean = null;
		try {
			Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
			Map<String, Object> headerMap = new HashMap<String, Object>();
			while (headerNames.hasMoreElements()) {
				String key = (String) headerNames.nextElement();
				headerMap.put(key, httpServletRequest.getHeader(key));
			}
			requestBean = JsonTools.covertObject(headerMap, RequestBean.class);
		} catch (Exception e) {
			result.setMessage("请求参数格式错误");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(requestBean == null) {
			result.setMessage("请求参数格式错误");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(StringTools.isNullOrNone(requestBean.getAppCode())){
			result.setMessage("请求参数系统代码为空");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(StringTools.isNullOrNone(requestBean.getToken())){
			result.setMessage("请求参数访问令牌为空");
			result.setData(400);
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		LeaguerBean leaguerBean = JwtTools.validToken(requestBean.getToken(), LeaguerBean.class);
		if(leaguerBean==null){
			result.setMessage("无效令牌，请重新登录");
			result.setData(400);
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(requestBean.getTimestamp()==null){
			result.setMessage("请求参数时间戳为空");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		Date timestamp = DateTools.getDate(StringTools.toString(requestBean.getTimestamp()));
		if(timestamp == null){
			result.setMessage("请求参数时间戳格式错误");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(DateTools.getInterval(DateTools.now(), timestamp)>10000){
			result.setMessage("请求过期，请重新请求");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(StringTools.isNullOrNone(requestBean.getVersion())){
			result.setMessage("请求参数版本信息缺失");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		ApiVersionEnum apiVersionEnum = ApiVersionEnum.getApiVersionEnumByCode(requestBean.getVersion());
		if(apiVersionEnum == null){
			result.setMessage("请求参数版本信息不合法");
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		List<SystemConfigBean> keySystemConfigBeanList = SystemConfigBean.findAllByParams(SystemConfigBean.class, "propertyName", requestBean.getAppCode()+"."+requestBean.getVersion()+".key");
		if(ListTools.isEmptyOrNull(keySystemConfigBeanList)){
			result.setMessage("应用"+requestBean.getAppCode()+"不允许使用接口版本:"+apiVersionEnum.getName());
			response.getWriter().write(JsonTools.toJsonString(result));
			return;
		}
		if(ApiVersionEnum.V2.equals(apiVersionEnum)){
			if(StringTools.isNullOrNone(requestBean.getSign())){
				result.setMessage("请求参数缺少签名");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			String secretKey = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(secretKey)){
				result.setMessage("请先设置签名参数");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			StringBuffer buffer = new StringBuffer();
			String paramterString = getParamterString(body);
			if(!StringTools.isNullOrNone(paramterString)){
				buffer.append(paramterString);
			}
			buffer.append(secretKey);
			if(!MD5Tools.check(buffer.substring(0), requestBean.getSign())){
				result.setMessage("请求参数签名验证错误");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}else if(ApiVersionEnum.V3.equals(apiVersionEnum)){
			if(StringTools.isNullOrNone(requestBean.getSign())){
				result.setMessage("请求参数缺少签名");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			String secretKey = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(secretKey)){
				result.setMessage("请先设置签名参数");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			StringBuffer buffer = new StringBuffer();
			String paramterString = getParamterString(body);
			if(!StringTools.isNullOrNone(paramterString)){
				buffer.append(paramterString);
			}
			buffer.append(secretKey);
			if(!SHA1Tools.check(buffer.substring(0), requestBean.getSign())){
				result.setMessage("请求参数签名验证错误");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}else if(ApiVersionEnum.V4.equals(apiVersionEnum)){
			String privateKey = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(privateKey)){
				result.setMessage("请先设置RSA私钥");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			try {
				body = RSATools.privateDecrypt(body, privateKey);
			} catch (LogicException e) {
				result.setMessage(e.getErrContent());
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}else if(ApiVersionEnum.V5.equals(apiVersionEnum)){
			String key = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(key)){
				result.setMessage("请先设置DES密钥");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			try {
				body = DESTools.decrypt(body, key);
			} catch (LogicException e) {
				result.setMessage(e.getErrContent());
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}else if(ApiVersionEnum.V6.equals(apiVersionEnum)){
			String key = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(key)){
				result.setMessage("请先设置AES密钥");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			try {
				body = AESTools.decrypt(body, key);
			} catch (LogicException e) {
				result.setMessage(e.getErrContent());
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}else if(ApiVersionEnum.V7.equals(apiVersionEnum)){
			String clientIp = RequestContextUtil.getIpAddr(httpServletRequest);
			String ip = keySystemConfigBeanList.get(0).getPropertyValue();
			if(StringTools.isNullOrNone(ip)){
				result.setMessage("请先设置白名单ip");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
			if(!clientIp.equals(ip)){
				result.setMessage("非法访问IP禁止访问");
				response.getWriter().write(JsonTools.toJsonString(result));
				return;
			}
		}
		if(!StringTools.isNullOrNone(requestBean.getFormId())){
			String[] formIds = requestBean.getFormId().split(",");
			for(String formId: formIds){
				FormBean formBean = new FormBean();
				formBean.setFormId(formId);
				formBean.setUserId(leaguerBean.getId());
				formBean.setCreateTime(DateTools.now());
				formBean.save();
			}
		}
		Map<String, Object> bodyMap = JsonTools.toObject(body, HashMap.class);
		bodyMap.put("leaguerId", leaguerBean.getId());
		requestWrapper.setBody(JsonTools.toJsonString(bodyMap));
		ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);
		chain.doFilter(requestWrapper, responseWrapper);
		String responseData = responseWrapper.getResponseData();
		if(ApiVersionEnum.V2.equals(apiVersionEnum)){
			String secretKey = keySystemConfigBeanList.get(0).getPropertyValue();
			StringBuffer buffer = new StringBuffer();
			String paramterString = getParamterString(responseData);
			if(!StringTools.isNullOrNone(paramterString)){
				buffer.append(paramterString);
			}
			buffer.append(secretKey);
			responseWrapper.addHeader("sign", MD5Tools.encrypt(buffer.substring(0)));
		}else if(ApiVersionEnum.V3.equals(apiVersionEnum)){
			String secretKey = keySystemConfigBeanList.get(0).getPropertyValue();
			StringBuffer buffer = new StringBuffer();
			String paramterString = getParamterString(body);
			if(!StringTools.isNullOrNone(paramterString)){
				buffer.append(paramterString);
			}
			buffer.append(secretKey);
			responseWrapper.addHeader("sign", SHA1Tools.encrypt(buffer.substring(0)));
		}else if(ApiVersionEnum.V4.equals(apiVersionEnum)){
			String privateKey = keySystemConfigBeanList.get(0).getPropertyValue();
			responseData = RSATools.privateEncrypt(responseData, privateKey);
		}else if(ApiVersionEnum.V5.equals(apiVersionEnum)){
			String key = keySystemConfigBeanList.get(0).getPropertyValue();
			responseData = DESTools.encrypt(responseData, key);
		}else if(ApiVersionEnum.V6.equals(apiVersionEnum)){
			String key = keySystemConfigBeanList.get(0).getPropertyValue();
			responseData = AESTools.encrypt(responseData, key);
		}
		response.getWriter().write(responseData);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}
	
	/**
	 * 获取参数字符串
	 * @param body
	 * @return
	 */
	private String getParamterString(String body){
		Map<String, Object> map = JsonTools.toObject(body, HashMap.class);
		Iterator<String> iterator = map.keySet().iterator();
		List<String> list = new ArrayList<String>();
		while(iterator.hasNext()){
			list.add(iterator.next());
		}
		Collections.sort(list);
		StringBuffer buffer = new StringBuffer();
		for (String key: list) {
			buffer.append("&").append(key).append("=").append(StringTools.toString(map.get(key)));
		}
		if(buffer.length()>0){
			return buffer.substring(1);
		}
		return null;
	}

}
