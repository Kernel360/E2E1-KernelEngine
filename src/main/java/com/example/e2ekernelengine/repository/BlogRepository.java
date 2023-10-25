package com.example.e2ekernelengine.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.example.e2ekernelengine.entity.Blog;
import com.example.e2ekernelengine.entity.OwnerType;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
	private final EntityManager em;

	public void save(Blog blog) {
		if (blog.getId() == null) {
			em.persist(blog);
		} else {
			em.merge(blog);
		}
	}

	public Blog findOne(Long id) {
		return em.find(Blog.class, id);
	}

	public List<Blog> findAll() {
		return em.createQuery("select b from Blog b", Blog.class)
				.getResultList();
	}

	public List<Blog> findByOwnerTypeIsIndividual(OwnerType ownerType) {
		return em.createQuery("select b from Blog b where b.ownerType = :ownerType", Blog.class)
				.setParameter("ownerType", ownerType)
				.getResultList();
	}

	public Blog deleteById(Long blogId) {
		Blog deletedBlog = em.find(Blog.class, blogId);
		if (deletedBlog != null) {
			em.remove(deletedBlog);
		}
		return deletedBlog;
	}
}
