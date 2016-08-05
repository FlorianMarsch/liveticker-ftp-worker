package de.florianmarsch.football_api.converter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.football_api.vo.Player;

public class PlayerConverter {

	public List<Player> convert(String content) {
		List<Player> response = new ArrayList<Player>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONArray players = jsonObject.getJSONObject("data").getJSONObject("team").getJSONArray("players");
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				response.add(new Player(player));
			}
			return response;
		} catch (JSONException e) {
			throw new RuntimeException("Error reading players from JSON : "+e.getMessage());
			
		}
	}

}
