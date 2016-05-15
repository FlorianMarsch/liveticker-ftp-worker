package de.florianmarsch.playerdata.club;

public enum Club {

	FCBayernMuenchen(1,6),
	BorussiaDortmund(5,155),
	Bayer04Leverkusen(8,162),
	BorussiaMgladbach(3,154),
	FCSchalke04(10,144),
	FSVMainz05(18,163),
	HerthaBSC(7,168),
	VfLWolfsburg(12,32),
	FCKoeln(13,151),
	HamburgerSV(4,157),
	FCIngolstadt04(90,167),
	FCAugsburg(68,171),
	SVWerderBremen(6,150),
	SVDarmstadt98(89,789),
	TSG1899Hoffenheim(62,158),
	EintrachtFrankfurt(9,156),
	VfBStuttgart(14,31),
	Hannover96(17,153);
//	SCFreiburg(21,159),
//	RBLeipzig(,885),
//	FCNuernberg(2,160);
	
	private Integer webserviceId;
	private Integer restId;
	
	
	
	private Club(Integer webserviceId, Integer restId) {
		this.webserviceId = webserviceId;
		this.restId = restId;
	}

	public Integer getRestId() {
		return restId;
	}

	public Integer getWebserviceId() {
		return webserviceId;
	}
	
	
	
}
