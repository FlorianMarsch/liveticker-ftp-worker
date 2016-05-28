package de.florianmarsch.liveticker.ftp;

public class FTPPushTask {

	private String url;
	private String directory;
	private String filename;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "FTPPushTask [url=" + url + ", directory=" + directory + ", filename=" + filename + "]";
	}

	

}
