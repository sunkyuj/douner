package com.sunkyuj.douner.user;

import com.sunkyuj.douner.errors.CustomException;
import com.sunkyuj.douner.errors.ErrorCode;
import com.sunkyuj.douner.security.JwtService;
//import com.sunkyuj.douner.security.JwtTokenUtil;
import com.sunkyuj.douner.security.LoginToken;
import com.sunkyuj.douner.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

//    @Value("${jwt.token.secret}")
//    private String secretkey;   // application.yml에서 설정한 token 키의 값을 저장함
//    private long expireTimeMs = 1000*60*60; // 토큰 1시간

    @Transactional
    public Long join(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .imageURL(userDto.getImageURL())
                .userType(userDto.getUserType())
                .address(userDto.getAddress())
                .build();

        userRepository.save(user);
        return user.getId();
    }
    public List<UserResponse> findUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user:users){
            userResponses.add(UserResponse.builder()
                            .name(user.getName())
                            .phoneNumber(user.getPhoneNumber())
                            .email(user.getEmail())
                            .imageURL(user.getImageURL())
                            .userType(user.getUserType())
                            .address(user.getAddress())
                    .build());
        }
        return userResponses;
    }
    public UserResponse findOne(Long id){
        User user = userRepository.findOne(id);
        return UserResponse.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .imageURL(user.getImageURL())
                .userType(user.getUserType())
                .address(user.getAddress())
                .build();
    }

    public List<UserResponse> findRequesters() {
        List<User> users = userRepository.findAllRequester();
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user:users){
            userResponses.add(UserResponse.builder()
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .imageURL(user.getImageURL())
                    .userType(user.getUserType())
                    .address(user.getAddress())
                    .build());
        }
        return userResponses;
    }

    public List<UserResponse> findVolunteers() {
        List<User> users = userRepository.findAllVolunteer();
        List<UserResponse> userResponses = new ArrayList<>();
        for(User user:users){
            userResponses.add(UserResponse.builder()
                    .name(user.getName())
                    .phoneNumber(user.getPhoneNumber())
                    .email(user.getEmail())
                    .imageURL(user.getImageURL())
                    .userType(user.getUserType())
                    .address(user.getAddress())
                    .build());
        }
        return userResponses;
    }

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        // 중복 회원 검사
//        List<User> findUsers =userRepository.findByName(userRegisterRequest.getName());
        List<User> findUsers =userRepository.findByEmail(userRegisterRequest.getEmail());
        if(!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        // UserJoinRequest -> User Entity변환후 데이터 DB 저장 , password는 암호화 하여 저장
        String encodedPassword = encoder.encode(userRegisterRequest.getPassword());
        User user = userRegisterRequest.toEntity(encodedPassword);
        userRepository.save(user);
        return new UserRegisterResponse(user.getId(), user.getName(), user.getEmail(), user.getUserType());
    }
/**/
//    public String login(UserLoginRequest userLoginRequest) {
//        // 유저 존재 여부 확인
//        List<User> findUsers =userRepository.findByEmail(userLoginRequest.getEmail());
//        if(findUsers.isEmpty()) {
//            throw new IllegalStateException("존재하지 않는 회원입니다."); // NOT FOUND 에러로 변경?
//        }
//        // password일치 하는지 여부 확인
//        if(!encoder.matches(userLoginRequest.getPassword(),findUsers.get(0).getPassword())){      // encoder.matches는 암호화된 문자를 입력된 문자와 비교해주는 메서드이다
////            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD,String.format("비밀번호가 틀립니다."));
//            throw new IllegalStateException("비밀번호가 틀립니다.");
//        }
//        return JwtTokenUtil.createToken(userLoginRequest.getName(),expireTimeMs,secretkey);
//    }

    // return 을 UserLoginResultToken 으로 함
    @Transactional
    public UserLoginResultToken login(UserLoginRequest userLoginRequest) {
        System.out.println(userLoginRequest);

        // 유저 존재 여부 및 유저타입 확인
        List<User> findUsers = userRepository.findByEmail(userLoginRequest.getEmail());
        if(findUsers.isEmpty() || userLoginRequest.getUserType()!=findUsers.get(0).getUserType()) {
            log.error("존재하지 않는 회원, 이메일이나 유저 타입을 확인하세요.");
            throw new IllegalArgumentException("존재하지 않는 회원, 이메일이나 유저 타입을 확인하세요.");
//            throw new CustomException(ErrorCode.LOGIN_ID_NOT_EXIST);
        }
        User findUser = findUsers.get(0);

        // password일치 하는지 여부 확인
        if(!encoder.matches(userLoginRequest.getPassword(),findUser.getPassword())){      // encoder.matches는 암호화된 문자를 입력된 문자와 비교해주는 메서드이다
            log.error("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//            throw new CustomException(ErrorCode.LOGIN_PASSWORD_NOT_EQUAL);
        }

        LoginToken loginToken = jwtService.createToken(findUser.getId());

        return UserLoginResultToken.builder()
                .accessToken(loginToken.getAccessToken())
                .refreshToken(loginToken.getRefreshToken())
                .userId(findUser.getId())
                .username(findUser.getName())
                .build();
    }


}
