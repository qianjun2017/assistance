/**
 * 
 */
package com.cc.system.shiro;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;

/**
 * @author Administrator
 *
 */
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {

	/**
	 * 对ShiroFilter来说，需要直接忽略的请求
	 */
	private Set<String> ignoreExt;
	
	public MyShiroFilterFactoryBean() {
		super();
		ignoreExt = new HashSet<>();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.spring.web.ShiroFilterFactoryBean#createInstance()
	 */
	@Override
	protected AbstractShiroFilter createInstance() throws Exception {
		SecurityManager securityManager = getSecurityManager();
		if (securityManager == null) {
			throw new BeanInitializationException("SecurityManager property must be set.");
		}
		if (!(securityManager instanceof WebSecurityManager)) {
			throw new BeanInitializationException("The security manager does not implement the WebSecurityManager interface.");
		}
		FilterChainManager manager = createFilterChainManager();
		PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
		chainResolver.setFilterChainManager(manager);
		return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
	}

	private final class MySpringShiroFilter extends AbstractShiroFilter {
		
		protected MySpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
			super();
			if (webSecurityManager == null) {
				throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
			}
			setSecurityManager(webSecurityManager);
			if (resolver != null) {
				setFilterChainResolver(resolver);
			}
		}

		/* (non-Javadoc)
		 * @see org.apache.shiro.web.servlet.AbstractShiroFilter#doFilterInternal(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
		 */
		@Override
		protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
				FilterChain chain) throws ServletException, IOException {
			HttpServletRequest request = (HttpServletRequest)servletRequest;
			String str = request.getRequestURI().toLowerCase();
			boolean flag = true;
			int idx = 0;
			if(( idx = str.indexOf(".")) > 0){
				str = str.substring(idx);
				if(ignoreExt.contains(str.toLowerCase())){
					flag = false;
				}
			}
			if (flag) {
				super.doFilterInternal(servletRequest, servletResponse, chain);
			}else {
				chain.doFilter(servletRequest, servletResponse);
			}
		}
	}
}
