package org.zerock.myapp.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Cleanup;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class HikariDataSourceTests {
	
//	@Resource(type=javax.sql.DataSource.class)
//	@Resource
//	@Inject
//	@Autowired
	
	// 주입 가능한 bean이 여러개일 때, @Resource를 이용해 직접 어떤 bean을 사용할 것인지 선택 가능
	@Setter(onMethod_= { @Resource(type=HikariDataSource.class) })
//	@Setter(onMethod_= { @Resource(type=PooledDataSource.class) })
//	@Setter(onMethod_= { @Autowired })		// @since java 8 and above	// 주입 시그널
	private DataSource dataSource;
	
	
	// 1. 선처리 작업 : 필드에 원하는 타입의 빈(Bean)객체가 잘 주입(DI)되었는지 확인
	@BeforeAll
	void beforeAll() {
		log.trace("beforeAll() invoked.");
		 
		assertNotNull(this.dataSource);
//		Objects.requireNonNull(this.dataSource);
//		assert this.dataSource != null;
		
		log.info("\t+ this.dataSource: {}", this.dataSource);
		
	} // beforeAll
	
	@Test
	void dummyTest() {;;}
	
	@Test
	@Order(1)
	@DisplayName("1. javax.sql.DataSource.getConnection() method test")
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	void testGetConnection() throws SQLException {
		log.trace("testGetConnection() invoked.");
		
		// AutoCloseable한 자원객체임 : 다 쓰고나면 반드시 close(자원해제) 해줘야 함
		// DataSource.getConnection() 메소드 => 무한정 기다림(blocking)
		
		@Cleanup
		Connection conn = this.dataSource.getConnection();	// Connection Pool로부터 빌린 Connection
		assertNotNull(conn);
		log.info("\t+ conn: {}, type: {}", conn, conn.getClass().getName());
		
		// 자원해제
//		conn.close(); // 이 close는 진짜 연결(Connection)을 닫아버릴까? -> NO! -> Pool로 "반납"
		
	} // test1
	
	
	@Test
	@Order(2)
	@DisplayName("2. testGetConnectionWithSQL")
	@Timeout(value=10, unit=TimeUnit.SECONDS)
	void testGetConnectionWithSQL() throws SQLException {
		log.trace("testGetConnectionWithSQL() invoked.");
		
		@Cleanup
		Connection conn = this.dataSource.getConnection();	// Connection Pool로부터 빌린 Connection

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM employees");
		
		try(conn; stmt; rs;) {
			
			while(rs.next() ) {
				// rs.getXXX(컬럼명)에서, "컬럼명"은 대소문자 구분하지 않음
				// rs.getString(컬럼명)에서, rs.getString() 메소드는 "만능"임
				String employee_id = rs.getString("employee_id");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				String email = rs.getString("email");
				String phone_number = rs.getString("phone_number");
				String hire_date = rs.getString("hire_date");
				String job_id = rs.getString("job_id");
				String salary = rs.getString("salary");
				String commission_pct = rs.getString("commission_pct");
				String department_id = rs.getString("department_id");
				
				String employee = String.format("{}, {}, {}, {}, {}, {}, {}, {}, {}, {}", 
						employee_id, first_name, last_name, email, phone_number, hire_date, salary,
						commission_pct, department_id); //.format
				
				log.info(employee);
			} // while
		} // try-with-resources
		
		
	} // test2


} // end class
