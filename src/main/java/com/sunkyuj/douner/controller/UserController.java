package com.sunkyuj.douner.controller;

import com.sunkyuj.douner.domain.user.User;
import com.sunkyuj.douner.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User user(@PathVariable Long userId) {
        return userService.findOne(userId);
    }
}
