package com.sunkyuj.douner.user;

import com.sunkyuj.douner.address.Address;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private String name; // 사용자 이름
    private String phoneNumber; // 전화번호
    private String password; // 비밀번호
    private String email; // 이메일
    private String imageURL; // 프로필사진 URL
    private UserType userType; // 사용자 타입 [REQUESTER, VOLUNTEER]
    private Address address; // 사용자 거주 위치
}
