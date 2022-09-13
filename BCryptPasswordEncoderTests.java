package org.zerock.myapp.pwhash;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BCryptPasswordEncoderTests {

//	@Disabled
	@Test
	@Order(1)
	@DisplayName("testMatches")
	@Timeout(value=5, unit=TimeUnit.SECONDS)
	void testBCryptPasswordEncoder() {
		log.trace("testBCryptPasswordEncoder() invoked.");
		
		String plainText = "choon1234*";		// Raw password
		
//		// Case1 : 저장된 해쉬값과 입력한 암호의 비교
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String cipherText = encoder.encode(plainText);
//		log.info("\t+ 1. cipherText: {}", cipherText);
//		
//		// plainText : 로그인 창에서, 사용자가 입력한 Raw password
//		// cipherText : 사용자 테이블의 암호컬럼에 저장된 해쉬값
//		boolean isMatched = encoder.matches(plainText, cipherText);
//		log.info("\t+ 2. isMatched : {}", isMatched);
		
		
		// Case2 : 같은 암호(plain text)에 대해서, 여러번 해쉬값을 생성하여 비교
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
		for(int i=0; i<10; i++) {
			String cipherText = encoder.encode(plainText);
			
			
			boolean isMatched = encoder.matches(plainText, cipherText);
			
			log.info("\t+ cipherText: {}", cipherText);
			log.info("\t+ isMatched : {}", isMatched);
		}
			
	} // testMatches
	
//	@Disabled
	@Test
	@Order(2)
	@DisplayName("testBCryptPasswordEncoderWithSalt")
	@Timeout(value=5, unit=TimeUnit.SECONDS)
	void testBCryptPasswordEncoderWithSalt() {
		log.trace("testBCryptPasswordEncoderWithSalt() invoked.");
		
		String password = "choon1234*";		//Plain Text
		String salt = "SALT_";
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String cipherText = encoder.encode(salt+password);
			
		log.info("\t+ cipherText: {}", cipherText);
		
		log.info("\t+ isMatchWithSalt: {}", encoder.matches(salt + password, cipherText));
		
	} // testBCryptPasswordEncoderWithSalt
	
	
} // end class
