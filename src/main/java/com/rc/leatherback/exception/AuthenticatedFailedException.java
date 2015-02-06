package com.rc.leatherback.exception;

public class AuthenticatedFailedException extends RuntimeException {
    private static final long serialVersionUID = 6524757115961008203L;

    public AuthenticatedFailedException(String message) {
        super(message);
    }
}
