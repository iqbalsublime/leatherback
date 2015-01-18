package com.rc.leatherback.exception;

public class PrescriptionNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 8281918129288010671L;

    public PrescriptionNotFoundException(String message) {
        super(message);
    }
}
