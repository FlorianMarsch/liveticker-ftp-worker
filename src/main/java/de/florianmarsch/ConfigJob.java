package de.florianmarsch;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import de.florianmarsch.football_api.Accessor;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Week;

public class ConfigJob implements Job {

	private Ligue ligue;

	

	public void execute(JobExecutionContext context) throws JobExecutionException {
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
		
		Accessor accessor = new Accessor();
		List<Week> weeks = accessor.getWeeks(ligue);
		Week current = null;
		for (Week week : weeks) {
			if(week.isActive()){
				current = week;
			}
		}
		try {
			context.getScheduler().getContext().put("week", current);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}


}
