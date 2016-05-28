package de.florianmarsch.liveticker;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.repeatMinutelyForever;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobBuilder;

import static org.quartz.SimpleScheduleBuilder.*;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.liveticker.ftp.FTPJob;

public class WorkerMain {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	
	public static void main(String[] args) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        scheduler.start();
        
        Trigger minutely = newTrigger()
                .startNow()
                .withSchedule(repeatMinutelyForever(1))
                .build();

        JobDetail liveJobDetail = JobBuilder.newJob(FTPJob.class).build();
        scheduler.scheduleJob(liveJobDetail, minutely);
		

	}

}
