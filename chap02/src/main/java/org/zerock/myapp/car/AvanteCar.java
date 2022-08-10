package org.zerock.myapp.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.NoArgsConstructor;
import lombok.ToString;


@ToString
@NoArgsConstructor

//@Component
public class AvanteCar {
	// 1. 고유한 속성
	private String model = "AVANTE 2022";
	private String color = "WHITE";
	private Double price = 30000000.0;
	
	// 2. 실시간 변하는 상태
	private int speed = 0;
	
	// ** 의존성 주입은 이걸 만들어준다 !!! 내가 만든 객체를 필드에다 넣어주겠다. 
	// new 연산자를 통한 객체 생성 필요 X
	// 3. 구성부품 (=집합관계)
	@Autowired private Engine engine;
	@Autowired private Handle handle;
	@Autowired private Tire tire;

}// end class
