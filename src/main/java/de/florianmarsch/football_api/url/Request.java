package de.florianmarsch.football_api.url;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Request {
	

	final static Logger logger = LoggerFactory.getLogger(Request.class);
	
	private URL url;
	private Integer retries = 0;
	
	public Request(URL aUrl){
		url = aUrl;
	}
	
	
	public String submit() {
		System.out.println(new Date() + " : " + url.toExternalForm());
		StringBuffer tempReturn = new StringBuffer();
		try {
			InputStream is = url.openStream();
			DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			String s;

			while ((s = dis.readLine()) != null) {
				tempReturn.append(s);
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException("Malformed URL : " + e.getMessage());
		} catch (IOException e) {
			if(retries < 3){
				retries = retries +1;
				System.out.println(new Date() + " : http 429 : too many attempts");
				try {
					Thread.sleep(45000*retries);
				} catch (InterruptedException e1) {
				}
				return submit();
			}else{
				throw new RuntimeException("IO after "+retries+" retries : " + e.getMessage());
			}
		}
		return tempReturn.toString();
	}
}
