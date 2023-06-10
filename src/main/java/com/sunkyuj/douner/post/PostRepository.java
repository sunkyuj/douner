package com.sunkyuj.douner.post;

import com.sunkyuj.douner.post.model.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor

public class PostRepository {
    private final EntityManager em;

    public void save(Post post) {
        em.persist(post);
    }
    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }
}
