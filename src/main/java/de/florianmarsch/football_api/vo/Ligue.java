package de.florianmarsch.football_api.vo;

import org.json.JSONException;
import org.json.JSONObject;

public class Ligue {

	private Integer id;
	private String name;
	private Integer currentSeason;
	
	public Ligue(JSONObject aJsonObject){
		try {
			id = aJsonObject.getInt("id");
			JSONObject season = aJsonObject.getJSONObject("currentSeason");
			currentSeason = season.getInt("id");
			name = aJsonObject.getString("name") +" "+season.getString("name");
		} catch (JSONException e) {
			throw new RuntimeException("Error converting ligue from JSON : "+e.getMessage());
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

	public Integer getCurrentSeason() {
		return currentSeason;
	}

	public void setCurrentSeason(Integer currentSeason) {
		this.currentSeason = currentSeason;
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
		Ligue other = (Ligue) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	public JSONObject toJson() {
		try {
			JSONObject json = new JSONObject();
			json.put("id", id);
			json.put("name", name);
			json.put("currentSeason", currentSeason);
			return json;
		} catch (JSONException e) {
			throw new RuntimeException("Error creating JSON : " + e.getMessage());
		}
	}
	
}
