package com.example.spring.mvc.web;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.example.spring.mvc.config.RootConfiguration;
import com.example.spring.mvc.config.WebConfiguration;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

public class DemoWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] { RootConfiguration.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfiguration.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	// 톰캣 9 미만은 UTF-8 기본 설정이 아니어서 주로 썼는데, 10부터는 지원해서 인코딩 바꿀거 아니면 굳이 이 메서드는 안써도 됨.
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		FilterRegistration characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, true, "/*");
		
		super.onStartup(servletContext);
	}

}
