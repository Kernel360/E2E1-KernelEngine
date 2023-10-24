package com.example.e2ekernelengine.controller;

import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BlogController {
    private final BlogService blogService;

    @PostMapping(value = "")
    public void save(@RequestBody Blog blog) {
        blogService.save(blog);
    }

    @GetMapping(value = "/{ownerType}")
    public void getCompanyBlogList(@PathVariable String ownerType) {
        blogService.findBlogsByOwnerType(ownerType);
    }
}
