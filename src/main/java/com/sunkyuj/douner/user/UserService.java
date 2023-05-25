package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
}
