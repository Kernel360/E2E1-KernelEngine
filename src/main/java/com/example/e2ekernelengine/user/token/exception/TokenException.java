package com.example.e2ekernelengine.user.token.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenException extends RuntimeException {

	public TokenException(String message) {
		super(message);
	}
}