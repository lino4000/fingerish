/*package com.lino4000.fingerish.config;

import org.h2.server.web.WebServlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Console {

	@Bean
	ServletRegistrationBean<WebServlet> h2servletRegistration(){
		ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<WebServlet>( new WebServlet());
		registrationBean.addUrlMappings("/console/*");
        return registrationBean;
	}
}
*/