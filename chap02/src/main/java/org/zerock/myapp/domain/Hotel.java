package org.zerock.myapp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString
@NoArgsConstructor

//@Component("Hotel")
public class Hotel {
	
	@Setter(onMethod_= { @Autowired })
	private Chef chef;
	
	
} // class
