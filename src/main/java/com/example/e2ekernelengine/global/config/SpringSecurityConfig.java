package com.example.e2ekernelengine.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.e2ekernelengine.domain.user.db.repository.UserRepository;
import com.example.e2ekernelengine.domain.user.security.CustomUserDetailService;
import com.example.e2ekernelengine.domain.user.token.filter.JwtAuthenticationFilter;
import com.example.e2ekernelengine.domain.user.token.filter.JwtAuthorizationFilter;
import com.example.e2ekernelengine.domain.user.token.service.TokenService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailService customUserDetailService;
	private final TokenService tokenService;
	private final UserRepository userRepository;
	@Value("${token.cookie.name}")
	private String cookieName;
	@Value("${token.cookie.refresh-name}")
	private String cookieRefreshName;
	@Value("${token.access-token.plus-minute}")
	private String accessTokenPlusMinute;
	@Value("${token.refresh-token.plus-hour}")
	private String refreshTokenPlusHour;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		http.csrf().disable();
		http.rememberMe();

		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(
				new JwtAuthenticationFilter(
						authenticationManager(),
						tokenService,
						cookieName,
						cookieRefreshName,
						accessTokenPlusMinute,
						refreshTokenPlusHour
				),
				UsernamePasswordAuthenticationFilter.class
		).addFilterBefore(
				new JwtAuthorizationFilter(
						userRepository,
						tokenService,
						cookieName,
						cookieRefreshName,
						accessTokenPlusMinute),
				BasicAuthenticationFilter.class
		);

		http.authorizeRequests()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated();
		http.formLogin()
				.loginPage("/login")
				.usernameParameter("userEmail")
				.passwordParameter("userPassword")
				.failureUrl("/login")
				.permitAll();
		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.deleteCookies(cookieName, cookieRefreshName);
		// TODO: DB에 refresh-token 삭제
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public AuthenticationManager authenticationManager(
			HttpSecurity http,
			BCryptPasswordEncoder bCryptPasswordEncoder
	) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUserDetailService)
				.passwordEncoder(bCryptPasswordEncoder)
				.and()
				.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
