package com.example.e2ekernelengine.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.e2ekernelengine.user.security.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomUserDetailService customUserDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable();
		http.csrf().disable();
		http.rememberMe().disable();
		// session 사용할 때는 아래 코드 주석처리
		/*http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
		// 추후에 jwt filter 추가 예정

		// authorization
		http.authorizeRequests()
				.antMatchers("/", "/home", "/signup", "/error").permitAll()
				.anyRequest().authenticated();
		// login
		http.formLogin()
				.loginPage("/login")
				.usernameParameter("userEmail")
				.passwordParameter("userPassword")
				.defaultSuccessUrl("/", true)
				.failureUrl("/login?error=true")
				.permitAll();
		// logout
		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true);
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
