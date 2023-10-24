package com.example.e2ekernelengine.service;

import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    //-- 비즈니스 로직 --//
    //-- xml을 파싱해서 블로그와 포스팅 정보를 저장하는 로직이 필요 --//

    /**
     * 객체를 저장만 할 수 있게 예시로 만든 메소드 입니다.
     * 반환 타입이나 하는 일, 메서드명 등에 대해서는 다시 고민하고 변경하겠습니다.
     **/
    public void save(Blog blog) {
        blogRepository.save(blog);
    }
    // FIXME :: 잘못된 ownerType 입력에 대한 예외처리 필요
    public List<Blog> findBlogsByOwnerType(String ownerType) {
        return blogRepository.findByOwnerTypeIsIndividual(ownerType);
    }
}
