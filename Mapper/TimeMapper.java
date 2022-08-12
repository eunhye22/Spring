package org.zerock.myapp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.zerock.myapp.domain.EmployeeVO;

public interface TimeMapper {

	@Select("SELECT to_char(sysdate, 'yyyy/MM/dd HH24:mi:ss') AS now FROM dual")
	public abstract String getCurrentTime1();
	
	@Select("SELECT to_char(current_date, 'yyyy/MM/dd HH24:mi:ss') AS now FROM dual")
	public abstract String getCurrentTime2();
} // end class