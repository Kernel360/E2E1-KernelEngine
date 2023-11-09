package com.example.e2ekernelengine.domain.admin.service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.domain.admin.dto.DateAndTotalUserCountDto;
import com.example.e2ekernelengine.domain.admin.dto.response.UserCountResponse;
import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepository;

	public UserCountResponse getTotalUserCountByJpa() {
		// TODO: 지난주부터 이번주까지 USer 기록을 꺼내온다.
		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(14), LocalTime.of(0, 0, 0));
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59, 59));
		log.debug("startDatetime: {}", startDatetime);
		log.debug("endDatetime: {}", endDatetime);
		
		List<User> userList = userRepository.findAllByUserRegisteredAtBetweenOrderByUserRegisteredAt(
				Timestamp.valueOf(startDatetime), Timestamp.valueOf(endDatetime));

		// TODO: 날짜별로 count
		// Map<Timestamp, Long> countByDate = userList.stream()
		// 		.collect(Collectors.groupingBy(User::getUserRegisteredAt, Collectors.counting()));
		//
		// List<Map.Entry<Timestamp, Long>> countList = countByDate.entrySet().stream()
		// 		.sorted(Map.Entry.comparingByKey())
		// 		.collect(Collectors.toList());
		//
		// // countList를 사용하거나, 필요에 따라 User 객체 리스트로 변환할 수 있어.
		// List<User> countedUserList = countList.stream()
		// 		.map(entry -> {
		// 			User user = new User();
		// 			user.setUserRegisteredAt(entry.getKey());
		// 			user.setCount(entry.getValue()); // User 클래스에 count 필드를 추가해야 함
		// 			// 다른 필요한 필드들도 여기에 추가
		// 			return user;
		// 		})
		// 		.collect(Collectors.toList());

		// TODO: total count 정리해서 response로 내보낸다.

		return null;
	}

	public UserCountResponse getTotalUserCountByNativeQuery() {
		List<Object[]> result = userRepository.getCountAndDateInRangeOrdered();
		LocalDate beforeOneWeek = LocalDate.now().minusDays(7);

		result.forEach(row -> {
			log.debug("row: {}", Arrays.toString(row));
		});

		List<DateAndTotalUserCountDto> thisWeeKUserCountList = new ArrayList<>();
		List<DateAndTotalUserCountDto> lastWeekUserCountList = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
			LocalDate date = LocalDate.parse((CharSequence)result.get(i)[1], formatter);
			if (date.isAfter(beforeOneWeek)) {
				thisWeeKUserCountList.add(DateAndTotalUserCountDto.builder()
						.date(date)
						.totalUserCount((BigInteger)result.get(i)[0])
						.build());
			} else {
				lastWeekUserCountList.add(DateAndTotalUserCountDto.builder()
						.date(date)
						.totalUserCount((BigInteger)result.get(i)[0])
						.build());
			}
		}

		return UserCountResponse.builder()
				.thisWeekTotalUserCountList(thisWeeKUserCountList)
				.lastWeekTotalUserCountList(lastWeekUserCountList)
				.build();
	}
}
