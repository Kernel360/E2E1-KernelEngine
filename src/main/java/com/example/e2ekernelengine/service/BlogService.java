package com.example.e2ekernelengine.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.example.e2ekernelengine.dto.BlogRequest;
import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.entity.OwnerType;
import com.example.e2ekernelengine.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
	private final BlogRepository blogRepository;

	//-- 비즈니스 로직 --//
	//-- xml을 파싱해서 블로그와 포스팅 정보를 저장하는 로직이 필요 --//
	@Transactional
	public void saveBlog(Blog blog) {
		blogRepository.save(blog);
	}

	public List<Blog> findBlogsByOwnerType(String ownerType) {
		if (!ownerType.equals(OwnerType.INDIVIDUAL.toString()) && !ownerType.equals(OwnerType.COMPANY.toString())) {
			throw new IllegalArgumentException("[ERROR] : 잘못된 ownerType 입력");
		}
		return blogRepository.findByOwnerTypeIsIndividual(ownerType);
	}

	public Blog findOneById(Long blogId) {
		return blogRepository.findOne(blogId);
	}

	public List<Blog> findAll() {
		return blogRepository.findAll();
	}

	@Transactional
	public void updateBlogById(Long blogId, BlogRequest blogRequest) {
		blogRepository.findOne(blogId)
				.updateBlog(blogRequest.getRss(), blogRequest.getUrl(), blogRequest.getDescription());
	}

	@Transactional
	public Blog deleteById(Long blogId) {
		return blogRepository.deleteById(blogId);
	}
}
