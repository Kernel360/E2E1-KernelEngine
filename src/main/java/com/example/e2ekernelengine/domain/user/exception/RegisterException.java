package com.example.e2ekernelengine.domain.user.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegisterException extends RuntimeException {

	public RegisterException(String message) {
		super(message);
	}
}
