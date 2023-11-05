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
	private final String cookieRefreshName;
	private final String accessTokenPlusMinute;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain
	) throws IOException, ServletException {

		Optional<String> accessToken = extractTokenFromCookie(request, cookieName);
		Optional<String> refreshToken = extractTokenFromCookie(request, cookieRefreshName);

		if (accessToken.isPresent()) {
			processAccessToken(accessToken.get(), response);
		} else {
			refreshToken.ifPresentOrElse(
					presentRefreshToken -> processRefreshToken(presentRefreshToken, response),
					() -> clearAuthenticationAndCookie(response)
			);
		}

		chain.doFilter(request, response);
	}

	private void processAccessToken(String accessToken, HttpServletResponse response) {
		if (tokenService.isTokenExpired(accessToken)) {
			clearAuthenticationAndCookie(response);
		} else {
			validateAndAuthenticateToken(response, accessToken);
		}
	}

	private void processRefreshToken(String refreshToken, HttpServletResponse response) {
		if (!tokenService.isTokenExpired(refreshToken)) {
			try {
				String newAccessToken = tokenService.createAccessTokenByRefreshToken(refreshToken);
				addAuthenticationCookie(response, newAccessToken);
				validateAndAuthenticateToken(response, newAccessToken);
			} catch (Exception e) {
				clearAuthenticationAndCookie(response);
			}
		} else {
			clearAuthenticationAndCookie(response);
		}
	}

	private Optional<String> extractTokenFromCookie(HttpServletRequest request, String cookieName) {
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

	private void addAuthenticationCookie(HttpServletResponse response, String token) {
		Cookie accessTokenCookie = new Cookie(cookieName, token);
		accessTokenCookie.setHttpOnly(true);
		accessTokenCookie.setPath("/");
		accessTokenCookie.setMaxAge(Integer.parseInt(accessTokenPlusMinute) * 60);
		response.addCookie(accessTokenCookie);
	}

	private void validateAndAuthenticateToken(HttpServletResponse response, String token) {
		try {
			String userEmail = tokenService.getUserEmail(token);
			userRepository.findUserByUserEmail(userEmail).ifPresent(user -> {
				Authentication auth = getAuthentication(user);
				SecurityContextHolder.getContext().setAuthentication(auth);
			});
		} catch (Exception e) {
			clearAuthenticationAndCookie(response);
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

	private void clearAuthenticationAndCookie(HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
}