package com.example.e2ekernelengine.domain.admin.service;

import com.example.e2ekernelengine.domain.admin.dto.DateAndTotalUserCountDto;
import com.example.e2ekernelengine.domain.admin.dto.MostVisitedFeedDto;
import com.example.e2ekernelengine.domain.admin.dto.response.DailyTop10FeedListResponse;
import com.example.e2ekernelengine.domain.admin.dto.response.RegistrationCountResponse;
import com.example.e2ekernelengine.domain.feed.db.repository.FeedRepository;
import com.example.e2ekernelengine.domain.statistics.db.entity.FeedStatistics;
import com.example.e2ekernelengine.domain.statistics.db.entity.UserRegisterStatistics;
import com.example.e2ekernelengine.domain.statistics.db.repository.DailyFeedStatisticsRepository;
import com.example.e2ekernelengine.domain.statistics.db.repository.UserRegisterStatisticsRepository;
import com.example.e2ekernelengine.domain.user.db.entity.User;
import com.example.e2ekernelengine.domain.user.db.repository.UserRepository;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

	private final UserRepository userRepository;

	private final FeedRepository feedRepository;

	private final DailyFeedStatisticsRepository dailyFeedStatisticsRepository;

	private final UserRegisterStatisticsRepository userRegisterStatisticsRepository;

	public RegistrationCountResponse getTotalUserCountByJpa() {
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

	public RegistrationCountResponse getTotalUserCountByNativeQuery() {
		List<Object[]> result = userRepository.getCountAndDateInRangeOrdered();
		LocalDate beforeOneWeek = LocalDate.now().minusDays(7);

		result.forEach(row -> {
			log.debug("row: {}", Arrays.toString(row));
		});

		List<DateAndTotalUserCountDto> thisWeeKUserCountList = new ArrayList<>();
		List<DateAndTotalUserCountDto> lastWeekUserCountList = new ArrayList<>();

		for (int i = 0; i < result.size(); i++) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
			LocalDate date = LocalDate.parse((CharSequence) result.get(i)[1], formatter);
			if (date.isAfter(beforeOneWeek)) {
				thisWeeKUserCountList.add(DateAndTotalUserCountDto.builder()
								.date(date)
								.totalRegistrationCount(3)
								.build());
			} else {
				lastWeekUserCountList.add(DateAndTotalUserCountDto.builder()
								.date(date)
								.totalRegistrationCount(3)
								.build());
			}
		}

		return RegistrationCountResponse.builder()
						.thisWeekTotalUserCountList(thisWeeKUserCountList)
						.lastWeekTotalUserCountList(lastWeekUserCountList)
						.build();
	}

	public DailyTop10FeedListResponse getDailyTop10FeedList(LocalDate date) {
		List<FeedStatistics> feedStatisticsList = dailyFeedStatisticsRepository.findTop10ByStatisticsAtOrderByVisitCountDesc(
						date.atStartOfDay(), date.atTime(23, 59, 59));
		return DailyTop10FeedListResponse.from(makeMostVisitedFeedDtoList(feedStatisticsList));
	}

	private List<MostVisitedFeedDto> makeMostVisitedFeedDtoList(List<FeedStatistics> list) {
		return list.stream()
						.map(feedStatistics -> feedRepository.findById(feedStatistics.getFeedId()))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.map(feed -> MostVisitedFeedDto.from(feed))
						.collect(Collectors.toList());
	}

	public RegistrationCountResponse getDailyRegistrationCount(LocalDate date) {
		List<UserRegisterStatistics> registrationCountList = userRegisterStatisticsRepository.findAllByStatisticsAtBetweenSort(
						date.minusWeeks(2).atStartOfDay(), date.atTime(23, 59, 59));

		LocalDateTime beforeOneWeek = LocalDate.now().minusDays(7).atTime(23, 59, 59);
		Map<Boolean, List<DateAndTotalUserCountDto>> userCountByWeek = registrationCountList.stream()
						.collect(Collectors.partitioningBy(data -> data.getStatisticsAt().isAfter(beforeOneWeek),
										Collectors.mapping(data -> DateAndTotalUserCountDto.of(data.getStatisticsAt().toLocalDate(),
														data.getRegisteredCount()), Collectors.toList())));

		return RegistrationCountResponse.of(userCountByWeek.get(true), userCountByWeek.get(false));
	}
}
