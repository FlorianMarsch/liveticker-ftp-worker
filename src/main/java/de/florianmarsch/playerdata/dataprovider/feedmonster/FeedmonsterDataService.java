package de.florianmarsch.playerdata.dataprovider.feedmonster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.playerdata.club.Club;
import de.florianmarsch.playerdata.dataprovider.AbstractDataProvider;

public class FeedmonsterDataService extends AbstractDataProvider{



	public Collection<FeedmonsterPlayer> getPlayersByClubId(Club club) {
		List<FeedmonsterPlayer> result = new ArrayList<FeedmonsterPlayer>();
		try {
			String all = loadFile("http://liveticker-system-api.herokuapp.com/api/team/"+club.getRestId()+"/squad");
			JSONArray players = new JSONObject(all).getJSONArray("data");
		
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				
				FeedmonsterPlayer tempPlayer = new FeedmonsterPlayer();
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setName(normalize(player.getString("name")));
				tempPlayer.setPosition(player.getString("position"));
				result.add(tempPlayer);
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}


	

}
