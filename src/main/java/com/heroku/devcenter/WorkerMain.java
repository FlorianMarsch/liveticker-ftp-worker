package com.heroku.devcenter;

import java.text.Normalizer;
import java.util.List;

import com.heroku.devcenter.axis.PlayerItem;
import com.heroku.devcenter.axis.PlayerItemWebservice;
import com.heroku.devcenter.axis.player.Player;
import com.heroku.devcenter.axis.player.PlayerService;

public class WorkerMain {

	public static void main(String[] args) throws Exception {
		PlayerService playerService = new PlayerService();
		PlayerItemWebservice webService = new PlayerItemWebservice();
		List<PlayerItem> allPlayerItems = webService.getAllPlayers();

		for (PlayerItem playerItem : allPlayerItems) {
			Player tempPlayer = new Player();
			tempPlayer.setExternID(String.valueOf(playerItem.getId()));
			tempPlayer.setName(playerItem.getName());

			String norm = Normalizer.normalize(playerItem.getName(), Normalizer.Form.NFD);
			norm = norm.replaceAll("[^\\p{ASCII}]", "");
			tempPlayer.setNormalizedName(norm);

			playerService.save(tempPlayer);
		}

	}

}
