package org.zerock.myapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
	private Integer tno;
	private Double price;
	private String grade;
	

}
