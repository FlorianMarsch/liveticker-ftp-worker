package de.florianmarsch.football_api.converter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.football_api.vo.Ligue;

public class LigueConverter {

	public List<Ligue> convert(String content) {
		List<Ligue> response = new ArrayList<Ligue>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONArray leagues = jsonObject.getJSONArray("data");
			for (int i = 0; i < leagues.length(); i++) {
				JSONObject league = leagues.getJSONObject(i);
				response.add(new Ligue(league));
			}
			return response;
		} catch (JSONException e) {
			throw new RuntimeException("Error reading leagues from JSON : "+e.getMessage());
			
		}
	}

}
