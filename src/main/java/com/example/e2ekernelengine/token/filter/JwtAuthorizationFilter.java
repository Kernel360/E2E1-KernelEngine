package com.example.e2ekernelengine.token.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.e2ekernelengine.token.service.TokenService;
import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	private final TokenService tokenService;
	private final String cookieName;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {
		String token = null;
		try {
			token = Arrays.stream(request.getCookies())
					.filter(cookie -> cookie.getName().equals(cookieName)).findFirst()
					.map(Cookie::getValue)
					.orElse(null);
		} catch (Exception ignored) {
		}
		if (token != null) {
			try {
				Authentication authentication = getUserEmailPasswordAuthenticationToken(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (Exception e) {
				Cookie cookie = new Cookie(cookieName, null);
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}
		chain.doFilter(request, response);
	}

	private Authentication getUserEmailPasswordAuthenticationToken(String token) {
		String userEmail = tokenService.getUserEmail(token);
		if (userEmail != null) {
			Optional<User> OptionalUser = userRepository.findByUserEmail(userEmail);
			User user = OptionalUser.get();
			return new UsernamePasswordAuthenticationToken(
					user.getUserName(),
					null,
					Collections.singletonList(
							new SimpleGrantedAuthority("ROLE_" + user.getUserPermissionType().getValue().toUpperCase())
					)
			);
		}
		return null;
	}
}