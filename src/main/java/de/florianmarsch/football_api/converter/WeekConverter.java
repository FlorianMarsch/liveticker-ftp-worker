package de.florianmarsch.football_api.converter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.football_api.vo.Week;

public class WeekConverter {

	public List<Week> convert(String content) {
		List<Week> response = new ArrayList<Week>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONArray weeks = jsonObject.getJSONObject("rounds").getJSONArray("data");
			for (int i = 0; i < weeks.length(); i++) {
				JSONObject week = weeks.getJSONObject(i);
				response.add(new Week(week));
			}
			return response;
		} catch (JSONException e) {
			throw new RuntimeException("Error reading weeks from JSON : "+e.getMessage());
			
		}
	}

}
