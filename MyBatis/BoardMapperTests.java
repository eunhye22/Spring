package org.zerock.myapp.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.zerock.myapp.domain.BoardVO;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class BoardMapperTests {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked.");
		
		// 1. mybatis-config.xml 파일에 대한 InputStream 객체 생성
		String path = "mybatis/config/mybatis-config.xml";
		
		@Cleanup
		InputStream is = Resources.getResourceAsStream(path);		
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		this.sqlSessionFactory = builder.build(is);		
		assert this.sqlSessionFactory != null;
		log.info("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
		
	} // beforeAll
	
	
	@Test
	@Order(1)
	@DisplayName("1.testSelectAllBoards")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testSelectAllBoards() {
		log.trace("testSelectAllBoards() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			// 마이바티스는 아래와 같이 2개의 값을 이용해서, 다수의 Mapper XML 파일이 있을 때
			// (1) 어느 특정 Mapper XML을 사용할지 결정하고 (by "namespace")
			// (2) 다시 결정된 Mapper XML 파일 안에 있는 많은 SQL 태그 중에,
			//     어느 태그의 SQL 문장을 사용할지 결정 (by "sqlID")
			
			String namespace = "Choonsik";		// Mapper XML 파일마다 지정된 이름
			String sqlId = "selectAllBoards";	// 특정 Mapper XML파일 안에 있는 특정 SQL태그의 식별자
			
			String sql = namespace + "." + sqlId;	// Mapped Statement -> 최종 수행시킬 SQL문장의 식별자값
			
			
			@Cleanup("clear")	// list.clear();
			List<BoardVO> list = sqlSession.<BoardVO>selectList(sql);
		
			list.forEach(log::info);
		} // try-with-resources
	}// testSelectAllBoards
	
	@Test
	@Order(3)
	@DisplayName("3.testSelectBoards")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testSelectBoards() {
		log.trace("testSelectBoards() invoked.");
		
		@Cleanup
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "Choonsik";		// Mapper XML 파일마다 지정된 이름
			String sqlId = "selectAllBoards";	// 특정 Mapper XML파일 안에 있는 특정 SQL태그의 식별자
			
			String sql = namespace + "." + sqlId;	// Mapped Statement -> 최종 수행시킬 SQL문장의 식별자값
			
			
			@Cleanup("clear")	// list.clear();
			List<BoardVO> list = sqlSession.<BoardVO>selectList(sql);
		
			list.forEach(log::info);
		} // try-with-resources
	}// testSelectAllBoards
	
	@AfterEach
	
	@AfterAll
	void afterAll() {
		
	}

} // end class
