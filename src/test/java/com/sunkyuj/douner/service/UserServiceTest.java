package com.sunkyuj.douner.service;

import com.sunkyuj.douner.domain.user.User;
import com.sunkyuj.douner.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        User user = new User();
        user.setName("kim");

        //when
        Long savedId = userService.join(user);

        //then
        Assert.assertEquals(user, userRepository.findOne(savedId));

    }
}