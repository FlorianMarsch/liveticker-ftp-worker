package com.heroku.devcenter.axis.player;


public class Player {

	private String externID;
	private String id;
	private String name;
	private String normalizedName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExternID() {
		return externID;
	}
	public void setExternID(String externID) {
		this.externID = externID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNormalizedName() {
		return normalizedName;
	}
	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
	}

	
}
