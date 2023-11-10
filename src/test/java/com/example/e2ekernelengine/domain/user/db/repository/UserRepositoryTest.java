package com.example.e2ekernelengine.domain.user.db.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
// @SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	// @Autowired
	// UserRepository userRepository;
	//
	// @Test
	// @DisplayName("지난주부터 이번주까지 User 기록을 꺼내온다.")
	// void findAllByUserRegisteredAtBetweenTest() {
	// 	//given
	// 	List<User> users = generateUsersForTest();
	// 	userRepository.saveAll(users);
	//
	// 	LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(2);
	// 	LocalDateTime lastWeekTimestamp = lastWeek;
	// 	LocalDateTime thisWeekTimestamp = LocalDateTime.now();
	//
	// 	// When
	// 	List<User> result = userRepository.findAllByUserRegisteredAtBetween(Timestamp.valueOf(lastWeekTimestamp),
	// 			Timestamp.valueOf(thisWeekTimestamp));
	//
	// 	// Then
	// 	assertThat(result).isNotNull();
	// 	assertThat(result).hasSize(15);
	// }
	//
	// private Timestamp generateRandomDate(String startDate, String endDate) {
	// 	LocalDate start = LocalDate.parse(startDate);
	// 	LocalDate end = LocalDate.parse(endDate);
	//
	// 	long startEpochDay = start.toEpochDay();
	// 	long endEpochDay = end.toEpochDay();
	//
	// 	long randomDay = startEpochDay + (long)(Math.random() * (endEpochDay - startEpochDay + 1));
	// 	Instant instant = LocalDate.ofEpochDay(randomDay).atStartOfDay(ZoneId.systemDefault()).toInstant();
	//
	// 	return Timestamp.from(instant);
	// }
	//
	// private List<User> generateUsersForTest() {
	// 	List<User> users = new ArrayList<>();
	//
	// 	for (int i = 0; i < 15; i++) {
	// 		User user = User.builder()
	// 				.userEmail("user" + i + "@example.com")
	// 				.userName("user" + i)
	// 				.userPassword("password" + i)
	// 				.userRegisteredAt(generateRandomDate("2023-10-26", "2023-11-09"))
	// 				.build();
	//
	// 		users.add(user);
	// 	}
	//
	// 	for (int i = 15; i < 20; i++) {
	// 		User user = User.builder()
	// 				.userEmail("user" + i + "@example.com")
	// 				.userName("user" + i)
	// 				.userPassword("password" + i)
	// 				.userRegisteredAt(generateRandomDate("2023-01-01", "2023-10-25"))
	// 				.build();
	//
	// 		users.add(user);
	// 	}
	//
	// 	return users;
	// }
}