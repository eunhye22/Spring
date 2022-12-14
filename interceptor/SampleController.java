package org.zerock.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/sample/")
@Controller
public class SampleController {

	@GetMapping("/doA")
	public void doA() {
		log.trace("doA() invoked.");
		
	} // doA
	
} // end class
