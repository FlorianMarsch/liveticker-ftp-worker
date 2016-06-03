package de.florianmarsch.ftp;

public class PushTask {

	private String content;
	private String directory;
	private String filename;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
		return "PushTask [content=" + content.length() + " bytes, directory=" + directory + ", filename=" + filename + "]";
	}

}
