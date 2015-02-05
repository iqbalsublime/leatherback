package com.rc.leatherback.exception;

public class ReportNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7585324883974114365L;

	public ReportNotFoundException(String message) {
		super(message);
	}
}
