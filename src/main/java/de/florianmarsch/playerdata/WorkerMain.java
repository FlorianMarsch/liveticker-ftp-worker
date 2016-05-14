package de.florianmarsch.playerdata;

import java.text.Normalizer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.playerdata.axis.PlayerItem;
import de.florianmarsch.playerdata.axis.PlayerItemWebservice;
import de.florianmarsch.playerdata.player.Player;
import de.florianmarsch.playerdata.player.PlayerService;

public class WorkerMain {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	
	public static void main(String[] args) throws Exception {
		logger.info("start player update");
		PlayerService playerService = new PlayerService();
		PlayerItemWebservice webService = new PlayerItemWebservice();
		List<PlayerItem> allPlayerItems = webService.getAllPlayers();
		logger.info("recive "+allPlayerItems.size()+" player info");
		for (PlayerItem playerItem : allPlayerItems) {
			Player tempPlayer = new Player();
			tempPlayer.setExternID(String.valueOf(playerItem.getId()));
			tempPlayer.setName(playerItem.getName());

			String norm = Normalizer.normalize(playerItem.getName(), Normalizer.Form.NFD);
			norm = norm.replaceAll("[^\\p{ASCII}]", "");
			tempPlayer.setNormalizedName(norm);

			playerService.save(tempPlayer);
		}
		logger.info("end player update");
		

	}

}
