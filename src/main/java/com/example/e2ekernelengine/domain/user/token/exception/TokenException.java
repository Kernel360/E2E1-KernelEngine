package com.example.e2ekernelengine.domain.user.token.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenException extends RuntimeException {

	public TokenException(String message) {
		super(message);
	}
}