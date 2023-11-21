package com.example.e2ekernelengine.domain.statistics.db.repository;

import com.example.e2ekernelengine.domain.statistics.db.entity.UserRegisterStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRegisterStatisticsRepository extends JpaRepository<UserRegisterStatistics, Long> {

}
