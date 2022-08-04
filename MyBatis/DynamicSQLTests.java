package org.zerock.myapp.persistence;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
public class DynamicSQLTests {
	
	private SqlSessionFactory sqlSessionFactory;
	
	@BeforeAll
	void beforeAll() throws IOException {
		log.trace("beforeAll() invoked.");
		
		// mybatis-config.xml 파일에 대한 InputStream 객체 생성
		String path = "mybatis/config/mybatis-config.xml";
		
		@Cleanup
		InputStream is = Resources.getResourceAsStream(path);		
		
		// 단한번 SqlSessionFactory 공장객체를 생성해서, 필드에 저장
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		this.sqlSessionFactory = builder.build(is);		
		
		assert this.sqlSessionFactory != null;
		log.info("\t+ this.sqlSessionFactory: {}", this.sqlSessionFactory);
		
	} // beforeAll
	
	@Test
	@Order(1)
	@DisplayName("1.findBoardByBno")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testSelectAllBoards() {
		log.trace("findBoardByBno() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "mappers.Board2Mapper";	// Mapper XML 파일마다 지정된 이름
			String id = "findBoardByBno";			// 특정 Mapper XML파일 안에 있는 특정 SQL태그의 식별자
			
			String sql = namespace + "." + id;
			
			List<BoardVO> board = sqlSession.<BoardVO>selectList(sql, "77");
			
			Objects.requireNonNull(board);
			log.info("\t+ board: {}", board);
			
		}// try-resources-with
	}// findBoardByBno
	
	@Test
	@Order(2)
	@DisplayName("2. testFindBoardsByTitle")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testFindBoardsByTitle() {
		log.trace("testFindBoardsByTitle() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "mappers.Board2Mapper";	
			String id = "findBoardByTitle";			
			
			String sql = namespace + "." + id;
			
			List<BoardVO> board = sqlSession.<BoardVO>selectList(sql, null);
			
			Objects.requireNonNull(board);
			log.info("\t+ board: {}", board);
			
		}// try-resources-with
	}//testFindBoardsByTitle
	
	@Test
	@Order(3)
	@DisplayName("3. testFindBoardsByWriter")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testFindBoardsByWriter() {
		log.trace("testFindBoardsByWriter() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "mappers.Board2Mapper";	
			String id = "findBoardsByWriter";			
			
			String sql = namespace + "." + id;
			
			List<BoardVO> board = sqlSession.<BoardVO>selectList(sql, "9");
			
			Objects.requireNonNull(board);
			log.info("\t+ board: {}", board);
			
		}// try-resources-with
	}//findBoardsByWriter
	
	@Test
	@Order(4)
	@DisplayName("4. testFindBoardsByBnoAndTitle")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testFindBoardsByBnoAndTitle() {
		log.trace("testFindBoardsByBnoAndTitle() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "mappers.Board2Mapper";	
			String id = "findBoardsByBnoAndTitle";			
			
			String sql = namespace + "." + id;
			
			Map<String, Object> params = new HashMap<>();
			params.put("bno", 100);
			params.put("title", "7");
			List<BoardVO> board = sqlSession.<BoardVO>selectList(sql, params);
			
			Objects.requireNonNull(board);
			log.info("\t+ board: {}", board);
			
		}// try-resources-with
	}//findBoardsByWriter
	
	@Test
	@Order(5)
	@DisplayName("4. testFindBoardsByBnoAndTitle2")
	@Timeout(value=30, unit=TimeUnit.SECONDS)
	void testFindBoardsByBnoAndTitle2() {
		log.trace("testFindBoardsByBnoAndTitle2() invoked.");
		
		SqlSession sqlSession = this.sqlSessionFactory.openSession();
		
		try (sqlSession) {
			
			String namespace = "mappers.Board2Mapper";	
			String id = "findBoardsByBnoAndTitle2";			
			
			String sql = namespace + "." + id;
			
			Map<String, Object> params = new HashMap<>();
//			params.put("bno", 100);
			params.put("title", "7");
			List<BoardVO> board = sqlSession.<BoardVO>selectList(sql, params);
			
			Objects.requireNonNull(board);
			log.info("\t+ board: {}", board);
			
		}// try-resources-with
	}//findBoardsByWriter
	
} // end class
