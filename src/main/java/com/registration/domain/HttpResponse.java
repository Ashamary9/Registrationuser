package com.registration.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpResponse {
    
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "India/IST")
	private Date timeStamp;
	private int httpStatusCode;
	private String httpStatus;
	private String message;
	private String reason;
	
	
	

	public HttpResponse(int value, HttpStatus forbidden, String upperCase, String forbiddenmessage) {
		// TODO Auto-generated constructor stub
	}




	public HttpResponse() {
		super();
		// TODO Auto-generated constructor stub
	}




	public HttpResponse(Date timeStamp, int httpStatusCode, String httpStatus, String message, String reason) {
		super();
		this.timeStamp = timeStamp;
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.message = message;
		this.reason = reason;
	}




	public Date getTimeStamp() {
		return timeStamp;
	}




	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}




	public int getHttpStatusCode() {
		return httpStatusCode;
	}




	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}




	public String getHttpStatus() {
		return httpStatus;
	}




	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public String getReason() {
		return reason;
	}




	public void setReason(String reason) {
		this.reason = reason;
	}

	
	
	
	
	
	
	
}
