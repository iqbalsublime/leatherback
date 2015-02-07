package com.rc.leatherback.exception;

public class AuthorisationNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2294912540159796241L;

	public AuthorisationNotFoundException(String message) {
		super(message);
	}
}