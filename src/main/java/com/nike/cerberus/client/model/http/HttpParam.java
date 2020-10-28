package com.nike.cerberus.client.model.http;

public enum HttpParam {
	
   LIST("list"),
   LIMIT("limit"),
   OFFSET("offset"),
   VERSION_ID("versionId"),
   SDB_NAME("sdbName");
   
   private String httpParam;
   
   HttpParam(String httpParam) {
       this.httpParam = httpParam;
   }

   public String get() {
		return httpParam;
	}
}
