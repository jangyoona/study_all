package com.demoweb.controller;

import com.demoweb.dto.MemberDto;
import com.demoweb.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginController implements Controller {
	
	@Override
	public ActionResult handleRequest(HttpServletRequest req, HttpServletResponse resp) {
		// 요청 데이터 읽기
		// 요청 처리
		String method = req.getMethod().toLowerCase();
		if (method.equals("get")) {
			return doGet(req, resp);
		} else {
			return doPost(req, resp);
		}
	}
	
	
	
	public ActionResult doGet(HttpServletRequest req, HttpServletResponse resp) {
		ActionResult ar = new ActionResult("account/login", false); 
		return ar;
	}
	
	public ActionResult doPost(HttpServletRequest req, HttpServletResponse resp) {
		
		// 1. 요청 데이터 읽기
		String memberId = req.getParameter("memberId");
		String passwd = req.getParameter("passwd");
		
		// 2. 요청 데이터 처리 ( 데이터베이스에서 데이터 조회 )
		// 2-1. 데이터베이스에서 데이터 조회
		
		// memberId와 passwd로 회원정보(모든 컬럼) 조회하고 결과를 JDBC 코드 구현
		MemberDto member = new MemberDto();
		member.setMemberId(memberId);
		member.setPasswd(passwd);
		
		AccountService accountService = new AccountService();
		MemberDto loginMember = accountService.loginMember(member);
		
		ActionResult ar = null;
		if (loginMember != null) { // 로그인 성공 ( 사용자가 입력한 id, password에 해당하는 데이터가 조회된 경우 )
			// 2-2. 로그인 처리 --> 세션에 데이터 저장
			HttpSession session = req.getSession(); // 서블릿에는 내장 객체 개념이 없기 때문에 request 객체로부터 session 객체 유도
			session.setAttribute("loginuser", loginMember);
			
			// 3-1. home으로 이동 ( 다른 서블릿으로 이동 -> redirect로 이동 )
			ar = new ActionResult("/mvcdemoweb/home", true); 
		} else { // 로그인 실패 ( 사용자가 입력한 id, password에 해당하는 데이터가 없는 경우 ) & 실패 후 사용자가 F5(새로고침) 했을 때 다시 전송하는 것을 막기위해 redirect 사용
			ar = new ActionResult("/mvcdemoweb/account/login?loginfail=true", true); 
		}
		return ar;
	}
	
	
}