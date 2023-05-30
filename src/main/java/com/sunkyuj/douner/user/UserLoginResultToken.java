package com.sunkyuj.douner.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginResultToken {
    private Long userIndex;
    private String userId;
    private String accessToken;
    private String refreshToken;
}
