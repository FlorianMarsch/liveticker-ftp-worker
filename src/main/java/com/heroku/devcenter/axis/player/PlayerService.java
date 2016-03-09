package com.heroku.devcenter.axis.player;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerService  {

	Map<String, Player> map = new HashMap<String, Player>();
	
	public PlayerService(){
		try {
			String all = loadFile("http://fussballmanager.herokuapp.com/Player");
			JSONObject data = new JSONObject(all);
			JSONArray players = data.getJSONArray("data");
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);
				
				Player tempPlayer = new Player();
				tempPlayer.setExternID(player.getString("externID"));
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setName(player.getString("name"));
				tempPlayer.setNormalizedName(player.getString("normalizedName"));
				map.put(tempPlayer.getExternID(), tempPlayer);
			}
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void save(Player aPlayer) throws IOException, JSONException {
		String externID = aPlayer.getExternID();
		Player tPlayer = map.get(externID);
		if(tPlayer == null){
			JSONObject tempJSONObject = new JSONObject();
			
			tempJSONObject.put("externID", aPlayer.getExternID());
			tempJSONObject.put("name", aPlayer.getName());
			tempJSONObject.put("normalizedName", aPlayer.getNormalizedName());
			
			String json = tempJSONObject .toString();
			URL url = new URL("http://fussballmanager.herokuapp.com/Player/new");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");
			OutputStreamWriter out = new OutputStreamWriter(
			    httpCon.getOutputStream());
			out.write(json);
			out.close();
			httpCon.getInputStream();
		}
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
