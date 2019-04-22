/**
 * 
 */
package com.cc.common.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.cc.common.tools.StringTools;
import com.cc.common.tools.WebTools;

/**
 * @author Administrator
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	
	/**
	 * 请求body
	 */
	private String body;

	public RequestWrapper(HttpServletRequest request) {
		super(request);
		body = WebTools.readBody(request);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getInputStream()
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if(!StringTools.isNullOrNone(body)){
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
			ServletInputStream servletInputStream = new ServletInputStream(){

				@Override
				public boolean isFinished() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean isReady() {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void setReadListener(ReadListener listener) {
					
				}

				@Override
				public int read() throws IOException {
					return byteArrayInputStream.read();
				}
				
			};
			return servletInputStream;
		}else{
			return super.getInputStream();
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletRequestWrapper#getReader()
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}
	
	public String getBody(){
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(String body) {
		this.body = body;
	}

}
