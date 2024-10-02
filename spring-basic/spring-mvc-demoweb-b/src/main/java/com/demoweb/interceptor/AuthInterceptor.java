package com.demoweb.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.demoweb.dto.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor{
	
	
	// Controller를 호출하기 전, 호출되는 메서드 ( 주로 preHandle 을 사용함. 나머지는 잘 안쓴대 )
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("AuthInterceptor.preHandle");
		
		MemberDto member = (MemberDto)request.getSession().getAttribute("loginuser");
		String uri = request.getRequestURI();
		if(uri.contains("write") || uri.contains("edit") || uri.contains("delete")) {
			if(member == null) {
				response.sendRedirect("/spring-demoweb/account/login");
				return false; // false? : 컨트롤러로 호출을 중단하라는 뜻
			}
		}
		
		return true; // ture? : 컨트롤러로 호출 수행을 계속 하라는 뜻
		
	}
	
	// Controller를 호출한 후에, 호출되는 메서드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
//		System.out.println("AuthInterceptor.postHandle");
		
	}
	
	
	// View를 호출한 후에, 호출되는 메서드 (모든 작업이 끝난 후에 호출)
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
							    throws Exception {
//		System.out.println("AuthInterceptor.afterCompletion");
		
	}
	

}
