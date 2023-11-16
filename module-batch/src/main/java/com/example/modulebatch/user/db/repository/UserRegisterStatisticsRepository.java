package com.example.modulebatch.user.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.modulebatch.user.db.entity.UserRegisterStatistics;

public interface UserRegisterStatisticsRepository extends JpaRepository<UserRegisterStatistics,Long> {
}
