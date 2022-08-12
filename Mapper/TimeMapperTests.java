package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.zerock.myapp.mapper.TimeMapper;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class TimeMapperTests {
	
	@Setter(onMethod_= {@Autowired})
	private SqlSessionFactory sqlSessionFactory;
	
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked.");
		
		assertNotNull(this.sqlSessionFactory);
		
		log.info("\t+ this.dataSource: {}", this.sqlSessionFactory);
	} // beforeAll
	
	@Test
	@Order(1)
	@DisplayName("1. testGetCurrentTime1")
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	void testGetCurrentTime1() throws SQLException {
		log.trace("testGetCurrentTime1() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {

			// Mapper Interface 방식
			TimeMapper mapper = sqlSession.getMapper(TimeMapper.class);
			
			assertNotNull(this.sqlSessionFactory);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			String time = mapper.getCurrentTime1();
			log.info("\t+ time : {}", time);
			
		} // try-with-resources
	
	} // testGetCurrentTime1
	
	@Test
	@Order(2)
	@DisplayName("2. testGetCurrentTime2")
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	void testGetCurrentTime2() throws SQLException {
		log.trace("testGetCurrentTime2() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {

			TimeMapper mapper = sqlSession.getMapper(TimeMapper.class);
			
			assertNotNull(this.sqlSessionFactory);
			log.info("\t+ mapper : {}, type : {}", mapper, mapper.getClass().getName() );
			
			String time = mapper.getCurrentTime2();
			log.info("\t+ time : {}", time);
			
		} // try-with-resources
	
	} // testGetCurrentTime2

	
} // end class
