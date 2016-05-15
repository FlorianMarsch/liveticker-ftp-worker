package de.florianmarsch.playerdata.dataprovider.comunio;

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

public class ComunioDataService {
	public List<ComunioPlayer> getAllPlayers() {
		List<ComunioPlayer> tempReturn = new ArrayList<ComunioPlayer>();

		List<Club> clubs = new ArrayList<Club>();
		clubs.addAll(Arrays.asList(Club.values()));

		for (Club club : clubs) {
			tempReturn.addAll(getplayersbyclubid(club.getWebserviceId()));
		}

		return tempReturn;
	}
	
	private Collection<ComunioPlayer> getplayersbyclubid(Integer restId) {
		List<ComunioPlayer> result = new ArrayList<ComunioPlayer>();
		try {
			String all = loadFile("http://api.comunio.de/clubs/"+restId);
			JSONArray players = new JSONObject(all).getJSONArray("squad");
		
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				
				ComunioPlayer tempPlayer = new ComunioPlayer();
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setName(player.getString("name"));
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