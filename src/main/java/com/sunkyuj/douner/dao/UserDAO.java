package com.sunkyuj.douner.dao;

import com.sunkyuj.douner.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User,String> {
}
