package de.florianmarsch.playerdata.dataprovider;

public class Player {

	private String id;
	
	private String comunio;
	private String feedmonster;
	
	private String name;
	private String abbreviationName;
	private String position;
	
	public String getComunio() {
		return comunio;
	}
	public void setComunio(String comunio) {
		this.comunio = comunio;
	}
	public String getFeedmonster() {
		return feedmonster;
	}
	public void setFeedmonster(String feedmonster) {
		this.feedmonster = feedmonster;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbreviationName() {
		return abbreviationName;
	}
	public void setAbbreviationName(String abbreviationName) {
		this.abbreviationName = abbreviationName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getLastName(){
		String[] split = name.split(" ");
		return split[split.length-1];
	}
	
}
