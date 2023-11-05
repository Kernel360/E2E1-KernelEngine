package com.example.e2ekernelengine.token.filter;

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

import com.example.e2ekernelengine.token.service.TokenService;
import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.security.CustomUserDetail;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	private final String cookieName;
	private final String accessTokenPlusMinute;

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
		String token = tokenService.createToken(user);
		Cookie cookie = new Cookie(cookieName, token);
		cookie.setMaxAge(Integer.parseInt(accessTokenPlusMinute));
		cookie.setPath("/");
		response.addCookie(cookie);
		response.sendRedirect("/");
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
