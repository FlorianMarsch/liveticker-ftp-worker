package de.florianmarsch.playerdata.club;

public enum Club {

	FCBayernMuenchen(1,10285),
	BorussiaDortmund(5,10303),
	Bayer04Leverkusen(8,10281),
	BorussiaMgladbach(3,10307),
	FCSchalke04(10,10576),
	FSVMainz05(18,10388),
	HerthaBSC(7,10437),
	VfLWolfsburg(12,10653),
	FCKoeln(13,10476),
	HamburgerSV(4,10419),
	FCIngolstadt04(90,10453),
	FCAugsburg(68,10269),
	SVWerderBremen(6,10677),
	SVDarmstadt98(89,10329),
	TSG1899Hoffenheim(62,10442),
	EintrachtFrankfurt(9,10347),
	VfBStuttgart(14,10646),
	Hannover96(17,10423);
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
