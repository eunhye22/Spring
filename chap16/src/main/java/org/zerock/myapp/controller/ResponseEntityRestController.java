package org.zerock.myapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.myapp.domain.SampleVO;
import org.zerock.myapp.domain.Ticket;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/responseentity")
@RestController
public class ResponseEntityRestController {

	@GetMapping(path="/check", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SampleVO> check(Double height, Double weight) {
		log.trace("check({}, {}) invoked.", height, weight);
		
		Ticket ticket = new Ticket(777, 15000., "B");
		SampleVO sampleVO = new SampleVO("춘식", 28, ticket);
		
		log.info("\t+ sampleVO: {}", sampleVO);
		
		ResponseEntity<SampleVO> response = null;
		BodyBuilder bodyBuilder = null;
		
		if(height < 20) {	// 받은 키를 체크해보니, 불가능
			bodyBuilder = ResponseEntity.status(HttpStatus.BAD_REQUEST);
		} else {
			bodyBuilder = ResponseEntity.status(HttpStatus.OK);
		}
		
		log.info("\t+ bodyBuilder: {}, type: {}", bodyBuilder, bodyBuilder.getClass().getName());
		
		 response = bodyBuilder.<SampleVO>body(sampleVO);
		log.info("\t+ response: {}, type: {}", response, response.getClass().getName());
		
		return response;	
	}
}
