package de.florianmarsch.football_api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import de.florianmarsch.football_api.converter.EventConverter;
import de.florianmarsch.football_api.converter.LigueConverter;
import de.florianmarsch.football_api.converter.PlayerConverter;
import de.florianmarsch.football_api.converter.TeamConverter;
import de.florianmarsch.football_api.converter.WeekConverter;
import de.florianmarsch.football_api.url.UrlBuilder;
import de.florianmarsch.football_api.vo.Event;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Player;
import de.florianmarsch.football_api.vo.Team;
import de.florianmarsch.football_api.vo.Week;

public class Accessor {
	
	private OneFootball api = new OneFootball();

	public List<Ligue> getLigues() {
		String content = api.getLigues();
		List<Ligue> convert = new LigueConverter().convert(content);
		return convert;
	}

	public List<Team> getTeams(Ligue aLigue) {
		String content = api.getTeams(aLigue.getId(), aLigue.getCurrentSeason());
		return new TeamConverter().convert(content);
	}

	public List<Player> getSquads(Team aTeam) {
		String content = api.getSquads(aTeam.getId());
		List<Player> convert = new PlayerConverter().convert(content);
		for (Player player : convert) {
			player.setTeam(aTeam.getId());
		}
		return convert;
	}

	public List<Week> getWeeks(Ligue aLigue) {
		String content =api.getWeeks(aLigue.getId(), aLigue.getCurrentSeason());
		return new WeekConverter().convert(content);
	}

	public List<Event> getEvents(Integer aCompetition,Integer aSeason,Week aWeek) {
		List<Event> response = new ArrayList<Event>();
		List<Date> days = aWeek.getDays();
		for (Date date : days) {
			String content = api.getEvents(aCompetition, aSeason, aWeek.getId());
			response.addAll(new EventConverter().convert(content));
		}
		return response;
	}


}
