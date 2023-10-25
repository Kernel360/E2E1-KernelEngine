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
import com.example.e2ekernelengine.service.BlogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {
	private final BlogService blogService;

	//-- CREATE --//
	@PostMapping(value = "/")
	public void save(@RequestBody BlogRequest request) {
		blogService.saveBlog(request.toEntity());
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

	@GetMapping(value = "/")
	public ResponseEntity<List<Blog>> findAll() {
		List<Blog> blogList = blogService.findAll();
		if (!blogList.isEmpty()) {
			return ResponseEntity.status(200)
					.body(blogList);
		}
		return ResponseEntity.notFound()
				.build();
	}

	@GetMapping(value = "/{ownerType}")
	public ResponseEntity<List<Blog>> findBlogListByOwnerType(@PathVariable String ownerType) {
		List<Blog> blogList = blogService.findBlogsByOwnerType(ownerType);
		if (!blogList.isEmpty()) {
			return ResponseEntity.status(200).body(blogList);
		}
		return ResponseEntity.notFound().build();
	}

	//-- UPDATE --//
	@PostMapping(value = "/{blog_id}")
	public ResponseEntity<Blog> updateBlogById(@PathVariable Long blog_id, @RequestBody BlogRequest request) {
		blogService.updateBlogById(blog_id, request);
		return ResponseEntity.status(200).build();
	}

	//-- DELETE --//
	@DeleteMapping(value = "/{blog_id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable Long blog_id) {
		Blog deletedBlog = blogService.deleteById(blog_id);
		if (deletedBlog != null) {
			return ResponseEntity.accepted().body(deletedBlog);
		}
		return ResponseEntity.notFound().build();
	}
}
