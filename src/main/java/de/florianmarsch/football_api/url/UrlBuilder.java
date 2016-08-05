package de.florianmarsch.football_api.url;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlBuilder {

	public URL getLigueUrl() {
		try {
			return new URL("https://config.onefootball.com/api/scoreconfig2/de.json");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public URL getTeamUrl(Integer aCompetition, Integer aSeason) {
		try {
			return new URL("https://feedmonster.onefootball.com/feeds/il/de/competitions/"+aCompetition+"/"+aSeason+"/teamsOverview.json");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public URL getSquadUrl(Integer aTeam) {
		try {
			return new URL("https://vintagemonster.onefootball.com/api/teams/de/"+aTeam+".json");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public URL getWeekUrl(Integer aCompetition, Integer aSeason) {
		try {
			return new URL("https://feedmonster.onefootball.com/feeds/il/de/competitions/"+aCompetition+"/"+aSeason+"/matchdaysOverview.json");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

	public URL getEventUrl(Integer aCompetition, Integer aSeason, Integer aMatchday) {
		try {
			return new URL("https://feedmonster.onefootball.com/feeds/il/de/competitions/"+aCompetition+"/"+aSeason+"/matchdays/"+aMatchday+".json");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}

}
