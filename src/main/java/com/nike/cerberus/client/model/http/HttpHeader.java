package com.nike.cerberus.client.model.http;

public enum HttpHeader {
	
    CERBERUS_TOKEN("X-Cerberus-Token"),
    LOCATION("Location"),
    ACCEPT("Accept"),
    CONTENT_TYPE("Content-Type"),
    CONTENT_DISPOSITION("Content-Disposition"),
    CONTENT_LENGTH("Content-Length");
    
    private String httpHeader;
    
    HttpHeader(String httpHeader) {
        this.httpHeader = httpHeader;
    }
 
    public String get() {
		return httpHeader;
	}
}
