package de.florianmarsch.football_api;

import de.florianmarsch.football_api.url.Request;
import de.florianmarsch.football_api.url.UrlBuilder;

public class Soccerama {

	private UrlBuilder urlbuilder = new UrlBuilder();

	public String getLigues() {
		return new Request(urlbuilder.getLigueUrl()).submit();
	}

	public String getTeams(String season) {
		return new Request(urlbuilder.getTeamUrl(season)).submit();
	}

	public String getSquads(String team) {
		return new Request(urlbuilder.getSquadUrl(team)).submit();
	}

	public String getWeeks(String season) {
		return new Request(urlbuilder.getWeekUrl(season)).submit();
	}

	public String getEvents(String date) {
		return new Request(urlbuilder.getEventUrl(date)).submit();
	}

}
