package com.example.e2ekernelengine.user.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.e2ekernelengine.user.db.entity.User;

public class CustomUserDetail implements UserDetails {

	private final String email;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetail(String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static CustomUserDetail build(User user) {
		String userRole = "ROLE_" + user.getUserPermissionType().getValue().toUpperCase();
		Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(userRole));

		return new CustomUserDetail(user.getUserEmail(), user.getUserPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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
