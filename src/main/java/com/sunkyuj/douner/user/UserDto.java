package com.sunkyuj.douner.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunkyuj.douner.common.Gender;
import com.sunkyuj.douner.location.Location;
import com.sunkyuj.douner.post.PostCategory;
import com.sunkyuj.douner.post.model.Post;
import jakarta.persistence.Embedded;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Location location; // 사용자 거주 위치
}
