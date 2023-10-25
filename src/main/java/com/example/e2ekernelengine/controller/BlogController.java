package com.example.e2ekernelengine.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e2ekernelengine.dto.BlogRequest;
import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.entity.OwnerType;
import com.example.e2ekernelengine.exception.NotFoundException;
import com.example.e2ekernelengine.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blogs")
public class BlogController {
	private final BlogService blogService;

	//-- CREATE --//
	// TODO :: 같은 주소에 대한 필터링 로직 추가 -> url 을 unique로 두면 되지 않나?
	// TODO :: java.sql.SQLIntegrityConstraintViolationException 정의해서 intercept 하기
	// TODO :: ResponseEntity의 원시 사용 지양
	@PostMapping
	public ResponseEntity save(@RequestBody BlogRequest request) {
		blogService.saveBlog(request.toEntity());
		return ResponseEntity.status(201)
				.build();
	}

	//-- Read --//
	@GetMapping(value = "/{blog_id}")
	public ResponseEntity<Blog> findOneById(@PathVariable Long blog_id) {
		Blog blog = blogService.findOneById(blog_id);
		if (blog != null) {
			return ResponseEntity.status(200)
					.body(blog);
		}
		return ResponseEntity.notFound()
				.build();
	}

	@GetMapping
	public ResponseEntity<List<Blog>> findAll() {
		List<Blog> blogList = blogService.findAll();
		if (!blogList.isEmpty()) {
			return ResponseEntity.status(200)
					.body(blogList);
		}
		throw new NotFoundException("[ERROR] : 게시된 블로그가 하나도 없습니다.");
		// return ResponseEntity.notFound()
		// 		.build();
	}

	@GetMapping(value = "/owner/{ownerType}")
	public ResponseEntity<List<Blog>> findBlogListByOwnerType(@PathVariable("ownerType") OwnerType ownerType) {
		List<Blog> blogList = blogService.findBlogsByOwnerType(ownerType);
		if (!blogList.isEmpty()) {
			return ResponseEntity.status(200)
					.body(blogList);
		}
		throw new NotFoundException("[ERROR] : 해당 속성으로 게시된 블로그가 없습니다.");
		// return ResponseEntity.notFound()
		// 		.build();
	}

	//-- UPDATE --//
	// TODO :: 실패했을 때 응답을 반환하도록 변경
	@PostMapping(value = "/{blog_id}")
	public ResponseEntity<Blog> updateBlogById(@PathVariable Long blog_id, @RequestBody BlogRequest request) {
		blogService.updateBlogById(blog_id, request);
		return ResponseEntity
				.status(200).build();
	}

	//-- DELETE --//
	@DeleteMapping(value = "/{blog_id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable Long blog_id) {
		Blog deletedBlog = blogService.deleteById(blog_id);
		if (deletedBlog != null) {
			return ResponseEntity
					.accepted().body(deletedBlog);
		}
		return ResponseEntity
				.notFound().build();
	}
}
