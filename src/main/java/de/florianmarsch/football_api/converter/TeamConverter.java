package de.florianmarsch.football_api.converter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.football_api.vo.Team;

public class TeamConverter {

	public List<Team> convert(String content) {
		List<Team> response = new ArrayList<Team>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONArray teams = jsonObject.getJSONArray("teams");
			for (int i = 0; i < teams.length(); i++) {
				JSONObject team = teams.getJSONObject(i);
				response.add(new Team(team));
			}
			return response;
		} catch (JSONException e) {
			throw new RuntimeException("Error reading Teams from JSON : "+e.getMessage());
			
		}
	}

}
