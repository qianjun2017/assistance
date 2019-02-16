/**
 * 
 */
package com.cc.system.shiro;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import com.cc.common.tools.StringTools;

/**
 * @author Administrator
 *
 */
public class MySessionManager extends DefaultWebSessionManager {

	private static final String AUTHORIZATION = "Authorization";
	
	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";
	
	public MySessionManager(){
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.web.session.mgt.DefaultWebSessionManager#getSessionId(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String id = httpServletRequest.getHeader(AUTHORIZATION);
		if(!StringTools.isNullOrNone(id)){
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return id;
		}else {
			return super.getSessionId(request, response);
		}
	}
}
