package com.example.e2ekernelengine.feed.dto.request;

import lombok.Getter;

@Getter
public class FeedSearchRequest {
	private String keyword; // 검색어
	// keyword만 필요하다면 PathVariable에서 keyword를 받아오면되기 때문에 해당 dto가 필요없지만
	// 추후에 최대 결과 수 제한 같은 변수를 추가할 수도 있음
	// 만약 작성자로 찾기, 제목으로 찾기와 같은 옵션을 넣으려면 해당 내용을 여기에 담을 예정
}
