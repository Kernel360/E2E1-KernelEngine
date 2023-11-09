package com.example.e2ekernelengine.domain.user.db.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.domain.user.db.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByUserEmail(String userEmail);

	@Query("SELECT u FROM User u WHERE u.userStatusType = 'ACTIVE' AND u.userEmail = :userEmail")
	Optional<User> findActiveUserByUserEmail(@Param("userEmail") String userEmail);

	List<User> findAllByUserRegisteredAtBetween(Timestamp startDatetime, Timestamp endDatetime);

	@Query(value = "SELECT COUNT(*) AS count, DATE_FORMAT(user_registered_at, '%y-%m-%d') AS date " +
			"FROM user " +
			"WHERE DATE_FORMAT(user_registered_at, '%y-%m-%d') BETWEEN DATE_FORMAT(CURDATE() - INTERVAL 14 DAY, '%y-%m-%d') AND DATE_FORMAT(CURDATE(), '%y-%m-%d') "
			+ "GROUP BY date", nativeQuery = true)
	List<Object[]> getCountByDateInRange();

	@Query(value = "SELECT COUNT(*) AS count, DATE_FORMAT(user_registered_at, '%y-%m-%d') AS date " +
			"FROM user " +
			"WHERE DATE_FORMAT(user_registered_at, '%y-%m-%d') BETWEEN DATE_FORMAT(CURDATE() - INTERVAL 14 DAY, '%y-%m-%d') AND DATE_FORMAT(CURDATE(), '%y-%m-%d') "
			+ "GROUP BY date " +
			"ORDER BY date", nativeQuery = true)
	List<Object[]> getCountAndDateInRangeOrdered();

	List<User> findAllByUserRegisteredAtBetweenOrderByUserRegisteredAt(Timestamp start, Timestamp end);
}
