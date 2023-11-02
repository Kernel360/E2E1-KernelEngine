package com.example.e2ekernelengine.user.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e2ekernelengine.user.db.entity.User;
import com.example.e2ekernelengine.user.util.UserStatusType;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUserEmail(String userEmail);

	// select * from user where user_id = ? and user_status_type = ? order by user_id desc limit 1
	Optional<User> findFirstByUserIdAndUserStatusTypeOrderByUserIdDesc(Long userId, UserStatusType userStatusType);

	// select * from user where user_email = ? and user_password = ? and user_status_type = ? order by user_id desc limit 1
	Optional<User> findFirstByUserEmailAndUserPasswordAndUserStatusTypeOrderByUserIdDesc(String userEmail,
			String userPassword, UserStatusType userStatusType);
}
