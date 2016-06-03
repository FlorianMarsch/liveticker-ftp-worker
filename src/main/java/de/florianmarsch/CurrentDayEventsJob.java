package de.florianmarsch;

import java.util.List;

import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import de.florianmarsch.football_api.Accessor;
import de.florianmarsch.football_api.vo.Event;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Week;
import de.florianmarsch.ftp.FTPPusher;
import de.florianmarsch.ftp.PushTask;

public class CurrentDayEventsJob implements Job {

	private Accessor accessor = new Accessor();
	private FTPPusher pusher = new FTPPusher();
	private Ligue ligue;
	private Week week;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		init(context);
		if (week != null) {
			copyWeek(ligue, week);
		}else{
			PushTask pushTask = new PushTask();
			pushTask.setContent(new JSONArray().toString());
			pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/");
			pushTask.setFilename("live.json");
			pusher.save(pushTask);
		}
	}
	
	private void copyWeek(Ligue ligue, Week week) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(week.toJson().toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/");
		pushTask.setFilename(week.getNumber() + ".json");
		pusher.save(pushTask);
		getLiveEvents(ligue, week);

	}
	
	private void getLiveEvents(Ligue ligue, Week week) {

		List<Event> events = accessor.getEvents(week);
		copyAllEvents(ligue, week, events);
		for (Event event : events) {
			copyEvent(ligue, week, event);
		}

	}
	
	private void copyAllEvents(Ligue ligue, Week week, List<Event> events) {

		JSONArray array = new JSONArray();
		for (Event event : events) {
			array.put(event.toJson());
		}

		PushTask pushTask = new PushTask();
		pushTask.setContent(array.toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/" + week.getNumber() + "/");
		pushTask.setFilename("events.json");
		pusher.save(pushTask);
		
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/");
		pushTask.setFilename("live.json");
		pusher.save(pushTask);

	}
	
	private void copyEvent(Ligue ligue, Week week, Event event) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(event.toJson().toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/events/");
		pushTask.setFilename(event.getId() + ".json");
		pusher.save(pushTask);

	}

	void init(JobExecutionContext context) {
		if (ligue == null) {
			try {
				ligue = (Ligue) context.getScheduler().getContext().get("ligue");
				if (ligue == null) {
					throw new RuntimeException("Ligue not configed");
				}
			} catch (SchedulerException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		try {
			week = (Week) context.getScheduler().getContext().get("week");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


}
