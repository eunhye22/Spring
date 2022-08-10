package org.zerock.myapp.di;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

// 테스트 메소드 수행시, 스프링 프레임워크까지 함께 구동되도록 해주는 어노테이션 설정 추가


// For JUnit Jupyter v5.x
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations= {
		// 필요한 스프링 설정파일을 등록해줌. 이때 "file: "이 사용되는데
		// "file: "의 의미는 프로젝트 폴더와 같음. ( root-context.xml 경로 등록 )
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class DITestsForAvanteCar {

	
	// Spring Beans Container 를 주입해달라!
	@Autowired
	private ApplicationContext beansContainer;
	
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll () invoked.");
		
		assertNotNull(this.beansContainer);
		log.info("\t+ this.beansContainer: {}", this.beansContainer);
		
	} // beforeAll
	
	@Test
	void dummyTest() {
		String[] beanNames = beansContainer.getBeanDefinitionNames();
		int beanCount = beansContainer.getBeanDefinitionCount();
		
		log.info("1. beanNames: {}", Arrays.toString(beanNames));
		log.info("2. beanCount: {}", beanCount);
	}

} // end class
