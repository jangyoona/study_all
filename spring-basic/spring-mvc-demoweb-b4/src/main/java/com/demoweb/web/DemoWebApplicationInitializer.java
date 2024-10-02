package com.demoweb.web;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.demoweb.config.RootConfiguration;
import com.demoweb.config.WebConfiguration;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration.Dynamic;
import jakarta.servlet.annotation.MultipartConfig;

// web.xml 파일을 대체하는 클래스
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
		
		application = servletContext;
		
		FilterRegistration characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, true, "/*");
		
		super.onStartup(servletContext);
	}

	private ServletContext application;
	
	@Override
	protected void customizeRegistration(Dynamic registration) { // web.xml 파일의 <multipart-config> 설정 code버전
		
		String tempPath = application != null ? application.getRealPath("/board-temp") : "/";
		System.out.println(tempPath);
		MultipartConfigElement element = new MultipartConfigElement(tempPath, 10485760, 52428800, 1048576);
		registration.setMultipartConfig(element);
		
		
	}
}
