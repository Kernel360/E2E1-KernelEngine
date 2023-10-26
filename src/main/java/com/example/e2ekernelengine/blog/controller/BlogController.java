package com.example.e2ekernelengine.blog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.blog.dto.BlogRequestDto;
import com.example.e2ekernelengine.blog.dto.BlogResponseDto;
import com.example.e2ekernelengine.blog.service.BlogService;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogController {
	private final BlogService blogService;

	//-- CREATE --//
	@PostMapping
	public ResponseEntity<BlogResponseDto> saveBlog(@RequestBody BlogRequestDto request) {
		BlogResponseDto savedBlogDto = blogService.saveBlog(request.toEntity());
		return ResponseEntity.status(201).body(savedBlogDto);
	}

	//-- Read --//
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

	//-- UPDATE --//
	@PostMapping(value = "/{blogId}")
	public ResponseEntity<BlogResponseDto> updateBlogById(@PathVariable Long blogId,
			@RequestBody BlogRequestDto request) {
		request.setId(blogId);

		BlogResponseDto updatedBlogDto = blogService.updateBlogById(request);
		return ResponseEntity
				.status(200).body(updatedBlogDto);
	}

	//-- DELETE --//
	@DeleteMapping(value = "/{blog_id}")
	public ResponseEntity<BlogResponseDto> deleteBlog(@PathVariable Long blog_id) {
		BlogResponseDto deletedBlogDto = blogService.deleteById(blog_id);
		return ResponseEntity
				.accepted().body(deletedBlogDto);
	}
}
