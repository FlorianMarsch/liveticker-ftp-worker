package de.florianmarsch.playerdata.dataprovider.comunio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.playerdata.club.Club;
import de.florianmarsch.playerdata.dataprovider.AbstractDataProvider;

public class ComunioDataService extends AbstractDataProvider{

	
	public Collection<ComunioPlayer> getPlayersByClubId(Club club) {
		List<ComunioPlayer> result = new ArrayList<ComunioPlayer>();
		try {
			String all = loadFile("http://api.comunio.de/clubs/"+club.getWebserviceId());
			JSONArray players = new JSONObject(all).getJSONArray("squad");
		
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				
				ComunioPlayer tempPlayer = new ComunioPlayer();
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setName(normalize(player.getString("name")).trim());
				result.add(tempPlayer);
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	
}