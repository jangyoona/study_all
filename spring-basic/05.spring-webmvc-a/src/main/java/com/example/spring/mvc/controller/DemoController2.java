package com.example.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.spring.mvc.dto.Person;

// @Controller
public class DemoController2 {

//	@RequestMapping(path = { "/demo/param" })
//	public String processParam(HttpServletRequest req) {
//		
//		String data1 = req.getParameter("data1");
//		String sData2 = req.getParameter("data2");
//		int data2 = Integer.parseInt(sData2);
//		
//		System.out.println(data1 + " / "+ data2);
//
//		return "demo/result"; // /WEB-INF/views/ + demo/result + .jsp
//	}
	
	
//	@RequestMapping(path = { "/demo/param" })
//	public String processParam(@RequestParam("data1") String data1, @RequestParam("data2") int data2) {
//		
//		System.out.println(data1 + " / "+ data2);
//
//		return "demo/result"; // /WEB-INF/views/ + demo/result + .jsp
//	}
	
//	@RequestMapping(path = { "/demo/param" }) // GET, POST, ... 등 모든 요청을 받겠다.
//	@RequestMapping(path = {"/demo/param"}, method = RequestMethod.GET) // GET만 받겠따.
//	@GetMapping(path = { "/demo/param" })
//	public String processParam(String data1, int data2) { // 전달인자 이름은
//		
//		System.out.println(data1 + " / "+ data2);
//
//		return "demo/result"; // /WEB-INF/views/ + demo/result + .jsp
//	}
	
	
	
//	@GetMapping(path = { "/demo/param" }) // GET 요청만 받겠다.
//	public String processParam(@RequestParam("data1") String data3, @RequestParam("data2") int data4) {
//		
//		System.out.println(data3 + " / "+ data4);
//
//		return "demo/result"; // /WEB-INF/views/ + demo/result + .jsp
//	}
	
	
//	@GetMapping(path = { "/demo/param" }) // GET 요청만 받겠다.
//	public String processParam(String data1, int data2, Model model) { // model? : View(JSP)로 전달되는 객체
//		
//		System.out.println(data1 + " / "+ data2);
//
//		model.addAttribute("newdata1", data1); // model에 데이터를 저장하면 request 객체에 저장
//		model.addAttribute("newdata2", data2);
//		
//		return "demo/result"; // /WEB-INF/views/ + demo/result + .jsp
//	}
	
	
	@GetMapping("/demo/param") // GET 요청만 받겠다.
	public ModelAndView processParam(String data1, int data2) { // model? : View(JSP)로 전달되는 객체
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("newdata1", data1); // model에 데이터를 저장하면 request 객체에 저장
		mav.addObject("newdata2", data2);
		
		mav.setViewName("demo/result"); // /WEB-INF/views/ + demo/result + .jsp
		return mav;
	}
	
	
	////////////////////////////////////////////////////
//	@PostMapping(path = {"/demo/param"})
//	public String processParam2(String name, String phone, String email, int age) {
//	public String processParam2(Person person) {
//		System.out.println(person.toString());
//		return "demo/result";
//		
//	}
	
	@PostMapping("/demo/param")
	public String processParam2(Person person, Model model) { // 객체 타입의 전달인자는 자동으로 View(jsp)로 전달이 됨.
		
		System.out.println(person.toString());
		
		model.addAttribute("person2", person);
		
		return "demo/result";
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////
//	@GetMapping("/demo/redirect")
//	public String redirect() {
//		
//		return "redirect:/demo/redirect-target";
//	}
	
	@GetMapping(path = { "/demo/redirect" })
	public RedirectView redirect() {
		
		RedirectView rv = new RedirectView("/mvc-a/demo/redirect-target");
		return rv;
	}
	
	@GetMapping(path = { "/demo/redirect-target" }, produces = "text/palin;charset=utf-8")
	@ResponseBody // view를 사용하지 않고 controller 메서드의 반환 값을 바로 요청한 곳으로 응답하는 설정.
	public String redirectTarget() {
		
		return "redirect-target -> redirect 결과입니다."; // jsp 경로가 아니라, 이 문자열 자체가 응답 데이터가 되는 거임.
	}
	
	
	
	@GetMapping(path = { "/demo/forward" })
	public String forward() {
		
		return "forward:/demo/forward-target";
	}
	
	@GetMapping(path = { "/demo/forward-target" }, produces = "text/palin;charset=utf-8")
	@ResponseBody // view를 사용하지 않고 controller 메서드의 반환 값을 바로 요청한 곳으로 응답하는 설정.
	public String forwardTarget() {
		
		return "forward-target -> forward 결과입니다."; // jsp 경로가 아니라, 이 문자열 자체가 응답 데이터가 되는 거임.
	}
	
	
}
