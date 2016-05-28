package de.florianmarsch.liveticker.ftp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPJob implements Job {

	final static Logger logger = LoggerFactory.getLogger(FTPJob.class);
	private FTPClient client;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("start ftp push");

		List<FTPPushTask> taks = getTasks();
		for (FTPPushTask task : taks) {
			try {
				String url = task.getUrl();
				String content = loadFile(url);
				String filename = task.getFilename();
				String directory = task.getDirectory();
				login();
				createDirs(directory);
				logout();
				login();
				upload(content, directory + filename);
				logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		logger.info("end ftp push");
	}

	private void logout() {
		try {
			client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				client.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void login() {
		client = new FTPClient();
		String host = System.getenv("FTP_HOST");
		String user = System.getenv("FTP_USER");
		String pw = System.getenv("FTP_PASSWD");
		try {
			client.connect(host);
			client.login(user, pw);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private List<FTPPushTask> getTasks() {
		List<FTPPushTask> tasks = new ArrayList<FTPPushTask>();

		FTPPushTask task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue");
		task.setDirectory("v1/api/");
		task.setFilename("ligue.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("team.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/events");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("events.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/weeks");
		task.setDirectory("v1/api/ligue/1229/");
		task.setFilename("weeks.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10285/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10285.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10303/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10303.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10281/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10281.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10307/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10307.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10576/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10576.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10388/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10388.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10437/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10437.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10653/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10653.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10476/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10476.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10419/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10419.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10453/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10453.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10269/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10269.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10677/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10677.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10329/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10329.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10442/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10442.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10347/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10347.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10646/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10646.json");
		tasks.add(task);

		task = new FTPPushTask();
		task.setUrl("http://liveticker-system-api.herokuapp.com/api/ligue/1229/team/10423/squad");
		task.setDirectory("v1/api/ligue/1229/team/");
		task.setFilename("10423.json");
		tasks.add(task);

		return tasks;
	}

	private void createDirs(String directory) {

		List<String> asList = Arrays.asList(directory.split("/"));
		for (String string : asList) {
			if (string != null) {
				String pathname = string.trim();
				if (!pathname.isEmpty()) {
					try {
						client.makeDirectory(pathname);
						client.changeWorkingDirectory("./" + pathname);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void upload(String content, String filename) {

		InputStream fis = null;

		try {

			fis = new ByteArrayInputStream(content.getBytes());

			//
			// Store file to server
			//
			client.storeFile(filename, fis);
			client.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
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

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempReturn.toString();
	}

}
