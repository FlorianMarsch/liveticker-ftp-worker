package de.florianmarsch.playerdata.dataprovider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.florianmarsch.playerdata.club.Club;
import de.florianmarsch.playerdata.dataprovider.comunio.ComunioDataService;
import de.florianmarsch.playerdata.dataprovider.comunio.ComunioPlayer;
import de.florianmarsch.playerdata.dataprovider.feedmonster.FeedmonsterDataService;
import de.florianmarsch.playerdata.dataprovider.feedmonster.FeedmonsterPlayer;

public class DataService {
	
	private FeedmonsterDataService feedmonster = new FeedmonsterDataService();
	private ComunioDataService comunio = new ComunioDataService();
	
	public List<Player> getAllPlayers() {
		List<Player> tempReturn = new ArrayList<Player>();

		List<Club> clubs = new ArrayList<Club>();
		clubs.addAll(Arrays.asList(Club.values()));

		for (Club club : clubs) {
			tempReturn.addAll(getPlayersByClubId(club));
		}

		return tempReturn;
	}

	private Collection<Player> getPlayersByClubId(Club club) {
		List<Player> result = new ArrayList<Player>();
		Collection<FeedmonsterPlayer> feedmonsterPlayers = feedmonster.getPlayersByClubId(club);
		Collection<ComunioPlayer> comunioPlayers = comunio.getPlayersByClubId(club);
		
		for (FeedmonsterPlayer feedmonsterPlayer : feedmonsterPlayers) {
			Player player = new Player();
			applyValues(player,feedmonsterPlayer );
			ComunioPlayer comunioPlayer = findComunioPlayer(player,comunioPlayers );
			applyValues(player,comunioPlayer );
			result.add(player);
		}
		
		return result;
	}

	private void applyValues(Player player, ComunioPlayer comunioPlayer) {
		if(comunioPlayer != null){
			player.setComunio(comunioPlayer.getId());
			player.setAbbreviationName(comunioPlayer.getName());
		}else{
			System.out.println("Found no Comunio for "+player.getName());
			player.setAbbreviationName(player.getLastName());
		}
	}

	private ComunioPlayer findComunioPlayer(Player player, Collection<ComunioPlayer> comunioPlayers) {
		for (ComunioPlayer comunioPlayer : comunioPlayers) {
			String name = player.getLastName();
			if(name == null){
				// Raffael or Chicharito
				name = player.getFirstName();
			}
			if(name.equals(comunioPlayer.getLastName())){
				return comunioPlayer;
			}
		}
		return null;
	}

	private void applyValues(Player player, FeedmonsterPlayer feedmonsterPlayer) {
		player.setAge(feedmonsterPlayer.getAge());
		player.setCountry(feedmonsterPlayer.getCountry());
		player.setFeedmonster(feedmonsterPlayer.getId());
		player.setFirstName(feedmonsterPlayer.getFirstName());
		player.setLastName(feedmonsterPlayer.getLastName());
		player.setName(feedmonsterPlayer.getName());
		player.setPosition(feedmonsterPlayer.getPosition());
		player.setThumbnail(feedmonsterPlayer.getThumbnail());
	}

}
