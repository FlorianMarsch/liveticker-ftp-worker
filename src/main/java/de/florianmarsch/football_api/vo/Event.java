package de.florianmarsch.football_api.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class Event {

	private Integer id;
	private String type;
	private String player;

	public Event(JSONObject aJsonObject) {
		try {
			id = aJsonObject.getInt("eventId");
			type = aJsonObject.getString("type");
			player = aJsonObject.getJSONObject("player").getString("name");
		} catch (JSONException e) {
			throw new RuntimeException("Error converting Event from JSON : " + e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public JSONObject toJson() {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("type", type);
			json.put("player", player);
			return json;
		} catch (JSONException e) {
			throw new RuntimeException("Error creating JSON : " + e.getMessage());
		}
	}


}
