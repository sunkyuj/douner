package com.sunkyuj.douner.user;

import com.sunkyuj.douner.security.JwtService;
import com.sunkyuj.douner.security.LoginToken;
import com.sunkyuj.douner.user.model.UserLoginRequest;
import com.sunkyuj.douner.user.model.UserLoginResultToken;
import com.sunkyuj.douner.user.model.UserRegisterRequest;
import com.sunkyuj.douner.user.model.UserRegisterResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
class UserRegisterLoginTest {
    @Autowired
    UserService userService;
    @Autowired
    JwtService jwtService;

    @Autowired
    PlatformTransactionManager transactionManager;

    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        // 트랜잭션 롤백
        transactionManager.rollback(status);
    }

    @Test
    void joinUser() {
        System.out.println("## User Register Test 시작 ##");
        System.out.println();

        //given
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "testUser1","password","test1@test.com",UserType.REQUESTER);
        int numOfUsersBefore = userService.findUsers().size();

        //when
        UserRegisterResponse result = userService.register(userRegisterRequest);
        int numOfUsersAfter = userService.findUsers().size();


        //then
        assertEquals("testUser1", result.getName());
        assertEquals("test1@test.com", result.getEmail());
        assertEquals(numOfUsersBefore+1, numOfUsersAfter);
    }

    @Test
    void loginUser() {
        //given
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(
                "testUser2","password","test2@test.com",UserType.REQUESTER);
        UserRegisterResponse userRegisterResponse = userService.register(userRegisterRequest);
        UserLoginRequest userLoginRequest = new UserLoginRequest(
                "test2@test.com","password",UserType.REQUESTER);

        //when
        UserLoginResultToken result = userService.login(userLoginRequest);

        //then
        System.out.println("userRegisterResponse.getUserId() = " + userRegisterResponse.getUserId());
        System.out.println("result.getUserId() = " + result.getUserId());

        assertEquals(userRegisterResponse.getUserId(),result.getUserId());
    }
}