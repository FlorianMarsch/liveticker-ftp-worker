package de.florianmarsch.playerdata.dataprovider;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.florianmarsch.playerdata.club.Club;

public class FeedmonsterDataService {

	public List<FeedmonsterPlayer> getAllPlayers() {
		List<FeedmonsterPlayer> tempReturn = new ArrayList<FeedmonsterPlayer>();

		List<Club> clubs = new ArrayList<Club>();
		clubs.addAll(Arrays.asList(Club.values()));

		for (Club club : clubs) {
			tempReturn.addAll(getplayersbyclubid(club.getRestId()));
		}

		return tempReturn;
	}

	private Collection<FeedmonsterPlayer> getplayersbyclubid(Integer restId) {
		List<FeedmonsterPlayer> result = new ArrayList<FeedmonsterPlayer>();
		try {
			String all = loadFile("https://vintagemonster.onefootball.com/api/teams/de/" + restId + ".json");
			JSONObject document = new JSONObject(all);
			JSONArray players = document.getJSONObject("data").getJSONObject("team").getJSONArray("players");
		
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				
				FeedmonsterPlayer tempPlayer = new FeedmonsterPlayer();
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setName(player.getString("name"));
				tempPlayer.setCountry(player.getString("country"));
				tempPlayer.setFirstName(player.getString("firstName"));
				tempPlayer.setLastName(player.getString("lastName"));
				tempPlayer.setPosition(player.getString("position"));
				result.add(tempPlayer);
			}
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	private String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempReturn.toString();
	}
	

}
