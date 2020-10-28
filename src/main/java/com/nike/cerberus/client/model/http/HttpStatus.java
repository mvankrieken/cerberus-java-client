package com.nike.cerberus.client.model.http;

/**
 * Enum for HTTP status codes interpreted by the Cerberus client.
 */
public enum HttpStatus {

    OK(200),
    CREATED(201),
    ACCEPTED(202),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    CONFLICT(409),
    TOO_MANY_REQUESTS(429),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503),
    GATEWAY_TIMEOUT(504);
    
    private int httpStatus;
    
    HttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }
 
    public int get() {
		return httpStatus;
	}
}
