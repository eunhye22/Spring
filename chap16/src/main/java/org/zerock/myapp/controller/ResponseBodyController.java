package org.zerock.myapp.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor
// 이 컨트롤러는 Rest Controller (@RestController)가 아니라,
// 스프링의 MVC 패턴을 따르는 컨트롤러임 => 따라서, 핸들러 메소드가 문자열(String)을 반환하면
// 이는 곧 뷰의 이름이 됨 => MVC에 의해서 View(JSP) 호출
@RequestMapping("/controller/")
@Controller
public class ResponseBodyController {

	@ResponseBody	// 순수한 데이터를 반환하도록 해줌!!!
	@GetMapping("/responseBody")
	public String responseBody() {
		log.trace("responseBody() invoked.");
		
		
		return "home";		// view의 이름 반환
		
	} // responseBody
	
	
	@ResponseBody
	@GetMapping(path="/getTicket", produces=MediaType.APPLICATION_JSON_VALUE)
//	@GetMapping("/getTicket")	=> default는 XML파일	
//	public Ticket getTicket() {
	private Ticket getTicket() {
		log.trace("getTicket() invoked.");
		
		Ticket ticket = new Ticket();
		ticket.setTno(3737);
		ticket.setPrice(150000.);
		ticket.setGrade("S");
		
		return ticket;
		
	} // getTicket
	
} // end class
