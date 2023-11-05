package com.example.e2ekernelengine.user.token.filter;

import java.io.IOException;
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

import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;
import com.example.e2ekernelengine.user.token.service.TokenService;

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

		Optional<String> token = extractTokenFromCookie(request);
		token.ifPresent(presentToken -> validateAndAuthenticateToken(response, presentToken));
		chain.doFilter(request, response);
	}

	private Optional<String> extractTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return Optional.of(cookie.getValue());
				}
			}
		}
		return Optional.empty();
	}

	private void validateAndAuthenticateToken(HttpServletResponse response, String token) {
		try {
			String userEmail = tokenService.getUserEmail(token);
			userRepository.findByUserEmail(userEmail).ifPresent(user -> {
				Authentication auth = getAuthentication(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
			});
		} catch (Exception e) {
			clearCookie(response);
		}
	}

	private Authentication getAuthentication(User user) {
		return new UsernamePasswordAuthenticationToken(
				user.getUserName(),
				null,
				Collections.singletonList(
						new SimpleGrantedAuthority("ROLE_" + user.getUserPermissionType().getValue().toUpperCase()))
		);
	}

	private void clearCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}