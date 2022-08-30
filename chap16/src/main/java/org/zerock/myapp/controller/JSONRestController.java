package org.zerock.myapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.SampleVO;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor


@RequestMapping("/RESTful/")
@RestController
public class JSONRestController {

//	@GetMapping(path="/getSampleVO.json", produces=MediaType.APPLICATION_XML_VALUE)	 // XML format
	@GetMapping(path="/getSampleVO.json", produces=MediaType.APPLICATION_JSON_VALUE)  // JSON format
	public SampleVO getSampleVO_Json() {
		log.trace("getSampleVO_Json() invoked.");
		
		SampleVO vo = new SampleVO("임춘식", 28, null);
		
		return vo;
	}
	
	@GetMapping(path="/getTicket", produces=MediaType.APPLICATION_JSON_VALUE)
	public Ticket getTicket_json() {
		log.trace("getTicket() invoked.");
		
		Ticket ticket = new Ticket();
		ticket.setTno(3737);
		ticket.setPrice(150000.);
		ticket.setGrade("S");

		return ticket;
	}
	
	@GetMapping(path="/getTickets.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Ticket> getTickets() {
		log.trace("getTickets() invoked.");
		
		List<Ticket> list = new ArrayList<>();
		for(int i=1; i<=5; i++) {
			Ticket ticket = new Ticket(i, 1000.*i,  (i <= 3)? "C":( (i ==4)? "B" : "A" )  );
			list.add(ticket);
			
		}// for
		return list;
		
	} // getTickets
	
	@ResponseBody
	@GetMapping(path="/getSpecialSampleVO.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public SampleVO getSpecialSampleVO() {
		log.trace("getSpecialSampleVO() invoked.");
		
		SampleVO vo = new SampleVO("임춘식", 28, new Ticket(1000, 250., "D"));
		log.info("\t+ vo: {}", vo);
		
		return vo;
	}
	
} // end class
