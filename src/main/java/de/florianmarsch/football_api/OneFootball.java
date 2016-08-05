package de.florianmarsch.football_api;

import de.florianmarsch.football_api.url.Request;
import de.florianmarsch.football_api.url.UrlBuilder;

public class OneFootball {

	private UrlBuilder urlbuilder = new UrlBuilder();

	public String getLigues() {
		return new Request(urlbuilder.getLigueUrl()).submit();
	}

	public String getTeams(Integer aCompetition, Integer aSeason) {
		return new Request(urlbuilder.getTeamUrl(aCompetition,aSeason)).submit();
	}

	public String getSquads(Integer aTeam) {
		return new Request(urlbuilder.getSquadUrl(aTeam)).submit();
	}

	public String getWeeks(Integer aCompetition, Integer aSeason) {
		return new Request(urlbuilder.getWeekUrl(aCompetition,aSeason)).submit();
	}

	public String getEvents(Integer aCompetition, Integer aSeason, Integer aMatchday) {
		return new Request(urlbuilder.getEventUrl(aCompetition,aSeason,aMatchday)).submit();
	}

}
