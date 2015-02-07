package com.rc.leatherback.exception;

public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -8887054826383571025L;

	public UserNotFoundException(String message) {
		super(message);
	}
}