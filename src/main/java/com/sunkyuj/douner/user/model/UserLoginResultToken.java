package com.sunkyuj.douner.user.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResultToken {
    private Long userId;
    private String username;
    private String accessToken;
    private String refreshToken;
}
