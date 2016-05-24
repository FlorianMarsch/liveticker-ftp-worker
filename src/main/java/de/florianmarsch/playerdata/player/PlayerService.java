package de.florianmarsch.playerdata.player;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.florianmarsch.playerdata.WorkerMain;
import de.florianmarsch.playerdata.dataprovider.Player;

public class PlayerService {

	final static Logger logger = LoggerFactory.getLogger(WorkerMain.class);

	Map<String, Player> map = new HashMap<String, Player>();

	public PlayerService() {
		try {
			String all = loadFile("http://fussballmanager.herokuapp.com/Player");
			JSONObject data = new JSONObject(all);
			JSONArray players = data.getJSONArray("data");
			for (int i = 0; i < players.length(); i++) {
				JSONObject player = players.getJSONObject(i);

				Player tempPlayer = new Player();
				tempPlayer.setId(player.getString("id"));
				tempPlayer.setComunio(player.getString("comunio"));
				tempPlayer.setFeedmonster(player.getString("feedmonster"));
				tempPlayer.setName(player.getString("name"));
				tempPlayer.setAbbreviationName(player.getString("abbreviationName"));
				tempPlayer.setPosition(player.getString("position"));

				map.put(tempPlayer.getFeedmonster(), tempPlayer);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		logger.info("already " + map.size() + " players here");

	}

	public void save(Player aPlayer) throws IOException, JSONException {
		String relatedID = aPlayer.getFeedmonster();
		Player tPlayer = map.get(relatedID);
		if (tPlayer == null) {
			logger.info("new player detected : " + aPlayer.getName());
			submit(getJson(aPlayer), null);
		} else {
			submit(getJson(aPlayer), tPlayer.getId());
		}
	}

	private String getJson(Player aPlayer) {
		JSONObject jsono = new JSONObject();

		try {
			jsono.put("comunio", aPlayer.getComunio());
			jsono.put("feedmonster", aPlayer.getFeedmonster());
			jsono.put("lastName", aPlayer.getLastName());
			jsono.put("name", aPlayer.getName());
			jsono.put("abbreviationName", aPlayer.getAbbreviationName());
			jsono.put("position", aPlayer.getPosition());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsono.toString();
	}

	void submit(String json, String id) throws MalformedURLException, IOException, ProtocolException {
		String endpoint = "http://fussballmanager.herokuapp.com/Player/";
		if (id == null) {
			endpoint += "new";
		} else {
			endpoint += id;
		}
		URL url = new URL(endpoint);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(json);
		out.close();
		httpCon.getInputStream();
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempReturn.toString();
	}

}
