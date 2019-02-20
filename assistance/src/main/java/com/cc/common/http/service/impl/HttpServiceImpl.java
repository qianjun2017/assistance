/**
 * 
 */
package com.cc.common.http.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.common.http.client.HttpClient;
import com.cc.common.http.service.HttpService;
import com.cc.common.tools.JsonTools;
import com.cc.common.tools.ListTools;
import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
@Service
public class HttpServiceImpl implements HttpService {

	@Autowired
	private HttpClient httpClient;

	@Override
	public String get(String url, Map<String, Object> paramMap, String encoding) {
		List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
		if (paramMap!=null && !paramMap.isEmpty()) {
			Iterator<String> iterator = paramMap.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = paramMap.get(key);
				NameValuePair mvp = null;
				if (value instanceof String) {
					mvp = new BasicNameValuePair(key, StringTools.toString(value));
				}
				if (mvp!=null) {
					nvpList.add(mvp);
				}
			}
		}
		String paramString = null;
		if (!ListTools.isEmptyOrNull(nvpList)) {
			try {
				paramString = EntityUtils.toString(new UrlEncodedFormEntity(nvpList, Consts.UTF_8));
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		HttpGet httpGet = null;
		if (!StringTools.isNullOrNone(paramString)) {
			httpGet = new HttpGet(url+"?"+paramString);
		}else {
			httpGet = new HttpGet(url);
		}
		CloseableHttpResponse response = null;
		try {
			response = httpClient.getObject().execute(httpGet);
			StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
    			if (entity != null) {
    				return EntityUtils.toString(entity);
    			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpGet.releaseConnection();
		}
		return null;
	}

	@Override
	public String post(String url, Map<String, Object> paramMap, String encoding) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(JsonTools.toJsonString(paramMap), Consts.UTF_8));
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.getObject().execute(httpPost);
			StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
    			if (entity != null) {
    				return EntityUtils.toString(entity);
    			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpPost.releaseConnection();
		}
		return null;
	}

	@Override
	public byte[] postForBytes(String url, Map<String, Object> paramMap, String encoding) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new StringEntity(JsonTools.toJsonString(paramMap), Consts.UTF_8));
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		CloseableHttpResponse response = null;
		try {
			response = httpClient.getObject().execute(httpPost);
			StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
            	HttpEntity entity = response.getEntity();
    			if (entity != null) {
    				InputStream is = entity.getContent();
    				ByteArrayOutputStream baos = new ByteArrayOutputStream();
    				byte[] buffer = new byte[100];
    				int rc = 0;
    				while ((rc = is.read(buffer, 0, 100)) > 0) {
    					baos.write(buffer, 0, rc);
    				}
    				byte[] byteArray = baos.toByteArray();
    				is.close();
    				baos.flush();
    				baos.close();
					return byteArray;
    			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response!=null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			httpPost.releaseConnection();
		}
		return null;
	}
}
