package org.zerock.myapp;

import java.util.Arrays;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.zerock.myapp.job.JobA;
import org.zerock.myapp.job.JobB;
import org.zerock.myapp.job.JobListenerImpl;
import org.zerock.myapp.job.SimpleJob;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class QuartzExample {
	
	public static void main(String[] args) {
		log.trace("main({}) invoked.", Arrays.toString(args));
		
		
		try {
			
			// 1. To create A Quartz job scheduler
			
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			
			// 2. To Create A Quartz Job With The Associated Trigger
			JobKey simpleJobKey = JobKey.jobKey("simpleJob", "GROUP_1");
			
			JobDetail simpleJob =	// QUartz Job to be scheduled.
				JobBuilder.
					newJob(SimpleJob.class).
					withDescription("simple Job").
//					withIdentity("simpleJob", "GROUP_1").
					withIdentity(simpleJobKey).		// jobKey 기반
					usingJobData("jobSays", "Hello, World!!!").
					usingJobData("myFloatData", 3.14159f).
					build();		
			
			TriggerKey simpleJobTriggerKey = TriggerKey.triggerKey("simpleJobTrigger", "GROUP_1");
			
			Trigger simpleJobTrigger = 
				TriggerBuilder.
					newTrigger().
					withDescription("Scheduling for the Simple Job Detail").
					withIdentity(simpleJobTriggerKey).
					withPriority(10).
					startNow().
					withSchedule(	// ***
						SimpleScheduleBuilder.
							simpleSchedule().
							withIntervalInSeconds(3).
							repeatForever()							
					).
					build();
			
			JobDetail jobA = // Quartz Job to be scheduled.
	        		JobBuilder.newJob(JobA.class).
	        			withIdentity( JobKey.jobKey("jobA", "GROUP_2") ).
	        			build();
	            

	            Trigger jobATrigger = // Job Scheduling registered to the Quartz Scheduler.
	        		TriggerBuilder.newTrigger().
	        			withIdentity( TriggerKey.triggerKey("jobATrigger", "GROUP_2") ).
	        			startNow().
	        			withPriority(15).
	        			withSchedule(
	    					SimpleScheduleBuilder.simpleSchedule().
	    						withIntervalInSeconds(40).
	    						repeatForever()
	    				).
	        			build();
	            
	            
	            JobDetail jobB = // Quartz Job to be scheduled.
	            		JobBuilder.newJob(JobB.class).
	            			withIdentity( JobKey.jobKey("jobB", "GROUP_3") ).
	            			build();
	            
	                Trigger jobBTrigger = // Job Scheduling registered to the Quartz Scheduler.
	            		TriggerBuilder.newTrigger().
	            			withIdentity( TriggerKey.triggerKey("jobBTrigger", "GROUP_3") ).
	            			startNow().
	            			withPriority(10).
	            			withSchedule(
	        					SimpleScheduleBuilder.simpleSchedule().
	        						withIntervalInSeconds(20).
	        						repeatForever()
	        				).
	            			build();

	        // 3. To add each listener with Quartz job, trigger, scheduler
	        ListenerManager lm = scheduler.getListenerManager();
	        lm.addJobListener(new JobListenerImpl());
			
			// 4. To schedule Quartz Jobs with Job and Trigger
			scheduler.scheduleJob(simpleJob, simpleJobTrigger);
			scheduler.scheduleJob(jobA, jobATrigger);
			scheduler.scheduleJob(jobB, jobBTrigger);
			
			// 5. To start a Quartz Scheduler
			scheduler.start();
			
			log.info("\t+ Quartz Job scheduler Started...");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			;;
		} // try-catch-finally
	} // main

} // class
