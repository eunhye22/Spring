package org.zerock.myapp.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
public class SimpleJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.trace("execute({}) inboked.", context);
		
		// Batch Job 구현코드 작성 (통계산출, 정산, 요금, ...)
		log.info("\t+ Batch Job A executed...");
		
		// JobDetail의 usingJobData(key, value)으로 넘겨준 데이터 획득 및 사용
		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		String jobSays = dataMap.getString("jobSays");
		float myFloatData = dataMap.getFloat("myFloatData");
		log.info("\t+ jobSays: {}, myFloatData: {}", jobSays, myFloatData);
	} // execute
		
}
