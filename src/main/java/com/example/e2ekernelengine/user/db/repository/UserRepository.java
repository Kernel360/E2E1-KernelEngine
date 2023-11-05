package com.example.e2ekernelengine.user.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e2ekernelengine.user.db.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUserEmail(String userEmail);

	Optional<User> findByUserEmail(String userEmail);

}
