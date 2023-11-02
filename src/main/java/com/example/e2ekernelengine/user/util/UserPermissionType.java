package com.example.e2ekernelengine.user.util;

import java.util.Arrays;
import java.util.List;

public enum UserPermissionType {
	USER("user"),
	ADMIN("admin");

	private final String value;

	UserPermissionType(String value) {
		this.value = value;
	}

	public static List<UserPermissionType> getAllUserPermissionTypes() {
		return Arrays.asList(values());
	}

	public String getValue() {
		return value;
	}
}
