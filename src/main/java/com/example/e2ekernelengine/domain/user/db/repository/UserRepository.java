package com.example.e2ekernelengine.domain.user.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.domain.user.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUserEmail(String userEmail);

}
