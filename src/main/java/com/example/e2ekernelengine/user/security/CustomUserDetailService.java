package com.example.e2ekernelengine.user.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User user = userRepository.findUserByUserEmail(userEmail)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with userEmail: " + userEmail));

		return CustomUserDetail.build(user);
	}
}
