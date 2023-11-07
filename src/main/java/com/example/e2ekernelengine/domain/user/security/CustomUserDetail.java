package com.example.e2ekernelengine.domain.user.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.e2ekernelengine.domain.user.db.entity.User;

import lombok.Getter;

public class CustomUserDetail implements UserDetails {

	private final Collection<? extends GrantedAuthority> authorities;

	@Getter
	private final User user;

	public CustomUserDetail(User user) {
		this.user = user;
		String userRole = "ROLE_" + user.getUserPermissionType().getValue().toUpperCase();
		this.authorities = Collections.singleton(new SimpleGrantedAuthority(userRole));
	}

	public static CustomUserDetail build(User user) {
		return new CustomUserDetail(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.user.getUserPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getUserEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
