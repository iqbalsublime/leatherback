package com.rc.leatherback.facade.dto;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

public class ErrorMessageDto implements Serializable {
	private static final long serialVersionUID = 1314689164651047961L;

	private int status;
	private String code;
	private String traceId;
	private String message;
	private String developerMessage;
	private String link;

	public int getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		setStatus(status.getStatusCode());
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
