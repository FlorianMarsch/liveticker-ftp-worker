package de.florianmarsch;

import static org.quartz.SimpleScheduleBuilder.repeatMinutelyForever;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.List;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.football_api.Accessor;
import de.florianmarsch.football_api.vo.Ligue;

public class WorkerMain {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	public static void main(String[] args) throws Exception {
		Accessor accessor = new Accessor();

		List<Ligue> ligues = accessor.getLigues();		
		
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		scheduler.start();

		for (Ligue ligue : ligues) {
			
			
			
			scheduler.getContext().put("ligue", ligue);
			
			
			Trigger minutely = newTrigger().startNow().withSchedule(repeatMinutelyForever(1)).build();
			JobBuilder newJob = JobBuilder.newJob(CurrentDayEventsJob.class);
			JobDetail currentDayEventsJobDetail = newJob.build();
			scheduler.scheduleJob(currentDayEventsJobDetail, minutely);
			
			
			Trigger daily = newTrigger().startNow().withSchedule(repeatMinutelyForever(24*60)).build();
			newJob = JobBuilder.newJob(SyncJob.class);
			JobDetail syncJobDetail = newJob.build();
			scheduler.scheduleJob(syncJobDetail, daily);

		}

	}

}
