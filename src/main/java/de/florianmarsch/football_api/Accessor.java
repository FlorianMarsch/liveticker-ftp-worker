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
import de.florianmarsch.football_api.vo.Event;
import de.florianmarsch.football_api.vo.Ligue;
import de.florianmarsch.football_api.vo.Player;
import de.florianmarsch.football_api.vo.Team;
import de.florianmarsch.football_api.vo.Week;

public class Accessor {
	
	private Soccerama soccerama = new Soccerama();

	public List<Ligue> getLigues(String aLeagueID) {
		String content = soccerama.getLigues();
		List<Ligue> convert = new LigueConverter().convert(content);
		Iterator<Ligue> it = convert.iterator();
		while (it.hasNext()) {
			Ligue ligue =  it.next();
			if(!ligue.getId().equals(Integer.valueOf(aLeagueID))){
				it.remove();
			}
		}
		
		return convert;
	}

	public List<Team> getTeams(Ligue aLigue) {
		String content = soccerama.getTeams(System.getenv("soccerama-current-season"));
		return new TeamConverter().convert(content);
	}

	public List<Player> getSquads(Team aTeam, String aSeasonID) {
		String content = soccerama.getSquads(aTeam.getId().toString(), aSeasonID);
		List<Player> convert = new PlayerConverter().convert(content);
		for (Player player : convert) {
			player.setTeam(aTeam.getId());
		}
		return convert;
	}

	public List<Week> getWeeks(Ligue aLigue) {
		String content =soccerama.getWeeks(System.getenv("soccerama-current-season"));
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
