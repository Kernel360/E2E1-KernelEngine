package com.example.modulebatch.user.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.modulebatch.user.db.entity.UserStatistics;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics,Long> {
}
