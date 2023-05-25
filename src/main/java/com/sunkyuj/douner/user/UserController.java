package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 나중에 register로 대체
    @PostMapping()
    public Long addUser(@RequestBody UserDto userDto){
        return userService.join(userDto);
    }


    @GetMapping("")
    public List<User> users() {
        return userService.findUsers();
    }

    @GetMapping("/requester")
    public List<User> requester() {
        return userService.findRequesters();
    }

    @GetMapping("/volunteer")
    public List<User> volunteer() {
        return userService.findVolunteers();
    }

    @GetMapping("/{userId}")
    public User user(@PathVariable("userId") Long userId) {
        return userService.findOne(userId);
    }
//
//    @PostMapping("/register")
//    public ResponseEntity<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterParam userRegisterParam) {
//        User res = userService.register(userRegisterParam);
//        UserRegisterResponse result = new UserRegisterResponse(res.getUserId(), "success");
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginReqParam userLoginReqParam) {
//        UserLoginResultToken result = userService.login(userLoginReqParam);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
