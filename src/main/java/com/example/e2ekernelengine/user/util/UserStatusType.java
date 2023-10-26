package com.example.e2ekernelengine.entity;

public enum UserStatusType {
	ACTIVE("active"),
	INACTIVE("inactive"),
	SUSPENDED("suspended"),
	WITHDRAWAL("withdrawal");

	private final String value;

	UserStatusType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
