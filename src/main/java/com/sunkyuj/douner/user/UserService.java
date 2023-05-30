package com.sunkyuj.douner.user;

import com.sunkyuj.douner.config.WebSecurityConfig;
import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public Long join(UserDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .imageURL(userDto.getImageURL())
                .userType(userDto.getUserType())
                .location(userDto.getLocation())
                .build();

        userRepository.save(user);
        return user.getId();
    }
    public List<User> findUsers(){
        return userRepository.findAll();
    }
    public User findOne(Long id){
        return userRepository.findOne(id);
    }

    public List<User> findRequesters() {
        return userRepository.findAllRequester();
    }

    public List<User> findVolunteers() {
        return userRepository.findAllVolunteer();
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
        System.out.println(user);
        userRepository.save(user);
        System.out.println("saved!");
        return new UserRegisterResponse(user.getId(), user.getName(), user.getEmail(), user.getUserType());
    }

//    public UserLoginResultToken login(UserLoginReqParam userLoginReqParam) {
//    }
}
