package org.zerock.myapp.domain;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@ToString
@NoArgsConstructor
@Log4j2

//@Component("restaurant")
public class Restaurant {
	
	// 아래 필드에 대해 setter 메소드를 만들고, 그 위에 지정된 어노테이션을 붙임
	// 1. 명시적으로 ! 
	@Setter(onMethod_= { @Autowired })	// 이 필드에 대한 setter 메소드를 통한 의존성 주입발생
	private Chef chef;
	
	
	// 2. 
//	@Autowired
//	public void setChef(Chef chef) {
//		log.trace("setChef({}) invoked.", chef);
//		
//		this.chef = chef;
//	}
	
	
	// 생성자를 통한 의존성 주입
//   @Autowired
//   public Restaurant(Chef chef) {
//      log.trace("Constructor({}) invoked.", chef);
//      
//      this.chef = chef;
//   }


} // class
