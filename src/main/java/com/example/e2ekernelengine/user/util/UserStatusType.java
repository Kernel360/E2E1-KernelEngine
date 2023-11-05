package com.example.e2ekernelengine.user.util;

public enum UserStatusType {
	ACTIVE("active"),
	INACTIVE("inactive"),
	SUSPENDED("suspended"),
	WITHDRAWAL("withdrawal");

	private final String value;

	UserStatusType(String value) {
		this.value = value;
	}

}
