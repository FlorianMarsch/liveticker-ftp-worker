package de.florianmarsch.liveticker.ftp;

import java.util.ArrayList;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPJob implements Job {

	final static Logger logger = LoggerFactory.getLogger(FTPJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("start ftp push");

		List<FTPPushTask> taks = getTasks();
		
		for (FTPPushTask task : taks) {
			try {
				logger.info("start task "+task);
				new FTPPusher().save(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("end ftp push");
	}

	

	private List<FTPPushTask> getTasks() {
		List<FTPPushTask> tasks = new ArrayList<FTPPushTask>();

		FTPPushTask task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue");
		task.setDirectory("v1/api/");
		task.setFilename("ligue.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("team.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/events");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("events.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/weeks");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("weeks.json");
		tasks.add(task);

		tasks.addAll(getTeamTasks(1229, 10285));
		tasks.addAll(getTeamTasks(1229, 10303));
		tasks.addAll(getTeamTasks(1229, 10281));
		tasks.addAll(getTeamTasks(1229, 10307));
		tasks.addAll(getTeamTasks(1229, 10576));
		tasks.addAll(getTeamTasks(1229, 10388));
		tasks.addAll(getTeamTasks(1229, 10437));
		tasks.addAll(getTeamTasks(1229, 10653));
		tasks.addAll(getTeamTasks(1229, 10476));
		tasks.addAll(getTeamTasks(1229, 10419));
		tasks.addAll(getTeamTasks(1229, 10453));
		tasks.addAll(getTeamTasks(1229, 10269));
		tasks.addAll(getTeamTasks(1229, 10677));
		tasks.addAll(getTeamTasks(1229, 10329));
		tasks.addAll(getTeamTasks(1229, 10442));
		tasks.addAll(getTeamTasks(1229, 10347));
		tasks.addAll(getTeamTasks(1229, 10646));
		tasks.addAll(getTeamTasks(1229, 10423));
		return tasks;
	}


	private List<FTPPushTask> getTeamTasks(int ligue, int team){
		List<FTPPushTask> tasks= new ArrayList<FTPPushTask>();
		
		FTPPushTask task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/"+ligue+"/team/"+team+"/squad");
		task.setDirectory("v1/api/ligue/"+ligue+"/team/");
		task.setFilename("10423.json");
		tasks.add(task);
		
		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/"+ligue+"/team/"+team+"/squad");
		task.setDirectory("v1/api/ligue/"+ligue+"/team/"+team+"/");
		task.setFilename("squad.json");
		tasks.add(task);
		return tasks;
	}

}
