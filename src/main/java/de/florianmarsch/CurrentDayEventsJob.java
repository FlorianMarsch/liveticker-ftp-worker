package de.florianmarsch;

import java.util.List;

import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import de.florianmarsch.football_api.Accessor;
import de.florianmarsch.football_api.vo.Event;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Week;
import de.florianmarsch.ftp.FTPPusher;
import de.florianmarsch.ftp.PushTask;

public class CurrentDayEventsJob implements Job {

	private Accessor accessor = new Accessor();
	private FTPPusher pusher = new FTPPusher();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<Ligue> ligues = accessor.getLigues();
		for (Ligue ligue : ligues){
			List<Week> weeks = accessor.getWeeks(ligue);

			Week currentWeek = null;
			for (Week week : weeks) {
				if(week.isActive()){
					currentWeek = week;
				}
			}
			
			
			if (currentWeek != null) {
				copyWeek(ligue, currentWeek);
			} else {
				PushTask pushTask = new PushTask();
				pushTask.setContent(new JSONArray().toString());
				pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/");
				pushTask.setFilename("live.json");
				pusher.save(pushTask);
			}
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

		List<Event> events = accessor.getEvents(ligue.getId(), ligue.getCurrentSeason(), week);
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

}
