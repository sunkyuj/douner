package com.sunkyuj.douner.user;

import com.sunkyuj.douner.user.model.User;
import com.sunkyuj.douner.utils.ApiResult;
import com.sunkyuj.douner.utils.ApiUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/users")
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

    @PostMapping("/register")
    public ApiResult<UserRegisterResponse> joinUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse result = userService.register(userRegisterRequest);
        return ApiUtils.success(result);
    }

    @PostMapping("/login")
    public ApiResult<UserLoginResultToken> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResultToken result = userService.login(userLoginRequest);
        return ApiUtils.success(result);
    }
}
