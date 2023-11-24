package com.example.e2ekernelengine.domain.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.domain.blog.dto.request.BlogRequestDto;
import com.example.e2ekernelengine.domain.blog.dto.request.SaveBlogRequest;
import com.example.e2ekernelengine.domain.blog.dto.response.BlogResponseDto;
import com.example.e2ekernelengine.domain.blog.service.BlogService;
import com.example.e2ekernelengine.domain.blog.util.BlogOwnerType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogController {
	private final BlogService blogService;

	@PostMapping
	public ResponseEntity<BlogResponseDto> saveBlog(@RequestBody @Valid SaveBlogRequest request) {
		BlogResponseDto savedBlogDto = blogService.saveBlog(request.toEntity());
		return ResponseEntity.status(201).body(savedBlogDto);
	}

	@GetMapping(value = "/{blogId}")
	public ResponseEntity<BlogResponseDto> findBlogById(@PathVariable Long blogId) {
		BlogResponseDto dto = blogService.findBlogById(blogId);
		return ResponseEntity.status(200)
				.body(dto);
	}

	@GetMapping
	public ResponseEntity<List<BlogResponseDto>> findAll() {
		List<BlogResponseDto> blogResponseDtoList = blogService.findAllBlog();
		return ResponseEntity.status(200)
				.body(blogResponseDtoList);
	}

	@GetMapping(value = "/owner/{ownerType}")
	public ResponseEntity<List<BlogResponseDto>> findBlogListByOwnerType(
			@PathVariable("ownerType") BlogOwnerType ownerType) {
		if (!ownerType.equals(BlogOwnerType.INDIVIDUAL) && !ownerType.equals(BlogOwnerType.COMPANY)) {
			throw new IllegalArgumentException("[ERROR] : 잘못된 ownerType 입력입니다.");
		}

		List<BlogResponseDto> blogList = blogService.findBlogsByOwnerType(ownerType);
		return ResponseEntity.status(200)
				.body(blogList);
	}

	@PostMapping(value = "/{blogId}")
	public ResponseEntity<BlogResponseDto> updateBlogById(@PathVariable Long blogId,
			@RequestBody BlogRequestDto request) {
		request.setId(blogId);

		BlogResponseDto updatedBlogDto = blogService.updateBlogById(request);
		return ResponseEntity
				.status(200).body(updatedBlogDto);
	}

	@DeleteMapping(value = "/{blogId}")
	public ResponseEntity<BlogResponseDto> deleteBlog(@PathVariable Long blogId) {
		log.debug("deleteBlog() : blog_id = {}", blogId);
		BlogResponseDto deletedBlogDto = blogService.deleteById(blogId);
		return ResponseEntity
				.accepted().body(deletedBlogDto);
	}

	// @PostMapping("/api/v1/user/blog")
	// public void saveUserBlog(@RequestBody @Valid SaveUserBlogRequest request) {
	// 	// TODO: UserId 뽑아내기
	// 	Long userId = 1L;
	// 	blogService.saveUserBlog(userId, request);
	// }
}
