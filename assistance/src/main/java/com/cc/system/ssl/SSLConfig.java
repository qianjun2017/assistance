package com.cc.system.ssl;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.Ssl;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SSLConfig {
	
	@Value("${server.port}")
    private int serverPort;

    @Value("${server.http.port}")
    private int serverHttpPort;
    
    @Value("${server.ssl.key-store}")
    private String keyStore;
    
    @Value("${server.ssl.key-store-password}")
    private String keyStorePassword;
    
    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;
    
    @Value("${server.ssl.key-alias}")
    private String keyAlias;
    
    public EmbeddedServletContainerCustomizer containerCustomizer() {
    	return new EmbeddedServletContainerCustomizer(){

			@Override
			public void customize(ConfigurableEmbeddedServletContainer container) {
				Ssl ssl = new Ssl();
				ssl.setKeyStore(keyStore);
				ssl.setKeyPassword(keyStorePassword);
				ssl.setKeyStoreType(keyStoreType);
				ssl.setKeyAlias(keyAlias);
				container.setSsl(ssl);
				container.setPort(serverPort);
			}
    		
    	};
    }

	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
			}
			
		};
		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
		return tomcat;
	}

	private Connector initiateHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        //需要重定向的http端口
        connector.setPort(serverHttpPort);
        connector.setSecure(false);
        //设置重定向到https端口
        connector.setRedirectPort(serverPort);
        return connector;
	}
}
