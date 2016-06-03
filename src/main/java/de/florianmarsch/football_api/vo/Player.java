package de.florianmarsch.football_api.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class Player {
	private Integer id;
	private String name;
	private String position;
	private Integer team;
	
	public Player(JSONObject aJsonObject){
		try {
			id = aJsonObject.getInt("id");
			name = aJsonObject.getString("name");
			position = aJsonObject.getJSONObject("position").getString("name");
		} catch (JSONException e) {
			throw new RuntimeException("Error converting Player from JSON : "+e.getMessage());
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getTeam() {
		return team;
	}

	public void setTeam(Integer team) {
		this.team = team;
	}
	
	public JSONObject toJson() {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("name", name);
			json.put("position", position);
			json.put("team", team);
			return json;
		} catch (JSONException e) {
			throw new RuntimeException("Error creating JSON : " + e.getMessage());
		}
	}
}
