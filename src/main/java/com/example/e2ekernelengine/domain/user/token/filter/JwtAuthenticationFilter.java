package com.example.e2ekernelengine.domain.user.token.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.security.CustomUserDetail;
import com.example.e2ekernelengine.domain.user.token.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final String cookieName;
	private final String cookieRefreshName;
	private final String accessTokenPlusMinute;
	private final String refreshTokenPlusHour;

	@Override
	public Authentication attemptAuthentication(
			HttpServletRequest request,
			HttpServletResponse response
	) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				request.getParameter("userEmail"),
				request.getParameter("userPassword"),
				new ArrayList<>()
		);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		return authentication;
	}

	@Override
	protected void successfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authResult
	) throws IOException {
		CustomUserDetail userDetail = (CustomUserDetail)authResult.getPrincipal();
		User user = userDetail.getUser();

		String accessToken = tokenService.createAccessToken(user);
		addAuthenticationCookie(request, response, accessToken, true);

		String refreshToken = tokenService.createAndStoreRefreshToken(user);
		addAuthenticationCookie(request, response, refreshToken, false);

		response.sendRedirect("/");
		/*String preLoginUrl = request.getParameter("preLoginUrl");
		if (preLoginUrl != null && !preLoginUrl.isBlank()) {
			response.sendRedirect(preLoginUrl);
		} else {
			response.sendRedirect("/");
		}*/
	}

	private void addAuthenticationCookie(HttpServletRequest request, HttpServletResponse response, String token,
			boolean isAccessToken) {
		Cookie authCookie;
		int maxAge;

		if (isAccessToken) {
			authCookie = new Cookie(cookieName, token);
			maxAge = Integer.parseInt(accessTokenPlusMinute) * 60;
		} else {
			authCookie = new Cookie(cookieRefreshName, token);
			maxAge = Integer.parseInt(refreshTokenPlusHour) * 60 * 60;
		}
		authCookie.setMaxAge(maxAge);
		authCookie.setPath("/");
		authCookie.setHttpOnly(true);
		authCookie.setSecure(request.isSecure());
		response.addCookie(authCookie);
	}

	@Override
	protected void unsuccessfulAuthentication(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException failed
	) throws IOException {
		response.sendRedirect("/login");
	}
}
