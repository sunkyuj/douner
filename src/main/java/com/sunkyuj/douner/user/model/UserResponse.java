package com.sunkyuj.douner.user.model;

import com.sunkyuj.douner.address.Address;
import com.sunkyuj.douner.user.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserResponse {
    private String name; // 사용자 이름
    private String phoneNumber; // 전화번호
    private String email; // 이메일
    private String imageURL; // 프로필사진 URL
    private UserType userType; // 사용자 타입 [REQUESTER, VOLUNTEER]
    private Address address; // 사용자 거주 위치
}
