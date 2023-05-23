package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }
    public User findOne(Long id) {
        return em.find(User.class, id);
    }
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }
    public List<User> findAllRequester() {
        return em.createQuery("select u from User u where u.userType = :userType", User.class)
                .setParameter("userType", UserType.REQUESTER)
                .getResultList();
    }
    public List<User> findAllVolunteer() {
        return em.createQuery("select u from User u where u.userType = :userType", User.class)
                .setParameter("userType", UserType.VOLUNTEER)
                .getResultList();
    }
    public List<User> findByName(String name) {
        return em.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }
}
