package de.florianmarsch.football_api.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.WorkerMain;
import de.florianmarsch.football_api.vo.Event;

public class EventConverter {

	final static Logger logger = LoggerFactory.getLogger(EventConverter.class);

	
	public Collection<? extends Event> convert(String content) {
		List<Event> response = new ArrayList<Event>();
		try {
			JSONObject jsonObject = new JSONObject(content);
			JSONArray fixtures = jsonObject.getJSONArray("fixtures");
			
			for (int i = 0; i < fixtures.length(); i++) {
				JSONObject fixture = fixtures.getJSONObject(i);
				JSONArray events = fixture.getJSONObject("events").getJSONArray("data");
				for (int j = 0; j < events.length(); j++) {
					JSONObject event = events.getJSONObject(j);
					String type = event.getString("type");
					Boolean isGoal = type.equalsIgnoreCase("goal");
					Boolean isOwn = type.equalsIgnoreCase("own");
					Boolean isPenalty = type.equalsIgnoreCase("penalty");
					if(isGoal || isOwn || isPenalty){
						response.add(new Event(event));
					}
				}
			}
			return response;
		} catch (JSONException e) {
			logger.error(content);
			throw new RuntimeException("Error reading Events from JSON : "+e.getMessage());
		}
	}

}
