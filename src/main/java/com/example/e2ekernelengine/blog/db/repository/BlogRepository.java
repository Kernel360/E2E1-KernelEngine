package com.example.e2ekernelengine.blog.db.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.blog.db.entity.Blog;
import com.example.e2ekernelengine.blog.util.BlogOwnerType;
import com.example.e2ekernelengine.global.exception.IllegalAccessToSameUrlException;
import com.example.e2ekernelengine.global.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
	private final EntityManager em;

	public Blog save(Blog blog) {
		if (!findBlogByUrl(blog).isEmpty()) {
			throw new IllegalAccessToSameUrlException("[ERROR] : 동일한 블로그가 존재합니다.");
		}
		if (blog.getBlogId() == null) {
			em.persist(blog);
		} else {
			em.merge(blog);
		}
		return blog;
	}

	private List<Blog> findBlogByUrl(Blog blog) {
		return em.createQuery("select b from Blog b where b.blogUrl = :url", Blog.class)
				.setParameter("url", blog.getBlogUrl())
				.getResultList();
	}

	public Blog findBlogById(Long id) {
		Blog foundBlog = em.find(Blog.class, id);
		if (foundBlog == null) {
			throw new NotFoundException("[ERROR] : 해당 아이디로 블로그가 존재하지 않습니다.");
		}
		return foundBlog;
	}

	public List<Blog> findAll() {
		List<Blog> blogList = em.createQuery("select b from Blog b", Blog.class)
				.getResultList();
		if (blogList.isEmpty()) {
			throw new NotFoundException("[ERROR] : 게시된 블로그가 하나도 없습니다.");
		}
		return blogList;
	}

	public List<Blog> findByOwnerTypeIsIndividual(BlogOwnerType ownerType) {
		List<Blog> blogList = em.createQuery("select b from Blog b where b.blogOwnerType = :ownerType", Blog.class)
				.setParameter("ownerType", ownerType)
				.getResultList();
		if (blogList.isEmpty()) {
			throw new NotFoundException("[ERROR] : 해당 속성으로 게시된 블로그가 없습니다.");
		}
		return blogList;
	}

	public Blog deleteById(Long blogId) {
		Blog deletedBlog = em.find(Blog.class, blogId);
		if (deletedBlog != null) {
			em.remove(deletedBlog);
		} else {
			throw new NotFoundException("[ERROR] : 해당 아이디로 블로그가 존재하지 않습니다.");
		}
		return deletedBlog;
	}
}
