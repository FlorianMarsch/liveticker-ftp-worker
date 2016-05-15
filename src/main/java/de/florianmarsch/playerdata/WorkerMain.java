package de.florianmarsch.playerdata;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.playerdata.dataprovider.DataService;
import de.florianmarsch.playerdata.dataprovider.Player;
import de.florianmarsch.playerdata.player.PlayerService;

public class WorkerMain {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	
	public static void main(String[] args) throws Exception {
		logger.info("start player update");
		PlayerService playerService = new PlayerService();
		DataService dataService = new DataService();
		List<Player> allPlayers = dataService.getAllPlayers();
		logger.info("recive "+allPlayers.size()+" player info");
		for (Player player : allPlayers) {
			playerService.save(player);
		}
		logger.info("end player update");
		

	}

}
