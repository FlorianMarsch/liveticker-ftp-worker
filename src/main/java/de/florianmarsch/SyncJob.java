package de.florianmarsch;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.football_api.Accessor;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Player;
import de.florianmarsch.football_api.vo.Team;
import de.florianmarsch.football_api.vo.Week;
import de.florianmarsch.ftp.FTPPusher;
import de.florianmarsch.ftp.PushTask;

public class SyncJob implements Job {

	final static Logger logger = LoggerFactory.getLogger(SyncJob.class);

	private Ligue ligue;
	private Accessor accessor = new Accessor();
	private FTPPusher pusher = new FTPPusher();
	private List<Player> players = new ArrayList<Player>();

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("start ftp push");

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

		copyLigue(ligue);
		players.clear();
		logger.info("end ftp push");
	}

	private void copyLigue(Ligue ligue) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(ligue.toJson().toString());
		pushTask.setDirectory("v1/api/league/");
		pushTask.setFilename(ligue.getId() + ".json");
		pusher.save(pushTask);
		getTeamTasks(ligue);
		getEventTasks(ligue);

	}

	private void getEventTasks(Ligue ligue) {

		List<Week> weeks = accessor.getWeeks(ligue);
		copyAllWeeks(ligue, weeks);
		for (Week week : weeks) {
			copyWeek(ligue, week);
		}

	}

	private void copyWeek(Ligue ligue, Week week) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(week.toJson().toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/week/");
		pushTask.setFilename(week.getNumber() + ".json");
		pusher.save(pushTask);

	}

	private void copyAllWeeks(Ligue ligue, List<Week> weeks) {

		JSONArray array = new JSONArray();
		for (Week week : weeks) {
			array.put(week.toJson());
		}

		PushTask pushTask = new PushTask();
		pushTask.setContent(array.toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/");
		pushTask.setFilename("weeks.json");
		pusher.save(pushTask);

	}

	private void getTeamTasks(Ligue ligue) {

		List<Team> teams = accessor.getTeams(ligue);
		copyAllTeams(ligue, teams);
		for (Team team : teams) {
			copyTeam(ligue, team);
		}
		copyAllPayers(ligue, players);
	}

	private void copyAllPayers(Ligue ligue, List<Player> somePlayers) {
		JSONArray array = new JSONArray();
		for (Player player : somePlayers) {
			array.put(player.toJson());
		}
		PushTask pushTask = new PushTask();
		pushTask.setContent(array.toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/");
		pushTask.setFilename("players.json");
		pusher.save(pushTask);

	}

	private void copyTeam(Ligue ligue, Team team) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(team.toJson().toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/team/");
		pushTask.setFilename(team.getId() + ".json");
		pusher.save(pushTask);
		copyPlayers(ligue, team);

	}

	private void copyPlayers(Ligue ligue, Team team) {

		List<Player> squad = accessor.getSquads(team, System.getenv("soccerama-current-season"));
		players.addAll(squad);
		JSONArray array = new JSONArray();
		for (Player player : squad) {
			array.put(player.toJson());
			copyPlayer(ligue, player);
		}
		PushTask pushTask = new PushTask();
		pushTask.setContent(array.toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/team/" + team.getId() + "/");
		pushTask.setFilename("player.json");
		pusher.save(pushTask);

	}

	private void copyPlayer(Ligue ligue, Player player) {

		PushTask pushTask = new PushTask();
		pushTask.setContent(player.toJson().toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/player/");
		pushTask.setFilename(player.getId() + ".json");
		pusher.save(pushTask);

	}

	private void copyAllTeams(Ligue ligue, List<Team> teams) {

		JSONArray array = new JSONArray();
		for (Team team : teams) {
			array.put(team.toJson());
		}

		PushTask pushTask = new PushTask();
		pushTask.setContent(array.toString());
		pushTask.setDirectory("v1/api/league/" + ligue.getId() + "/");
		pushTask.setFilename("teams.json");
		pusher.save(pushTask);

	}

}
