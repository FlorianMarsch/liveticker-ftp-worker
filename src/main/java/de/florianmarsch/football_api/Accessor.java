package de.florianmarsch.football_api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.florianmarsch.football_api.converter.EventConverter;
import de.florianmarsch.football_api.converter.LigueConverter;
import de.florianmarsch.football_api.converter.PlayerConverter;
import de.florianmarsch.football_api.converter.TeamConverter;
import de.florianmarsch.football_api.converter.WeekConverter;
import de.florianmarsch.football_api.vo.Event;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Player;
import de.florianmarsch.football_api.vo.Team;
import de.florianmarsch.football_api.vo.Week;

public class Accessor {
	
	private Soccerama soccerama = new Soccerama();

	public List<Ligue> getLigues() {
		String content = soccerama.getLigues();
		return new LigueConverter().convert(content);
	}

	public List<Team> getTeams(Ligue aLigue) {
		Integer currentSeason = aLigue.getCurrentSeason();
		String content = soccerama.getTeams(currentSeason.toString());
		return new TeamConverter().convert(content);
	}

	public List<Player> getSquads(Team aTeam) {
		String content = soccerama.getSquads(aTeam.getId().toString());
		List<Player> convert = new PlayerConverter().convert(content);
		for (Player player : convert) {
			player.setTeam(aTeam.getId());
		}
		return convert;
	}

	public List<Week> getWeeks(Ligue aLigue) {
		Integer currentSeason = aLigue.getCurrentSeason();
		String content =soccerama.getWeeks(currentSeason.toString());
		return new WeekConverter().convert(content);
	}

	public List<Event> getEvents(Week aWeek) {
		List<Event> response = new ArrayList<Event>();
		List<Date> days = aWeek.getDays();
		for (Date date : days) {
			String content = soccerama.getEvents(getFormated(date));
			response.addAll(new EventConverter().convert(content));
		}
		return response;
	}

	private String getFormated(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
}
