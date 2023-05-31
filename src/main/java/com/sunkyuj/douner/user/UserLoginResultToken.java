package com.sunkyuj.douner.user;

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
