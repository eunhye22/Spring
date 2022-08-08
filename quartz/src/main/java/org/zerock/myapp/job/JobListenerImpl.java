package org.zerock.myapp.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JobListenerImpl implements JobListener {

	@Override
	public String getName() {
		log.trace("\t+ getName() invoked.");
		return null;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		log.trace("\t+ jobToBeExecuted() invoked.");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.trace("\t+ jobExecutionVetoed() invoked.");

	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.trace("\t+ jobWasExecuted() invoked.");

	}

}
