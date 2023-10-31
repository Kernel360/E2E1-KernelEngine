package com.example.e2ekernelengine.user.exception;

public class LoginException extends RuntimeException {
	public LoginException() {
	}

	public LoginException(String message) {
		super(message);
	}
}
