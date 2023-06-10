package com.sunkyuj.douner.user.model;

import com.sunkyuj.douner.user.UserType;
import com.sunkyuj.douner.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
    private String email;
    private String password;
    private UserType userType;

    protected UserLoginRequest(){}

    // 사용자에게 입력받은 데이터를 Entity로 보내줌

    public User toEntity(String password){          // 비밀번호를 암호화 해야하기 때문에 password는 따로 입력받아 저장시킨다.
        return User.builder()
                .password(password)
                .email(this.email)
                .userType(this.userType)
                .build();
    }
}
