package org.zerock.myapp.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.myapp.domain.Hotel;
import org.zerock.myapp.domain.Restaurant;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 테스트 메소드 수행시, 스프링 프레임워크까지 함께 구동되도록 해주는 어노테이션 설정 추가

//For JUnit 4.x
//@RunWith
//@RunWith(SpringRunner.class)

// For JUnit Jupyter v5.x
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations= {
		// 필요한 스프링 설정파일을 등록해줌. 이때 "file: "이 사용되는데
		// "file: "의 의미는 프로젝트 폴더와 같음. ( root-context.xml 경로 등록 )
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DITests {
	
	// 이 JUnit 테스트 클래스도, JUnit test framework에 의해서
	// 구동시 기본생성자를 이용하여 객체를 생성하고, 
	// 이 객체는 자동으로 Spring Beans Container의 Bean으로 등록됨
	
	
//	@Autowired
//	private Hotel hotel;
//	
	@Autowired		// 의존성 주입 시그널 전송 to Beans Container
	private Restaurant restaurant;
	
	@Autowired
	private Hotel hotel;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		
		// Null이 아니면 찍어보자! (의존성 주입이 됐으면 Null이 아님)
		assertNotNull(this.restaurant);				//1st. method
//		Objects.requireNonNull(this.restaurant);	//2nd. method
//		assert this.restaurant						//3rd. method
		log.info("\t+ 1. this.restaurant: {}", this.restaurant);
		log.info("\t+ 2. this.hotel: {}", this.hotel);
	}// beforeAll
	
	
	@Test
	void dummyTest() {;;}

} // end class
