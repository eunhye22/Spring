package org.zerock.myapp.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2
public class JobA implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.trace("execute({}) inboked.", context);
		
		// Batch Job 구현코드 작성 (통계산출, 정산, 요금, ...)
		log.info("\t+ Batch Job A executed...");
	} // execute

} // end class
