package org.zerock.myapp;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

@Accessors(fluent=true, chain=true)
@Data
class FluentAPI {
	private String name;
	private int age;

} // end class

@ToString
@Builder
class Person {
	private String name;
	private int age;
	private String ssn;			// 초기화
	private int otherField1;
	private int otherField2;
	private int otherField3;	// 초기화
	private int otherField4;
	private int otherField5;
	private int otherField6;
	private int otherField7;	// 초기화
} // end dlass


@Log4j2
public class Main {
	public static void main(String... args) {
		FluentAPI api = new FluentAPI();
		api.name("Choonsik").age(28);

		log.info(api);
		
//		---------------------------------------------
//		BuilderPattern - 나한테 필요한 필드만 셋팅하고 빌드하기!
		Person obj = 
				Person.builder().ssn("123456-1234567").otherField3(28).otherField7(30).build();
	} // main
}
