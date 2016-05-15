package de.florianmarsch.playerdata.dataprovider;

public class Player {

	private String id;
	
	private String comunio;
	private String feedmonster;
	
	private String country;
	private String firstName;
	private String lastName;
	private String name;
	private String abbreviationName;
	private String position;
	private String age;
	private String thumbnail;
	
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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
	
	
}
