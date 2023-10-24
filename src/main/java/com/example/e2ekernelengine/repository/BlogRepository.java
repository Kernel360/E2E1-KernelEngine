package com.example.e2ekernelengine.repository;

import com.example.e2ekernelengine.entity.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogRepository {
    private final EntityManager entityManager;

    public void save(Blog blog) {
        entityManager.persist(blog);
    }

    public Blog findOne(Long id) {
        return entityManager.find(Blog.class, id);
    }

    public List<Blog> findAll() {
        return entityManager.createQuery("select b from Blog b", Blog.class)
                .getResultList();
    }

    public List<Blog> findByOwnerTypeIsIndividual(String ownerType) {
        return entityManager.createQuery("select b from Blog b where b.ownerType = :ownerType", Blog.class)
                .setParameter("ownerType", ownerType)
                .getResultList();
    }

}
