package com.sunkyuj.douner.service;

import com.sunkyuj.douner.domain.user.User;
import com.sunkyuj.douner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }
    public List<User> findUsers(){
        return userRepository.findAll();
    }
    public User findOne(Long id){
        return userRepository.findOne(id);
    }

}
