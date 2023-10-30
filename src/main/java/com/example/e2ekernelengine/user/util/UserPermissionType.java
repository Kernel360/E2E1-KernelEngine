package com.example.e2ekernelengine.user.util;

public enum UserPermissionType {
	MEMBER("member"),
	ADMIN("admin");

	private final String value;

	UserPermissionType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
