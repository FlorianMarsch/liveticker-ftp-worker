package de.florianmarsch.football_api.url;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UrlBuilder {

	private String baseUrl = "https://api.soccerama.pro/v1/";
	private String endpoint;
	private String endpointParameter;
	private Set<String> includes = new HashSet<String>();
	private String apiToken;

	public UrlBuilder() {
		apiToken = System.getenv("soccerama-api-token");
		if (apiToken == null) {
			throw new RuntimeException("soccerama-api-token is missing");
		}
	}

	public UrlBuilder setEndpoint(String aEndpoint) {
		endpoint = aEndpoint;
		return this;
	}

	public UrlBuilder setParameter(String aParameter) {
		endpointParameter = aParameter;
		return this;
	}

	public UrlBuilder addInclude(String aInclude) {
		includes.add(aInclude);
		return this;
	}

	public URL build() {
		StringBuilder builder = new StringBuilder();
		builder.append(baseUrl);
		builder.append(endpoint.toString().toLowerCase());
		if (endpointParameter != null) {
			builder.append("/");
			builder.append(endpointParameter);
		}
		builder.append("?");
		if (!includes.isEmpty()) {
			builder.append("include=");
			for (Iterator<String> iterator = includes.iterator(); iterator.hasNext();) {
				String include = iterator.next();
				builder.append(include);
				if (iterator.hasNext()) {
					builder.append(",");
				}
			}
			builder.append("&");
		}
		builder.append("api_token=");
		builder.append(apiToken);
		try {
			return new URL(builder.toString());
		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL : " + e.getMessage());
		}
	}

	public URL getLigueUrl() {
		return new UrlBuilder().setEndpoint("competitions").addInclude("currentSeason").build();
	}

	public URL getTeamUrl(String season) {
		return new UrlBuilder().setEndpoint("teams/season").setParameter(season).build();
	}

	public URL getSquadUrl(String team) {
		return new UrlBuilder().setEndpoint("players/team").addInclude("position").setParameter(team).build();
	}

	public URL getWeekUrl(String season) {
		return new UrlBuilder().setEndpoint("seasons").addInclude("rounds").setParameter(season).build();
	}

	public URL getEventUrl(String date) {
		return new UrlBuilder().setEndpoint("livescore/date").addInclude("events").setParameter(date).build();
	}

}
