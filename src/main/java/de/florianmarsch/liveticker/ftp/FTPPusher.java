package de.florianmarsch.liveticker.ftp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPPusher {
	final static Logger logger = LoggerFactory.getLogger(FTPPusher.class);

	public void save(FTPPushTask task) {
		try {

			String url = task.getUrl();
			String content = loadFile(url);
			String filename = task.getFilename();
			String directory = task.getDirectory();
			upload(content, directory ,filename);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void upload(String content, String dir, String filename) {
		CloseableHttpClient client = HttpClients.createDefault();
		String host = System.getenv("ftpgateway");
		HttpPost httpPost = new HttpPost(host);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("file", filename));
		nvps.add(new BasicNameValuePair("dir", dir));
		nvps.add(new BasicNameValuePair("data", content));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			client.execute(httpPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String loadFile(String url) {

		StringBuffer tempReturn = new StringBuffer();
		try {
			URL u = new URL(url);
			InputStream is = u.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error reciving file : " + e.getMessage());
		}
		return tempReturn.toString();
	}

}
