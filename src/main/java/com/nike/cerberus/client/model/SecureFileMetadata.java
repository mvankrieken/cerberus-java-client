package com.nike.cerberus.client.model;

public class SecureFileMetadata {

	private String filename;
	private int contentLength;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public String toString() {
		return "SecureFileMetadata [filename=" + filename + ", contentLength=" + contentLength + "]";
	}

}
