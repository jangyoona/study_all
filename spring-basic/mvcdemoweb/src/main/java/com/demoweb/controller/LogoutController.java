package com.demoweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutController implements Controller{

	@Override
	public ActionResult handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		// 요청 데이터 읽기
		// 요청 처리
		HttpSession session = req.getSession(); //서블릿에서는 session이 내장 객체가 아니므로 request 객체로부터 유도함.
		session.removeAttribute("loginuser"); // session에서 loginuser로 저장된 데이터 제거
		
		ActionResult ar = new ActionResult("/mvcdemoweb/home", true); // 서블릿 이동 -> redirect
		return ar;
	}
}
