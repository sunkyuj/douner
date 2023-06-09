package com.sunkyuj.douner.user.model;

import com.sunkyuj.douner.user.UserType;
import com.sunkyuj.douner.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterResponse {
    private Long userId;
    private String name;
    private String email;
    private UserType userType;
}
