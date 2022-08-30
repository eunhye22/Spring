package org.zerock.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor


@RequestMapping("/requestbody/")
@RestController
public class RequestBodyController {
	
	
	@GetMapping(path="/getTicket")
	public Ticket getTicket( @RequestBody Ticket ticket) {
		log.trace("getTicket() invoked.", ticket);

		return ticket;
	} // getTicket
	
} // end class
