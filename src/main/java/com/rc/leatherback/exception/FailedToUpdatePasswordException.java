package com.rc.leatherback.exception;

public class FailedToUpdatePasswordException extends RuntimeException {
	private static final long serialVersionUID = -3068610455685819609L;

	public FailedToUpdatePasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}