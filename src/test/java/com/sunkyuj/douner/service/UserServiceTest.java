package com.sunkyuj.douner.service;

import com.sunkyuj.douner.user.model.User;
import com.sunkyuj.douner.user.UserRepository;
import com.sunkyuj.douner.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

//    @Test
//    public void 회원가입() throws Exception {
//        //given
//        User user = new User();
//        user.setName("kim");
//
//        //when
//        Long savedId = userService.join(user);
//
//        //then
//        Assert.assertEquals(user, userRepository.findOne(savedId));
//
//    }
}